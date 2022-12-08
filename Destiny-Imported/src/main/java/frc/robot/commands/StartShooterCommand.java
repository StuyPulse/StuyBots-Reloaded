/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Shooter;

/**
 * This just sets the speed of the shooter to a certain speed.
 * 
 * This was mainly implemented to show how button binds can be made.
 */
public class StartShooterCommand extends CommandBase {
    private Shooter shooter;
    private double targetRPM;

    public StartShooterCommand(Shooter subsystem, double rpm) {
        shooter = subsystem;
        targetRPM = rpm;

        // This makes sure that two commands that need the same subsystem dont mess eachother up. 
        // Example, if a command activated by a button needs to take control away from a default command.
        addRequirements(subsystem);
    }

    // Called 50 times a second if the robot is running
    @Override
    public void execute() {
        shooter.setRPM(targetRPM);
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
