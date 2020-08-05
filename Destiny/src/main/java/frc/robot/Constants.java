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

    public interface Ports {
        public interface Drivetrain {
            public int FRONT_LEFT = 1;
            public int FRONT_RIGHT = 3;
            public int BACK_LEFT = 4;
            public int BACK_RIGHT = 2;
        }
    
        public int SHOOTER = 13;
    }

    // The class SmartNumber lets us change its value while the robot is running
    // This can be crucial if you need to tune the robot during operation.
    public interface Tuning {
        // Just leave the PID values like this if you are unable to tune
        public SmartNumber P = new SmartNumber("Drivetrain P", 0.1);
        public SmartNumber I = new SmartNumber("Drivetrain I", 0.0);
        public SmartNumber D = new SmartNumber("Drivetrain D", 0.0);

        // This is the gain for the Take Back Half algorithm for the shooter
        public SmartNumber GAIN = new SmartNumber("TBH Gain", 0.01);
    }

}
