package frc.robot.commands;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.math.SLMath;
import com.stuypulse.stuylib.streams.*;
import com.stuypulse.stuylib.streams.filters.*;

import static frc.robot.Constants.Elevator.*;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

/**
 * This command moves the elevator based on the left stick of the operator
 * 
 * The Gamepad is interacted through a Stream, which may be unusual
 */
public class ElevatorDefaultCommand extends CommandBase {
    
    private final Elevator elevator;

    private IStream stream;

    public ElevatorDefaultCommand(Elevator elevator, Gamepad operator) {
        this.elevator = elevator;
        
        // chad code
        stream = operator::getLeftY;
        stream = stream.filtered(
            SLMath::square,
            x -> x * 0.4,
            x -> x - 0.16,
            x -> SLMath.clamp(x, 0.7),
        
            new LowPassFilter(LOW_PASS_RC)
        );

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.move(stream.get());
    }
    
    @Override
    public boolean isFinished() {
        return false;
    }

}