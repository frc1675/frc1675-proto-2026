package frc.robot;

public class Constants {

    public static class Drive {
        public static final double ROTATION_P = 0.05;
        public static final double ROTATION_I = 0.001;
        public static final double ROTATION_D = 0;
        public static final double ROTATION_TARGET_RANGE = 1.5;
        public static final double MAXIMUM_VELOCITY = 5.7912;
        public static final double MAXIMUM_ROTATION_VELOCITY = 1.0;
    }

    public static class Controller {
        public static final int DRIVER_CONTROLLER_PORT = 0;

        public static final double DEADZONE_CONSTANT = 0.1675;
        public static final int LEFT_X_AXIS = 0;
        public static final int LEFT_Y_AXIS = 1;
        public static final int RIGHT_X_AXIS = 4;

        public static final int SCALE_TRANSLATION = 1;
    }
    public static class Auto {
        // 0 is a placeholder until best values are found
        public static final double TRANSLATION_P = 6;
        public static final double ROTATION_P = 6;
    }
}
