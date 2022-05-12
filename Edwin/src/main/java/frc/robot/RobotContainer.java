// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.input.gamepads.AutoGamepad;
import com.stuypulse.stuylib.input.gamepads.PS4;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.Ports;
import frc.robot.commands.DrivetrainDrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Pump;

public class RobotContainer {

    public final Drivetrain drivetrain = new Drivetrain();
    public final Gamepad driver = new AutoGamepad(Ports.Gamepad.DRIVER);
    public final Feeder feeder = new Feeder();
    public final Pump pump = new Pump();

    public RobotContainer() {
        configureButtonBindings();
        configureDefaultCommands();

        pump.compress();
    }

    private void configureButtonBindings() {

    }

    private void configureDefaultCommands() {
        drivetrain.setDefaultCommand(new DrivetrainDrive(drivetrain, driver));
    }

    public Command getAutonomousCommand() {
        return null;
    }
}
