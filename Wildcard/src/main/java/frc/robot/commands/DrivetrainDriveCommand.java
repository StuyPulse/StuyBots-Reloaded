/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Constants.Drivetrain.*;

/**
 * An example command that uses an example subsystem.
 */
public class DrivetrainDriveCommand extends CommandBase {

    private final Drivetrain drivetrain;

    private IStream speed;
    private IStream angle;    

    public DrivetrainDriveCommand(Drivetrain drivetrain, Gamepad driver) {
        this.drivetrain = drivetrain;

        speed = () -> driver.getRightTrigger() - driver.getLeftTrigger();
        speed = speed.filtered(new LowPassFilter(SPEED_RC));

        angle = driver::getRightX;
        angle = angle.filtered(new LowPassFilter(ANGLE_RC));

        addRequirements(drivetrain);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        drivetrain.arcadeDrive(speed.get(), angle.get());
    }

}
