/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * This contains all of the ports for motors, gamepads, etc.
 * 
 * Try to avoid having random constants around your code.
 * 
 * The reason its an interface is so that all the values are constant
 * 
 */
public interface Constants {

    interface Intake {
        interface Ports {
            int MOTOR = 10;
        }

        double ACQUIRE_SPEED = 1.0;
        double DEACQUIRE_SPEED = -1.0;
    }

    interface Elevator {
        interface Ports {
            int MASTER = 8;
            int FOLLOWER = 9;

            int BRAKE = 6;

            int LIMIT_SWITCH = 5;

            int TILT_A = 5;
            int TILT_B = 7;
        }

        double ENCODER_TICKS_PER_REV = 1024.0;

        double ENCODER_RAW_MULTIPLIER = 1 / 1207.0;
        double HEIGHT_MULTIPLIER = -1;

        boolean BRAKED = true;

        Value TILT_FORWARD = Value.kReverse;
        Value TILT_BACK = Value.kForward;
    }

    interface Grabber {
        interface Ports {
            int PUSHER_CHANNEL = 0;
            int FLOOP_CHANNEL = 0;

            // Solenoid class specific
            int PUSHER_MODULE = 1;

        }

        // Booleans to dictate which position of the solenoid is
        // the given state. This is used instead of enums and 1-2 helper
        // functions because, although cleaner, it feels verbose.
        boolean OPENED = false;
        boolean EXTENDED = true;
    }

    interface Drivetrain {

        interface Ports {
            int GEAR_SHIFT = 0;

            int LEFT_TOP = 2;
            int LEFT_MIDDLE = 3;
            int LEFT_BOTTOM = 1;

            int RIGHT_TOP = 6;
            int RIGHT_MIDDLE = 7;
            int RIGHT_BOTTOM = 5;

            public interface Greyhill {
                int LEFT_A = 2;
                int LEFT_B = 3;

                int RIGHT_A = 0;
                int RIGHT_B = 1;
            }
        }

        int CURRENT_LIMIT = 65;

        double WHEEL_DIAMETER = 6;
        double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;

        double OUTER_GEAR_RATIO = 24.0 / 60.0;

        public interface NEOEncoder {
            double YIELD = 1.0;
            double RAW_MULTIPLIER = WHEEL_CIRCUMFERENCE * YIELD;
        }

        public interface Greyhill {
            double PULSES_PER_REVOLUTION = 256 * 4.0;
            double YIELD = 1.3;
            double INCHES_PER_PULSE = ((WHEEL_CIRCUMFERENCE * OUTER_GEAR_RATIO) / PULSES_PER_REVOLUTION) * YIELD;
        }
    }
}
