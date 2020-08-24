package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Intake.*;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/*
Intake can hold one ball at a time. 
It's intended use is for instant command bindings.

If ramping is replaced with filtering, the subsystem
should have a default command and target speeds.
*/
public class Intake extends SubsystemBase {

    private CANSparkMax motor;
    private CANEncoder encoder;

    // encoder = motor.getEncoder();

    public Intake() {
        motor = new CANSparkMax(Ports.MOTOR, MotorType.kBrushless);
        encoder = motor.getEncoder();

        // configure motors
        motor.setIdleMode(IdleMode.kBrake);
    }

    public void acquire() {
        motor.set(ACQUIRE_SPEED);
    }

    public void deaquire() {
        motor.set(DEACQUIRE_SPEED);
    }

    public void stop() {
        motor.stopMotor();
    }

    /*
     * 
     * FIXME: we don't have to do ramping
     * 
     * we could have filtering in a periodic() call or a default command where:
     * 
     * setMotorSpeed(m_filter.get(getMotorSpeed()))
     * 
     */

    // TODO: WHY RAMPING AND WHY NOT CONSTANT
    public void enableRamping() {
        motor.setOpenLoopRampRate(0.5);
    }

    public void disableRamping() {
        motor.setOpenLoopRampRate(0.0);
    }

    // if there is a ball present, the motor's speed should be zero,
    // this is how to tell if a ball is present
    public double getVelocity() {
        // it might be a good idea to filter
        return encoder.getVelocity();
    }

}