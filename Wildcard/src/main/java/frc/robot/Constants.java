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
        interface Ports {
            int LEFT_TOP = 3;
            int LEFT_MIDDLE = 2;
            int LEFT_BOTTOM = 1;
        
            int RIGHT_TOP = 6;
            int RIGHT_MIDDLE = 5;
            int RIGHT_BOTTOM = 4;

            int GEAR_SHIFT = 7;
        }
    }

    public interface Intake {
        interface Ports {
            int MOTOR = 11;

            int SOLENOID_RIGHT = 2;
            int SOLENOID_LEFT = 3;

            int LIMIT_SWITCH = 1;
            int IR_SENSOR = 8;
        }
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
    }


}
