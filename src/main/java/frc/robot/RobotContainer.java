// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.drive.DriveSubsystem;
import frc.robot.operation.OperationConfiguration;
import frc.robot.operation.PrototypeDriverConfiguration;
import java.util.function.DoubleSupplier;
import swervelib.SwerveInputStream;

public class RobotContainer {
final CommandXboxController driverController =
      new CommandXboxController(OperatorConstants.DriverControllerPort);
final Wheels wheels =
    new Wheels(); 
    private OperationConfiguration driverConfig;

    private DriveSubsystem drive;

    public RobotContainer() {
     

        drive = new DriveSubsystem();

        configureBindings();
    }

    public void teleopInit() {}

    private void configureBindings() {
 driverController.a().onTrue(new InstantCommand(() -> wheels.startmotor()));
        driverConfig.registerTeleopFunctions(this);
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }

    // used by operation configurations to register the ability to drive the swerve
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
}
