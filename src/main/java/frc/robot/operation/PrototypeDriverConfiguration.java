package frc.robot.operation;

import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.RobotContainer;

/**
 * This class represents a operation configuration that uses an xbox controller
 */
public class PrototypeDriverConfiguration extends AbstractCommandXboxOperationConfiguration {

    public PrototypeDriverConfiguration(CommandXboxController controller) {
        super(controller);
    }

    @Override
    public void registerTeleopFunctions(RobotContainer rc) {
        // rc.registerManuallyFieldOrientedSwerveDrive(
        rc.registerAutoFieldOrientedDrive(
                () -> getJoystickInput(controller, 0),
                () -> getJoystickInput(controller, 1),
                () -> getJoystickInput(controller, 4));
    }


	@Override
	public void registerRobotFunctions(RobotContainer rc) {
        rc.registerZeroGyro(controller.start());
        rc.registerShoot(controller.a());
	}

    
    private double getJoystickInput(CommandXboxController stick, int axe) {
        return stick.getRawAxis(axe);
    }

	@Override
	public Trigger start() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'start'");
	}

}
