package frc.robot.commands.Elevator;

import frc.robot.subsystems.Elevator;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class ElevatorToHeight extends CommandBase {

    private final Elevator elevator;
    private final double height;

    public ElevatorToHeight(Elevator elevator, double height) {
        this.elevator = elevator;
        this.height = height;
        addRequirements(elevator);
    }

    public final ElevatorToHeight untilReady() {
        return this;
    }

    @Override
    public void initialize() {
        elevator.setTargetHeight(height);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}