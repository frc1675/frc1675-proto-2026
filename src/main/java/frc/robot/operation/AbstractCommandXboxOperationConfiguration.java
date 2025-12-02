package frc.robot.operation;

import dev.doglog.DogLog;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class represents a operation configuration that uses an xbox controller
 */
public abstract class AbstractCommandXboxOperationConfiguration implements OperationConfiguration {

    protected CommandXboxController controller;

    public AbstractCommandXboxOperationConfiguration(CommandXboxController controller) {
        this.controller = controller;
    }

    public void periodic() {
        // Diagnostic logging
        DogLog.log("SwerveStudy/driverX", controller.getLeftX());
        DogLog.log("SwerveStudy/driverY", controller.getLeftY());
        DogLog.log("SwerveStudy/driverRot", controller.getRightX());
    }
}
