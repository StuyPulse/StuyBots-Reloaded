/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * This contains all of the ports for motors, gamepads, etc.
 * 
 * Try to avoid having random constants around your code.
 * 
 * The reason its an interface is so that all the values are constant
 */
public interface Constants {

    public interface Drivetrain {
        interface Ports {
            int LEFT_TOP = 3;
            int LEFT_MIDDLE = 2;
            int LEFT_BOTTOM = 1;

            int RIGHT_TOP = 6;
            int RIGHT_MIDDLE = 5;
            int RIGHT_BOTTOM = 4;

            int GEAR_SHIFT = 7;
        }

        interface Encoders {
            double WHEEL_DIAMETER = 6.0;
            int PULSES_PER_REVOLUTION = 256;
            int FACTOR = 4;
            double INCHES_PER_REVOLUTION = Math.PI * WHEEL_DIAMETER;

            double EMPERICAL_RAW_MULTIPLIER = (63.7 / 63.0) * 61.1 / ((463.544 + 461.814) / 2.0);// 163/1246.0;

            double RAW_MULTIPLIER = EMPERICAL_RAW_MULTIPLIER * INCHES_PER_REVOLUTION / (PULSES_PER_REVOLUTION * FACTOR);

            double RIGHT_GAIN = -1;
            double LEFT_GAIN = 1;
        }

        double SPEED_RC = 0.2;
        double ANGLE_RC = 0.1;
    }

    public interface Intake {
        interface Ports {
            int MOTOR = 11;

            int SOLENOID_RIGHT = 2;
            int SOLENOID_LEFT = 3;

            int LIMIT_SWITCH = 1;
            int IR_SENSOR = 8;
        }

        double ACQUIRE_SPEED = 1;
        double DEACQUIRE_SPEED = -1;
    }

    public interface Lift {
        interface Ports {
            int INNER_LEFT_MOTOR = 7;
            int INNER_RIGHT_MOTOR = 9;
            int OUTER_LEFT_MOTOR = 8;
            int OUTER_RIGHT_MOTOR = 10;

            int TOP_LIMIT_SWITCH = 2;
            int BOTTOM_LIMIT_SWITCH = 0;
        }

        double TOTAL_CARRIAGE_MOVEMENT = 92.25; // unchanged for new

        interface Encoders {
            double EMPERICAL_RAW_MULTIPLIER = (45 + 46.5 + 39) / (41.125 + 42.199 + 35.44);

            double DIAMETER_OF_SPROCKET = 2.873; // unchanged for new
            double TO_SPROCKET_REDUCTION = 1 / 106.94; // (1.0 / 4) * (1.0 / 3); // updated
            double RAW_MULTIPLIER = EMPERICAL_RAW_MULTIPLIER * (DIAMETER_OF_SPROCKET * Math.PI * 3 / 1024) / 4.4
                    * TO_SPROCKET_REDUCTION;
        }

        double LIFT_RC = 0.1;
    }

}
