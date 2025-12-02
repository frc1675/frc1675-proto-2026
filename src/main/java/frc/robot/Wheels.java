// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;

import com.revrobotics.spark.SparkMax;

/** Add your docs here. */
public class Wheels {
   
    private final int FL_ROTATION = 1;
    private SparkMax flRotationMotor = new SparkMax(FL_ROTATION, MotorType.kBrushless);
    private final int FL_WHEEL = 2;
    private SparkMax flWheelMotor = new SparkMax(FL_WHEEL, MotorType.kBrushless);

    private final int FR_ROTATION = 3;
    private SparkMax frRotationMotor = new SparkMax(FR_ROTATION, MotorType.kBrushless);
    private final int FR_WHEEL = 4;
    private SparkMax frWheelMotor = new SparkMax(FR_WHEEL, MotorType.kBrushless);

    private final int BR_ROTATION = 5;
    private SparkMax brRotationMotor = new SparkMax(BR_ROTATION, MotorType.kBrushless);
    private final int BR_WHEEL = 6;
    private SparkMax brWheelMotor = new SparkMax(BR_WHEEL, MotorType.kBrushless);

    private final int BL_ROTATION = 7;
    private SparkMax blRotationMotor = new SparkMax(BL_ROTATION, MotorType.kBrushless);
    private final int BL_WHEEL = 8;
    private SparkMax blWheelMotor = new SparkMax(BL_WHEEL, MotorType.kBrushless);



    public final double MOTOR_ON = 1.2;
    public final double MOTOR_OFF = 0.0;
    public void startFlRotationMotor () {
        flRotationMotor.setVoltage(MOTOR_ON);
    }
    public void stopFlRotationMotor () {
        flRotationMotor.setVoltage(MOTOR_OFF);
    }

    public void startFrRotationMotor () {
        frRotationMotor.setVoltage(MOTOR_ON);
    }
    public void stopFrRotationMotor () {
        frRotationMotor.setVoltage(MOTOR_OFF);
    }

    public void startBlRotationMotor () {
        blRotationMotor.setVoltage(MOTOR_ON);
    }
    public void stopBlRotationMotor () {
        blRotationMotor.setVoltage(MOTOR_OFF);
    }

    public void startBrRotationMotor () {
        brRotationMotor.setVoltage(MOTOR_ON);
    }
    public void stopBrRotationMotor () {
        brRotationMotor.setVoltage(MOTOR_OFF);
    }


    public void startFlWheelMotor () {
        flWheelMotor.setVoltage(MOTOR_ON);
    }
    public void stopFlWheelMotor () {
        flWheelMotor.setVoltage(MOTOR_OFF);
    }

    public void startFrWheelMotor () {
        frWheelMotor.setVoltage(MOTOR_ON);
    }
    public void stopFrWheelMotor () {
        frWheelMotor.setVoltage(MOTOR_OFF);
    }

    public void startBlWheelMotor () {
        blWheelMotor.setVoltage(MOTOR_ON);
    }
    public void stopBlWheelMotor () {
        blWheelMotor.setVoltage(MOTOR_OFF);
    }

    public void startBrWheelMotor () {
        brWheelMotor.setVoltage(MOTOR_ON);
    }
    public void stopBrWheelMotor () {
        brWheelMotor.setVoltage(MOTOR_OFF);
    }
}


