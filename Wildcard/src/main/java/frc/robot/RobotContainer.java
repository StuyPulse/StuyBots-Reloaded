/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.gamepads.PS4;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
// Import all the commands and subsystems
import frc.robot.commands.DrivetrainDriveCommand;
import frc.robot.commands.LiftMoveCommand;
import frc.robot.commands.autos.DoNothingAutonCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Lift;

/**
 * This is where the majority of the robot code is going to be created.
 * 
 * All button binds and commands are initialized here
 */
public class RobotContainer {

    // Create new driver gamepad connected to port 0
    private Gamepad driver = new PS4(0);
    private Gamepad operator = new PS4(1);

    // Create Subsystems
    private Drivetrain drivetrain = new Drivetrain();
    private Intake intake = new Intake();
    private Lift lift = new Lift();
    
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
        lift.setDefaultCommand(new LiftMoveCommand(lift, operator));
    }

    /**
     * Creates button bindings for gamepad
     */
    private void configureButtonBindings() {

        // Allow control for acquire, deacquire, and stopping
        operator.getDPadUp().whenPressed(new InstantCommand(intake::acquire, intake));
        operator.getDPadDown().whenPressed(new InstantCommand(intake::deacquire, intake));
        operator.getDPadLeft().whenPressed(new InstantCommand(intake::stop, intake));

        // Toggle intake to open / close
        operator.getDPadRight().whenPressed(new InstantCommand(intake::toggle, intake));

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
