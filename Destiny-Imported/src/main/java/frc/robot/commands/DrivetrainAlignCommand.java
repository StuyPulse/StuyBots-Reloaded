/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.stuypulse.stuylib.control.PIDController;
import com.stuypulse.stuylib.network.limelight.Limelight;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Drivetrain;
import frc.robot.Constants.Tuning;

/**
 * ----- PLEASE READ -----
 * This code is more complicated than the rest of this project.
 * 
 * It is the basic implementation of a PID Controller for a drivetrain aligning with a target.
 * 
 * The basic setup goes like this:
 * 
 *  - The limelight is tuned so that the reflexite tape under the target is highlighted
 * 
 *  - Using the Limelight class, we are able to get the angle of the target to the robot
 * 
 *  - This angle will tell us how far away we are from looking straight at the target
 * 
 *  - This angle is called our error, and will be used in the PID Controller
 * 
 *  - By giving this error to the PID controller, we can calculate the speed at which to turn
 * 
 *  - If the PID algorithm is tuned incorrectly, we will turn too fast and miss the target, or turn too slowly and never align
 * 
 * This is a complicated part of robot code
 * 
 * If you have any extra questions, please contact the heads of the 694 SE department 
 * They will be happy to help. 
 * 
 */
public class DrivetrainAlignCommand extends CommandBase {

    private Drivetrain drivetrain;
    private PIDController controller;

    public DrivetrainAlignCommand(Drivetrain subsystem) {
        drivetrain = subsystem;

        controller = new PIDController();
        
        // This makes sure that two commands that need the same subsystem dont mess eachother up. 
        // Example, if a command activated by a button needs to take control away from a default command.
        addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called 50 times a second if the robot is running
    @Override
    public void execute() {
        // Get the PID values from the smart numbers in the constant file.
        // Lets us tune in real time
        controller.setP(Tuning.P.doubleValue());
        controller.setI(Tuning.I.doubleValue());
        controller.setD(Tuning.D.doubleValue());
        
        // This is how far away we are from are target value
        // If we want to be looking straight at the target, this would have to be 0
        // But if we are looking away from the target, this will tell us by how much
        //double error = Limelight.getTargetXAngle();

        // The PID Controller will tell us how much we should turn to get to the target
        //double out = controller.update();
        double out = 0.0;

        // This moves the drivetrain by that amount
        drivetrain.arcadeDrive(0, out);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
