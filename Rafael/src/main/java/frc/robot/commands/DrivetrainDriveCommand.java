/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.streams.filters.*;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Drivetrain;

/**
 * ----- PLEASE READ -----
 * This is an example implementation of the drivetrain drive command.
 * 
 * This is pretty much what we use for competitions. 
 * 
 * This version includes the use of Filters.
 * 
 * Filters are complicated, but can be used to make driving much smooter and more reliable.
 */
public class DrivetrainDriveCommand extends CommandBase {

    private Drivetrain drivetrain;
    private Gamepad driver;

    // These filters help smooth out driving
    // But they are also optional
    private IFilter speedFilter = new LowPassFilter(0.2);
    private IFilter turnFilter = new LowPassFilter(0.03);

    public DrivetrainDriveCommand(Drivetrain subsystem, Gamepad gamepad) {
        drivetrain = subsystem;
        driver = gamepad;
        
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
        // Get the speed from the triggers
        double speed = driver.getRightTrigger() - driver.getLeftTrigger();

        // Get the turn value from the left stick
        double turn = driver.getLeftX();

        // Filter the Speed and Turn value
        // This is optional, but it leads to a smoother driving experience.
        speed = speedFilter.get(speed);
        turn = turnFilter.get(turn);

        // Send values to drivetrain
        drivetrain.curvatureDrive(speed, turn);
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
