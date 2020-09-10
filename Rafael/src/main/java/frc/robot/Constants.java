/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.stuypulse.stuylib.network.SmartNumber;

/**
 * This contains all of the ports for motors, gamepads, etc.
 * 
 * Try to avoid having random constants around your code.
 * 
 * The reason its an interface is so that all the values are constant
 */
public interface Constants {

    public interface Drivetrain {
        public interface Ports {
            int LEFT_TOP = 1;
            int LEFT_BOTTOM = 2;
            int RIGHT_TOP = 3;
            int RIGHT_BOTTOM = 4;

            int GEAR_SHIFT = 0;
        }

        public interface Encoders {
            int PULSES_PER_REVOLUTION = 256;
            double WHEEL_DIAMETER = 4.0;
            double FACTOR = 4.0; // output must be scaled *down* by 4 due to type of encoder
            double INCHES_PER_REVOLUTION = Math.PI * WHEEL_DIAMETER;

            double LEFT_MULTIPLIER = 1.0;
            double RIGHT_MULTIPLIER = -1.0;
        }
    }

}
