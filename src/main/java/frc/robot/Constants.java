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
    public static class Arm {
         // Motor constants
         public static final int ARM_MOTOR_RIGHT = 15;
         public static final int ARM_MOTOR_LEFT = 16;
         // PID constants
         public static final double PID_P_COEFFICIENT = 0.023;
         public static final double PID_I_COEFFICIENT = 0;
         public static final double PID_D_COEFFICIENT = 0;
         // Arm movment lmits constants
         public static final double TARGET_RANGE = 2.0;
         public static final double DEBOUNCE_TIME = 0.25;
         // Position constnats
         public static final double HOME_POSITION = 136;
         public static final double PODIUM_SHOT_ANGLE = HOME_POSITION - 33;
 
         public static final double HIGH_SCORE_POSITION = HOME_POSITION - 78;
         public static final double AMP_POSITION = HOME_POSITION - 103;
         public static final double MAX_ARM_RANGE_DEGREES = HOME_POSITION - 118; // home - 125 degrees is vertical
         // PID Profile constants
         public static final double MAXIMUM_VELOCITY = 150; // degrees per second
         public static final double MAXIMUM_ACCELERATION = 750; // degrees per second squared
     }
    } 

