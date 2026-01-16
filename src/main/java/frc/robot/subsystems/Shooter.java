package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
//import frc.robot.Constants.OperatorConstants;

import com.revrobotics.spark.SparkMax;

/** Add your docs here. */
public class Shooter {
   
    private final int SPARKMAX = 9; 
    private SparkMax flRotationMotor = new SparkMax(SPARKMAX, MotorType.kBrushless);

    public final double MOTOR_ON = 12;
    public final double MOTOR_OFF = 0.0;
    public void startFlRotationMotor () {
        flRotationMotor.setVoltage(MOTOR_ON);
    }
    public void stopFlRotationMotor () {    
      flRotationMotor.setVoltage(MOTOR_OFF);
  }
}