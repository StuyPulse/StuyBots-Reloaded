package frc.robot.commands;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.streams.*;
import com.stuypulse.stuylib.streams.filters.*;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Elevator;

/**
 * This command moves the elevator based on the left stick of the operator
 */
public class ElevatorDefaultCommand extends CommandBase {
    
    private final Elevator elevator;
    private final Gamepad operator;

    private IStream stream;

    public ElevatorDefaultCommand(Elevator elevator, Gamepad operator) {
        this.elevator = elevator;
        this.operator = operator;

        stream = () -> operator.getLeftY();

        // TODO: create constants for this and commments
        stream = stream.filtered(new LowPassFilter(0.5));

        addRequirements(elevator);
    }

    @Override
    public void execute() {
        double moveSpeed = stream.get();
        
        elevator.move(moveSpeed);
    }
    
    @Override
    public boolean isFinished() {
        return false;
    }

}