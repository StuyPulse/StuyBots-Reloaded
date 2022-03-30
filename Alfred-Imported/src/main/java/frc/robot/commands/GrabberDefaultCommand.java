package frc.robot.commands;

import com.stuypulse.stuylib.input.Gamepad;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Grabber;

public class GrabberDefaultCommand extends CommandBase {
    private final Grabber grabber;
    private final Gamepad gamepad;

    public GrabberDefaultCommand(Grabber grabber, Gamepad gamepad) {
        this.grabber = grabber;
        this.gamepad = gamepad;

        addRequirements(grabber);
    }

    @Override
    public void execute() {
        if (gamepad.getRawBottomButton()) {
            grabber.extend();
        } else {
            grabber.retract();
        }

        if (gamepad.getRawRightButton()) {
            grabber.close();
        } else {
            grabber.open();
        }
    }
}
