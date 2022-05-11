package frc.robot.commands;

import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class DrivetrainDrive extends CommandBase {
    private Drivetrain drivetrain;
    private Gamepad gamepad;

    public DrivetrainDrive(Drivetrain drivetrain, Gamepad gamepad) {
        this.drivetrain = drivetrain;
        this.gamepad = gamepad;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        double speed = gamepad.getRightTrigger() - gamepad.getLeftTrigger();
        double turn = gamepad.getLeftX();

        drivetrain.arcadeDrive(speed, turn);
    }
}
