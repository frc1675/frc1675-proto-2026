// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

@Logged
public class DriveTestSenors extends SubsystemBase {
  /** Creates a new DriveTestSenors. */
  private final int LeftFront = 0;
  private final int RightFront = 1;
  private final int RightRear = 2;
  private final int LeftRear = 3;

  
  private DutyCycleEncoder encoder = new DutyCycleEncoder(LeftFront);
  private DutyCycleEncoder encoder1 = new DutyCycleEncoder(RightFront);
  private DutyCycleEncoder encoder2 = new DutyCycleEncoder(RightRear);
  private DutyCycleEncoder encoder3 = new DutyCycleEncoder(LeftRear);


  public DriveTestSenors() {
 }
  
  @Logged
  public double getEncoderValue() {
    return encoder.get();
  }
  @Logged
  public double getEncoderValue1() {
    return encoder1.get();
  }
  @Logged
  public double getEncoderValue2() {
    return encoder2.get();
  }
  @Logged
  public double getEncoderValue3() {
    return encoder3.get();
  }
  
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
