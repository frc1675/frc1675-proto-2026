// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Meter;

import dev.doglog.DogLog;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.brownbox.util.AllianceUtil;
import frc.robot.drive.DriveSubsystem;
import frc.robot.operation.OperationConfiguration;
import frc.robot.operation.PrototypeDriverConfiguration;
import java.util.function.DoubleSupplier;

public class RobotContainer {

    private OperationConfiguration driverConfig;

    private DriveSubsystem drive;

    public RobotContainer() {

        driverConfig = new PrototypeDriverConfiguration(new CommandXboxController(0));

        drive = new DriveSubsystem();

        configureBindings();
    }

    public void teleopInit() {
        boolean blueAlliance = !AllianceUtil.isRedAlliance();
        Pose2d startingPose = blueAlliance
                ? new Pose2d(new Translation2d(Meter.of(1), Meter.of(4)), Rotation2d.fromDegrees(0))
                : new Pose2d(new Translation2d(Meter.of(16), Meter.of(4)), Rotation2d.fromDegrees(180));
        drive.setPose2d(startingPose);
    }

    private void configureBindings() {
        driverConfig.registerTeleopFunctions(this);
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }

    // used by operation configurations to register the ability to drive the swerve
    public void registerSwerveDrive(DoubleSupplier x, DoubleSupplier y, DoubleSupplier rotation) {

        Command driveCommand = drive.driveCommand(
                () -> {
                    DogLog.log("Controller/Translation Y", y.getAsDouble());
                    return y.getAsDouble() * AllianceUtil.getTranslationDirection();
                },
                () -> {
                    DogLog.log("Controller/Translation X", x.getAsDouble());
                    return x.getAsDouble() * AllianceUtil.getTranslationDirection();
                },
                rotation);

        drive.setDefaultCommand(driveCommand);
    }
}
