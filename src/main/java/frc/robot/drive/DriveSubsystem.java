package frc.robot.drive;

import static edu.wpi.first.units.Units.Meter;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.io.File;
import java.io.IOException;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import dev.doglog.DogLog;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

public class DriveSubsystem extends SubsystemBase {

    double maximumSpeed = 1.0;

    SwerveDrive swerve;
    File swerveJsonDirectory = new File(Filesystem.getDeployDirectory(), "swerve");

    public DriveSubsystem() {

        boolean blueAlliance = false;
        Pose2d startingPose = blueAlliance
                ? new Pose2d(new Translation2d(Meter.of(1), Meter.of(4)), Rotation2d.fromDegrees(0))
                : new Pose2d(new Translation2d(Meter.of(16), Meter.of(4)), Rotation2d.fromDegrees(180));
        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;

        try {
            swerve = new SwerveParser(swerveJsonDirectory).createSwerveDrive(maximumSpeed, startingPose);
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

            DogLog.log("SwerveStudy/chassisX", logVelocity.vxMetersPerSecond);
            DogLog.log("SwerveStudy/chassisY", logVelocity.vyMetersPerSecond);
            DogLog.log("SwerveStudy/chassisRot", logVelocity.omegaRadiansPerSecond);

            swerve.driveFieldOriented(logVelocity);
        });
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
            swerve.drive(
                    new Translation2d(
                            translationX.getAsDouble() * swerve.getMaximumChassisVelocity(),
                            translationY.getAsDouble() * swerve.getMaximumChassisVelocity()),
                    angularRotationX.getAsDouble() * swerve.getMaximumChassisAngularVelocity(),
                    true,
                    false);
        });
    }
}
