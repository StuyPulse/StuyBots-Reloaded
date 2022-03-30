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

import static frc.robot.Constants.Drivetrain.*;

/**
 * This is just a simple Drivetrain Drive Command that implements very simple filters.
 */
public class DrivetrainDriveCommand extends CommandBase {

    private Drivetrain drivetrain;
    private Gamepad driver;

    // These filters help smooth out driving and prevent motor damage
    private IFilter speedFilter = new LowPassFilter(SPEED_FILTER);
    private IFilter turnFilter = new LowPassFilter(TURNING_FILTER);

    public DrivetrainDriveCommand(Drivetrain subsystem, Gamepad gamepad) {
        drivetrain = subsystem;
        driver = gamepad;
        
        addRequirements(subsystem);
    }

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
        drivetrain.arcadeDrive(speed, turn);
    }


    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
