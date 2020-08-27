package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Drivetrain;

/**
 * This command toggles the Gear Shift of the drivetrain
 */
public class DrivetrainToggleGearCommand extends InstantCommand {
     
    private final Drivetrain drivetrain;
    
    public DrivetrainToggleGearCommand(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
    }

    public void initialize() {
        Drivetrain.Gear gear = drivetrain.getGear();

        if(gear.equals(Drivetrain.Gear.HIGH)) {
            drivetrain.setGear(Drivetrain.Gear.LOW);
        } else {
            drivetrain.setGear(Drivetrain.Gear.HIGH);
        }
    }
}