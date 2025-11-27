package frc.robot.operation;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotContainer;

/**
 * This class represents a operation configuration that uses an xbox controller
 */
public class PrototypeDriverConfiguration extends AbstractCommandXboxOperationConfiguration {

    public PrototypeDriverConfiguration(CommandXboxController controller) {
        super(controller);
    }

    @Override
    public void registerRobotFunctions(RobotContainer rc) {}

    @Override
    public void registerTeleopFunctions(RobotContainer rc) {
        rc.registerSwerveDrive(
                () -> getJoystickInput(controller, 0),
                () -> getJoystickInput(controller, 1),
                () -> getJoystickInput(controller, 4));
    }

    private double getJoystickInput(CommandXboxController stick, int axe) {
        return stick.getRawAxis(axe);
    }
}
