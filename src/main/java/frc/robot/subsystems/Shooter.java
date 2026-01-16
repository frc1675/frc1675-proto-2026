package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
//import frc.robot.Constants.OperatorConstants;
import com.revrobotics.spark.SparkMax;

import dev.doglog.DogLog;

/** Add your docs here. */
public class Shooter extends SubsystemBase {
   
    private final int SPARKMAX = 9; 
    private SparkMax motor = new SparkMax(SPARKMAX, MotorType.kBrushless);

    public final double MOTOR_ON = 12;
    public final double MOTOR_OFF = 0.0;
    public void start () {
        motor.setVoltage(MOTOR_ON);
    }
    public void stop () {    
      motor.setVoltage(MOTOR_OFF);
  }
  @Override
  public void periodic() {
    DogLog.log("ShooterRPM",motor.getEncoder().getVelocity());
  }
}