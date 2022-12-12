package frc.robot.commands.Elevator;

import frc.robot.subsystems.Elevator;
import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.util.StopWatch;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

public class ElevatorDrive extends CommandBase {
    
    private final Elevator elevator;
    private final IStream velocity;

    private final StopWatch timer;

    public ElevatorDrive(Elevator elevator, Gamepad gamepad) {
        this.elevator = elevator;

        velocity = IStream.create(gamepad::getLeftY)
            .filtered(x -> x * Constants.Elevator.SPEED);
        
        timer = new StopWatch();

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.addTargetHeight(velocity.get() * timer.reset());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}