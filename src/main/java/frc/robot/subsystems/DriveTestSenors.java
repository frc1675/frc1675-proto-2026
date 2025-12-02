// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@Logged
public class DriveTestSenors extends SubsystemBase {
  /** Creates a new DriveTestSenors. */
  private final int LeftFront = 0;
  private final int RightFront = 1;
  private final int RightRear = 2;
  private final int LeftRear = 3;

  
  private DutyCycleEncoder leftFront = new DutyCycleEncoder(LeftFront);
  private DutyCycleEncoder rightFront = new DutyCycleEncoder(RightFront);
  private DutyCycleEncoder rightRear = new DutyCycleEncoder(RightRear);
  private DutyCycleEncoder leftRear = new DutyCycleEncoder(LeftRear);


  public DriveTestSenors() {
  }
  
  @Logged
  public double getleftFront() {
    return leftFront.get();
  }
  @Logged
  public double getrightFront() {
    return rightFront.get();
  }
  @Logged
  public double getrightRear() {
    return rightRear.get();
  }
  @Logged
  public double getleftRear() {
    return leftRear.get();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("leftFront", getleftFront() );
    SmartDashboard.putNumber("rightFront", getrightFront() );
    SmartDashboard.putNumber("rightRear", getrightRear() );
    SmartDashboard.putNumber("leftRear", getleftRear() );
  }
}
