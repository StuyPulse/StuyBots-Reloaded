/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.FilteredIStream;
import com.stuypulse.stuylib.streams.IStream;
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
    private IStream speed;

    // These filters help smooth out driving
    // But they are also optional
    private IFilter speedFilter = new LowPassFilter(0.4);
    private IFilter turnFilter = new LowPassFilter(0.1);


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
        IStream rawSpeed = driver::getRightX; 

        // Get the turn value from the left stick
        

        // Filter the Speed and Turn value
        // This is optional, but it leads to a smoother driving experience.
        SmartNumber power = new SmartNumber("Power Filter", 2);
        speed = new FilteredIStream(rawSpeed, 
                                            (x) -> SLMath.deadband(x, 0), 
                                            (x) -> SLMath.spow(x, power.get()), 
                                            new LowPassFilter(new SmartNumber("Speed Filter", 0.2)));
    }

    // Called 50 times a second if the robot is running
    @Override
    public void execute() {
        // Get the speed from the triggers
        double turn = driver.getLeftX();
        
        drivetrain.arcadeDrive(speed.get(), turn);
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
