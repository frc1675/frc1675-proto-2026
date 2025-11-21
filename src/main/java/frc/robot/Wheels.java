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
   
    private final int FLWHEEL = 1;
    private SparkMax motor = new SparkMax(FLWHEEL, MotorType.kBrushless);
    public final double MOTOR_ON = 1.2;
    public final double MOTOR_OFF = 0.0;

    public void startmotor () {
        motor.setVoltage(MOTOR_ON);
    }
}
