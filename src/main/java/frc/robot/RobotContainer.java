// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.drive.DriveSubsystem;

import java.io.File;
import java.util.function.DoubleSupplier;

import swervelib.SwerveInputStream;

public class RobotContainer {

    private final CommandXboxController driverController;

    private DriveSubsystem drive;

    public RobotContainer() {

        driverController = new CommandXboxController(0);

        drive = new DriveSubsystem();

        configureBindings();
    }

    public void teleopInit() {
        registerSwerveAngularVelocityDrive(() -> getJoystickInput(driverController, 0), () -> getJoystickInput(driverController, 1), () -> getJoystickInput(driverController, 4));
    }

    public void registerSwerveAngularVelocityDrive(DoubleSupplier x, DoubleSupplier y, DoubleSupplier rotation) {
        SwerveInputStream driveAngularVelocity = SwerveInputStream.of(
                        drive.getSwerveDrive(), x, y) // Axis which give the desired translational angle and speed.
                .withControllerRotationAxis(rotation) // Axis which give the desired angular velocity.
                .deadband(Constants.Controller.DEADZONE_CONSTANT) // Controller deadband
                .scaleTranslation(Constants.Controller.SCALE_TRANSLATION) // Scaled controller translation axis
                .allianceRelativeControl(
                        false); // Alliance relative controls. Done already in the driver configuration files.
        Command driveFieldOrientedAnglularVelocity = drive.driveFieldOriented(driveAngularVelocity);
        drive.setDefaultCommand(driveFieldOrientedAnglularVelocity);
    }

    private double getJoystickInput(CommandXboxController stick, int axe) {
        return stick.getRawAxis(axe);
    }

    

    private void configureBindings() {}

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
