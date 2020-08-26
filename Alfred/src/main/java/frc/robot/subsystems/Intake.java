package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Intake.*;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.IFilter;

/*
 * Intake is used to capture balls and hold them. It can hold one ball at a time. 
 * 
 * The intake values support ramping because thats what the original did.
 * 
 * It's intended use is for instant command bindings.
*/
public class Intake extends SubsystemBase {

    // Target speed the intake wants to run at
    private double target;

    // Filter / Stream combo for ramping
    private IFilter ramp; 
    private final IStream rampedSpeed = () -> target;

    // Motor + Encoder
    private CANSparkMax motor;
    private CANEncoder encoder;

    public Intake() {
        motor = new CANSparkMax(Ports.MOTOR, MotorType.kBrushless);
        encoder = motor.getEncoder();

        // Disable Ramping
        disableRamping();

        // configure motors
        motor.setIdleMode(IdleMode.kBrake);
    }

    // Update Motor with ramped speed
    public void periodic() {
        motor.set(rampedSpeed.get());
    }

    // Enable / Disable Ramping
    public Intake setFilter(IFilter filter) {
        ramp = (filter == null) ? (x -> x) : (filter);
        return this;
    }

    public Intake enableRamping() {
        return setFilter(getDefaultFilter());
    }

    public Intake disableRamping() {
        return setFilter(null);
    }

    // Subsystem controls
    public void acquire() {
        // motor.set(ACQUIRE_SPEED);
        target = ACQUIRE_SPEED;
    }

    public void deacquire() {
        // motor.set(DEACQUIRE_SPEED);
        target = DEACQUIRE_SPEED;
    }

    public void stop() {
        target = STOP_SPEED;
    }

    // if there is a ball present, the motor's speed should be zero,
    // this is how to tell if a ball is present
    public double getVelocity() {
        // it might be a good idea to filter
        return encoder.getVelocity();
    }

}