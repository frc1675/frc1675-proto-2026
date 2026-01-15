package frc.robot.subsystems;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.robot.Constants;

public class Arm implements AutoCloseable {
     final SingleJointedArmSim armSim = new SingleJointedArmSim(
            DCMotor.getNEO(2),
            100.0,
            SingleJointedArmSim.estimateMOI(Units.inchesToMeters(30), 13),
            Units.inchesToMeters(30),
            Units.degreesToRadians((-180)), // min angle
            Units.degreesToRadians((360)), // max 
            true,
            Units.degreesToRadians((0)));

            private final Mechanism2d mech2d = new Mechanism2d(60, 60);
    private final MechanismRoot2d armPivot = mech2d.getRoot("ArmPivot", 30, 30);
    private final MechanismLigament2d armTower = armPivot.append(new MechanismLigament2d("ArmTower", 30, -90));
    private final MechanismLigament2d arm = armPivot.append(new MechanismLigament2d(
            "Arm", 30, (Constants.Arm.HOME_POSITION), 6, new Color8Bit(Color.kYellow)));
               public Arm() {
      SmartDashboard.putData("Arm Sim", mech2d);
  }
  public void simulationPeriodic () {
        armSim.update(0.02);
        armSim.setInput (0);
        double angleraidans = armSim.getAngleRads();
        arm.setAngle(Units.radiansToDegrees(angleraidans));
  }
    public void close() {
    
      }
public void stop() {
        
}
    
}
