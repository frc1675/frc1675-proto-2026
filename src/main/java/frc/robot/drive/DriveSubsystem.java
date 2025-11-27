package frc.robot.drive;

import static edu.wpi.first.units.Units.Meter;

import dev.doglog.DogLog;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.brownbox.util.AllianceUtil;
import java.io.File;
import java.io.IOException;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

public class DriveSubsystem extends SubsystemBase {

    SwerveDrive swerve;
    File swerveJsonDirectory = new File(Filesystem.getDeployDirectory(), "swerve");

    public DriveSubsystem() {

        boolean blueAlliance = true;
        Pose2d startingPose = blueAlliance
                ? new Pose2d(new Translation2d(Meter.of(1), Meter.of(4)), Rotation2d.fromDegrees(0))
                : new Pose2d(new Translation2d(Meter.of(16), Meter.of(4)), Rotation2d.fromDegrees(180));
        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;

        try {
            swerve = new SwerveParser(swerveJsonDirectory)
                    .createSwerveDrive(Constants.Drive.MAXIMUM_VELOCITY, startingPose);
        } catch (IOException e) {
            System.out.println("Swerve drive configuration file could not be found at "
                    + Filesystem.getDeployDirectory()
                    + "/swerve");
            e.printStackTrace();
        }
    }

    public SwerveDrive getSwerveDrive() {
        return swerve;
    }

    public Command driveFieldOriented(Supplier<ChassisSpeeds> velocity) {
        return run(() -> {
            ChassisSpeeds logVelocity = velocity.get();
            SmartDashboard.putNumber("swerveX", logVelocity.vxMetersPerSecond);
            SmartDashboard.putNumber("swerveY", logVelocity.vyMetersPerSecond);
            SmartDashboard.putNumber("swerveR", logVelocity.omegaRadiansPerSecond);
            swerve.driveFieldOriented(logVelocity);
        });
    }

    public void setPose2d(Pose2d pose) {
        swerve.resetOdometry(pose);
        swerve.setGyro(new Rotation3d(0, 0, pose.getRotation().getRadians()));
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
}
