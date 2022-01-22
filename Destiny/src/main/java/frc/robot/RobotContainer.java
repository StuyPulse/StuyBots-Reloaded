/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;


// Import all the commands and subsystems
import frc.robot.commands.*;
import frc.robot.commands.autos.*;
import frc.robot.subsystems.*;

import com.stuypulse.stuylib.input.gamepads.*;
import com.stuypulse.stuylib.input.Gamepad;

/**
 * This is where the majority of the robot code is going to be created.
 * 
 * All button binds and commands are initialized here
 */
public class RobotContainer {

    // Create new driver gamepad connected to port 0
    private Gamepad driver = new AutoGamepad(0);

    // Make the subsystems
    private Drivetrain drivetrain = new Drivetrain();
    private Shooter shooter = new Shooter();

    /**
     * Run at creation
     */
    public RobotContainer() {
        configureDefaultCommands();
        configureButtonBindings();
        configureSmartDashboard();
    }

    /**
     * Creates default commands for everything to run
     */
    private void configureDefaultCommands() {
        drivetrain.setDefaultCommand(new DrivetrainDriveCommand(drivetrain, driver));
        shooter.setDefaultCommand(new StopShooterCommand(shooter));
    }

    /**
     * Creates button bindings for gamepad
     */
    private void configureButtonBindings() {
        // Basic setup that:
        //    runs shooter full speed while right button is held
        //    runs shooter half speed while bottom button is held
        //    aligns the robot with the target while left button is held

        //driver.getRightButton().whileHeld(new StartShooterCommand(shooter, 1000));
        driver.getBottomButton().whenPressed(new StopShooterCommand(shooter));
        driver.getRightButton().whenPressed(new StartShooterCommand(shooter, 1000));
    

        driver.getLeftButton().whileHeld(new DrivetrainAlignCommand(drivetrain));
    }

    // This lets us select an auton
    private static SendableChooser<Command> autonChooser = new SendableChooser<>();

    /**
     * Put every auton that we made on smart dashboard
     */
    private void configureSmartDashboard() {
        autonChooser.addOption("Do Nothing", new DoNothingAutonCommand());
        SmartDashboard.putData(autonChooser);
    }

    /**
     * This lets us return which command we want to run during auton
     */
    public Command getAutonomousCommand() {
        return autonChooser.getSelected();
    }
}
