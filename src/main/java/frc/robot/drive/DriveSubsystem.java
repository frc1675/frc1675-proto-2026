package frc.robot.drive;

import static edu.wpi.first.units.Units.Meter;

import dev.doglog.DogLog;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.brownbox.util.AllianceUtil;
import java.io.File;
import java.io.IOException;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

public class DriveSubsystem extends SubsystemBase {

    SwerveDrive swerve;
    File swerveJsonDirectory = new File(Filesystem.getDeployDirectory(), "swerve");

    public DriveSubsystem() {

        Pose2d startingPose = new Pose2d(new Translation2d(Meter.of(1), Meter.of(4)), Rotation2d.fromDegrees(0));
        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;

        try {
            swerve = new SwerveParser(swerveJsonDirectory)
                    .createSwerveDrive(Constants.Drive.MAXIMUM_VELOCITY, startingPose);
            swerve.setHeadingCorrection(true, 0.05);
        } catch (IOException e) {
            System.out.println("Swerve drive configuration file could not be found at "
                    + Filesystem.getDeployDirectory()
                    + "/swerve");
            e.printStackTrace();
        }

        // Load the PathPlanner config
        RobotConfig config = null;
        try{
            config = RobotConfig.fromGUISettings();
        } catch (Exception e) {
            // Handle exception as needed
            e.printStackTrace();
        }

        // Configure AutoBuilder last
        AutoBuilder.configure(
            this::getPose, // Robot pose supplier
            this::resetOdometry, // Method to reset odometry (will be called if your auto has a starting pose)
            this::getRobotRelativeChassisSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
            (speeds, feedforwards) -> driveRobotRelative(speeds), // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds. Also optionally outputs individual module feedforwards
            new PPHolonomicDriveController( // PPHolonomicController is the built in path following controller for holonomic drive trains
                    new PIDConstants(5.0, 0.0, 0.0), // Translation PID constants
                    new PIDConstants(20.0, 0.0, 0.0) // Rotation PID constants
            ),
            config, // The robot configuration
            () -> {
              // Boolean supplier that controls when the path will be mirrored for the red alliance
              // This will flip the path being followed to the red side of the field.
              // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

              var alliance = DriverStation.getAlliance();
              if (alliance.isPresent()) {
                return alliance.get() == DriverStation.Alliance.Red;
              }
              return false;
            },
            this // Reference to this subsystem to set requirements
    );
    }

    public SwerveDrive getSwerveDrive() {
        return swerve;
    }


    public Command driveFieldOriented(Supplier<ChassisSpeeds> velocity) {
        return run(() -> {
            ChassisSpeeds logVelocity = velocity.get();
            DogLog.log("SwerveStudy/chassisX", logVelocity.vxMetersPerSecond);
            DogLog.log("SwerveStudy/chassisY", logVelocity.vyMetersPerSecond);
            DogLog.log("SwerveStudy/chassisRot", logVelocity.omegaRadiansPerSecond);
            swerve.driveFieldOriented(logVelocity);
        });
    }

    // return the ROBOT-RELATIVE ChassisSpeeds -- used by PathPlanner's AutoBuilder.
    public ChassisSpeeds getRobotRelativeChassisSpeeds() {
        return swerve.getRobotVelocity();
    }

    // Use given ChassisSpeeds relative to the robot to drive -- used by PathPlanner's AutoBuilder.
    // PathPlanner operates in the frame of reference of the robot - it doesn't act in a field-relative manner like we drive in teleop.
    public void driveRobotRelative(ChassisSpeeds speeds) {
        swerve.drive(speeds);
    }

    // reset the robot's odometry to the given pose -- used by PathPlanner's AutoBuilder
    public void setPose2d(Pose2d pose) {
        swerve.resetOdometry(pose);
    }

    // Returns the current pose of the robot -- used by PathPlanner's AutoBuilder
    public Pose2d getPose() {
        return swerve.getPose();
    }


     /** Used for PathPlanner autonomous */
     public void resetOdometry(Pose2d override) {
        swerve.setGyro(new Rotation3d(
                swerve.getRoll().getMeasure(),
                swerve.getPitch().getMeasure(),
                override.getRotation().getMeasure()));

        swerve.resetOdometry(override);

        System.out.println("Ran resetOdometry");
    }

    /** Used for PathPlanner autonomous */
    public ChassisSpeeds getRobotRelativeSpeeds() {
        return swerve.getRobotVelocity();
    }


    /**
     * Zero the gyroscope. This is useful for resetting which way is considered positive for field
     * relative robot driving. This should probably only be done while debugging.
     */
    public void zeroGyroscope() {
        swerve.zeroGyro();
    }

    /** Used for PathPlanner autonomous */
    public void setRobotRelativeChassisSpeeds(ChassisSpeeds speeds) {
        swerve.drive(
                new Translation2d(speeds.vxMetersPerSecond, speeds.vyMetersPerSecond),
                speeds.omegaRadiansPerSecond,
                false,
                false);
    }

    /**
     * Command to drive the robot using translative values and heading as angular velocity.
     *
     * @param translationX     Translation in the X direction.
     * @param translationY     Translation in the Y direction.
     * @param angularRotationX Rotation of the robot to set
     * @return Drive command.
     */
    public Command driveCommand(
            DoubleSupplier translationX, DoubleSupplier translationY, DoubleSupplier angularRotationX) {
        return run(() -> {
            // Make the robot move
            DogLog.log("Drive/Red Alliance", AllianceUtil.isRedAlliance());
            DogLog.log("Drive/Robot Y", translationY.getAsDouble());
            DogLog.log("Drive/Robot X", translationX.getAsDouble());
            DogLog.log("Drive/Robot Yaw", swerve.getYaw().getDegrees());
            swerve.drive(
                    new Translation2d(
                            translationX.getAsDouble() * swerve.getMaximumChassisVelocity(),
                            translationY.getAsDouble() * swerve.getMaximumChassisVelocity()),
                    angularRotationX.getAsDouble() * swerve.getMaximumChassisAngularVelocity(),
                    false,
                    false);
        });
    }

    public void periodic() {
            DogLog.log("Drive/Yaw", getPose().getRotation());
        }
}
