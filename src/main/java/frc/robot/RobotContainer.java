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
<<<<<<< HEAD
=======
import swervelib.SwerveInputStream;
>>>>>>> origin/yagsl-attempt-2

public class RobotContainer {

    // The configurations that will govern the controls
    private OperationConfiguration driverConfig;

    // The subsystems of the robot - these perform Commands to make the robot do things.
    private DriveSubsystem drive;

    // Instantiate things when the robot code starts for use in this class.
    public RobotContainer() {
        // Set up operation configurations
        driverConfig = new PrototypeDriverConfiguration(new CommandXboxController(0));

        // set up subsystems
        drive = new DriveSubsystem();

        // handle any other setup
        configureBindings();
    }

    // A hook called from Robot.java that will fire when the robot enters teleoperated mode.
    public void teleopInit() {
        boolean blueAlliance = !AllianceUtil.isRedAlliance();
        Pose2d startingPose = blueAlliance
                ? new Pose2d(new Translation2d(Meter.of(1), Meter.of(4)), Rotation2d.fromDegrees(0))
                : new Pose2d(new Translation2d(Meter.of(16), Meter.of(4)), Rotation2d.fromDegrees(180));
        drive.setPose2d(startingPose);
    }

    public void periodic() {
        driverConfig.periodic();
    }

    // A separate method to hold the code for telling the various operation configurations
    //  to bind themselves to commands. This needs to happen after subsystems are available.
    private void configureBindings() {
        driverConfig.registerTeleopFunctions(this);
    }

    // Robot.java uses this to get the command to run for autonomous mode
    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }

    // used by operation configurations to register the ability to drive the swerve
<<<<<<< HEAD
    public void registerSwerveDrive(DoubleSupplier x, DoubleSupplier y, DoubleSupplier rotation) {
=======
    // Does its own logic to convert inputs to be field oriented
    public void registerManuallyFieldOrientedDrive(DoubleSupplier x, DoubleSupplier y, DoubleSupplier rotation) {
>>>>>>> origin/yagsl-attempt-2

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

    // used by operation configurations to register the ability to drive the swerve
    // Uses SwerveInputStream and YAGSL driveFieldOriented to auto-adjust to field oriented
    public void registerAutoFieldOrientedDrive(
            DoubleSupplier driverX, DoubleSupplier driverY, DoubleSupplier driverRotation) {
        // This is where we need to think about FRAMES OF REFERENCE.
        // Frame of reference 1: "Driver".
        //   The frame of reference you have when standing in the driver station of your current alliance.
        //   Whether the robot is on the blue or red alliance is irrelevant to this frame of reference.
        //   Pushing up on the driver translation joystick gives negative value to driverY and vice-versa.
        //   Pushing left on the driver translation joystick gives negative value to driverX and vice-versa.
        //   Pushing left on the driver rotation joystick gives negative value to driverRotation and vice-versa.
        // Frame of reference 2: "Field" / "Blue Origin".
        //   See:
        // https://docs.wpilib.org/en/stable/docs/software/basic-programming/coordinate-system.html#field-coordinate-systems
        //   The frame of reference that uses Blue Alliance Wall, Opposite Scoring Table corner as 0, 0.
        //   This frame of reference REMAINS THE SAME whether the robot is on the blue or red alliance.
        //   Most of the robot code and libraries operate in this frame of reference, and do transforms of convenience
        //     to handle alliance differences (driving, PathPlanner, etc). Velocity tranformations for driver input
        //     behave the same independent of whether the field is rotationally symmetric or simply mirrored.
        //   Blue alliance is the assumed default state of he robot, and cases where the robot is on the red alliance
        //     modify default behavior.
        //   Moving towards the scoring table gives positive value to fieldY and vice-versa.
        //   Moving towards the red alliance wall gives positive value to fieldX and vice-versa.
        //   Rotating counter-clockwise gives positive value to the robotAngle and vice-versa.

        // SwerveInputStream is a convenience class offered by YAGSL that takes in joystick values and converts
        //   them to a WPILib ChassisSpeeds object.
        // https://broncbotz.org/YAGSL-Lib/docs/swervelib/SwerveInputStream.html
        // https://github.com/BroncBotz3481/YAGSL/blob/main/swervelib/SwerveInputStream.java
        //
        // ChassisSpeeds is a data structure that describes the velocities (including angular)
        //   of the robot.
        // https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/math/kinematics/ChassisSpeeds.html
        SwerveInputStream driveAngularVelocity = SwerveInputStream
                // SwerveInputStream expects X and Y in terms of a BLUE ALLIANCE ROBOT.
                // Velocities will be flipped if the robot is red thanks to allianceRelativeControl later.
                // For a blue robot driver:
                //   Pushing up on the translation joystick (-driverY)
                //     should move the robot toward the red wall (+fieldX) and vice-versa.
                //   Pushing left on the translation joystick (-driverX)
                //     should move the robot toward the scoring table (+fieldY) and vice-versa.
                //   Result: fieldX = driverY * -1, fieldY = driverX * -1
                .of(drive.getSwerveDrive(), () -> (driverY.getAsDouble() * -1.0), () -> (driverX.getAsDouble() * -1.0))
                // the rotation of the robot doesn't change based on what way it's facing.
                .withControllerRotationAxis(driverRotation)
                // deadband to apply to each controller input - if it's less than the deadband, it's 0 instead.
                .deadband(Constants.Controller.DEADZONE_CONSTANT)
                // An amount to scale translation by, currently 1.0 (don't scale).
                // We could replace this with a DoubleSupplier if we want it to be dynamic.
                .scaleTranslation(Constants.Controller.SCALE_TRANSLATION)
                // If the robot is on the red alliance, rotate the calculated ChassisSpeeds 180 degrees.
                //   This makes sense, as both fieldX and fieldY would be flipped compared to driverY and driverX.
                .allianceRelativeControl(true);

        Command driveFieldOrientedAnglularVelocity = drive.driveFieldOriented(driveAngularVelocity);
        drive.setDefaultCommand(driveFieldOrientedAnglularVelocity);
    }
}
