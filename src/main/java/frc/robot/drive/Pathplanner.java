package frc.robot.drive;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import frc.robot.Constants;

public class Pathplanner {
    public Pathplanner(DriveSubsystem drive) {
        RobotConfig config = null;
    try{
      config = RobotConfig.fromGUISettings();
    } catch (Exception e) {
      // Handle exception as needed
      e.printStackTrace();
    }

    AutoBuilder.configure(
        drive::getPose,
        drive::resetOdometry,
        drive::getRobotRelativeSpeeds,
        (speeds, feedforwards) -> drive.setRobotRelativeChassisSpeeds(speeds),
        new PPHolonomicDriveController(
            new PIDConstants(5.0, 0.0, 0.0), 
            new PIDConstants(5.0, 0.0, 0.0)),
        config,
        () -> true,
        drive);
}
}
