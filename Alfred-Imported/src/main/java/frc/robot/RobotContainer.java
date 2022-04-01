/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
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
    private Gamepad driver = new Xbox(0);
    private Gamepad operator = new Xbox(1);

    // Create new subsystems
    private Compressor compressor = new Compressor();
    private Drivetrain drivetrain = new Drivetrain();
    private Elevator elevator = new Elevator();
    private Grabber grabber = new Grabber();
    private Intake intake = new Intake().enableRamping();

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
        elevator.setDefaultCommand(new ElevatorDefaultCommand(elevator, operator));
        intake.setDefaultCommand(new IntakeDefaultCommand(intake, operator));
        //grabber.setDefaultCommand(new GrabberDefaultCommand(grabber, operator));
    }

    /**
     * Creates button bindings for gamepad
     */
    private void configureButtonBindings() {
        driver.getBottomButton().whenPressed(new DrivetrainToggleGearCommand(drivetrain));
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
