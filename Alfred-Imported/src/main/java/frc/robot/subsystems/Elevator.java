package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.stuypulse.stuylib.network.SmartBoolean;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Elevator.*;

/**
 * Elevator's purpose is to move the intake and grabber vertically.
 * 
 * It tilts away and into the robot via a solenoid.
 * It also has a break to lock the intake and grabber.
 * 
 * FIXME: the brake lock is currently unsafe / untested
 */
public class Elevator extends SubsystemBase {
    
    private final SmartBoolean usingLimitSwitch;

    private WPI_TalonSRX master;
    private WPI_VictorSPX follower;

    // Prevent the grabber/intake from falling after lifting
    private Solenoid brake;

    // Controls the rotation of the elevator (because it can tilt away from the robot)
    private DoubleSolenoid tiltLock;

    // Limit switch to detect grabber/intake at the bottom of the elevator
    private DigitalInput bottomSensor;

    public Elevator() {
        master = new WPI_TalonSRX(Ports.MASTER);
        follower = new WPI_VictorSPX(Ports.FOLLOWER);
        
        configureMotors();
        resetEncoder();

        brake = new Solenoid(PneumaticsModuleType.CTREPCM, Ports.BRAKE);
        tiltLock = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Ports.TILT_A, Ports.TILT_B);

        bottomSensor = new DigitalInput(Ports.LIMIT_SWITCH);

        usingLimitSwitch = new SmartBoolean(SMART_DASHBOARD_INDEX, true);

        releaseBrake();
        tiltForward();
    }

    // Setup encoder and master/follower setup
    private void configureMotors() {
        follower.follow(master);

        master.setNeutralMode(NeutralMode.Brake);
        follower.setNeutralMode(NeutralMode.Brake);

        master.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    }

    // Reset encoder
    public void resetEncoder() {
        master.setSelectedSensorPosition(0,0,0);
    }

    // Raw encoder reading
    private double getRawEncoderReading() {
        return master.getSelectedSensorPosition();
    }

    // Stop by turning off motor and braking
    public void stop() {
        move(0);
        enableBrake();
    }

    // Apply algorithm to get height
    public double getHeight() {
        return getRawEncoderReading() * ENCODER_RAW_MULTIPLIER * HEIGHT_MULTIPLIER;
    }

    // private void setHeight(double height)

    // Release brake and move unless you are at bottom and attempting to go down
    // TODO: figure out what was done with the proportionality thing in alfred
    // TODO: dont move when elevator is at top
    public void move(double speed) {
        if (isAtBottom() && speed < 0) {
            stop();
            return;
        }

        releaseBrake();
        master.set(speed);
    }

    // Brake elevator
    public void enableBrake() {
        if (!isBraked()) {
            brake.set(BRAKED);
        }
    }

    // Release brake
    public void releaseBrake() {
        if (isBraked()) {
            brake.set(!BRAKED);
        }
    }

    // Check state of elevator brake
    public boolean isBraked() {
        return brake.get() == BRAKED;
    }

    // Tilt forward
    public void tiltForward() {
        if (!isTilted()) {
            tiltLock.set(TILT_FORWARD);
        }
    }

    // Tilt back
    public void tiltBack() {
        if (isTilted()) {
            tiltLock.set(TILT_BACK);
        }
    }

    // Is tilted forward
    public boolean isTilted() {
        return tiltLock.get() == TILT_FORWARD;
    }

    // Return the reading from the limit switch
    public boolean isAtBottom() {
        if (!usingLimitSwitch.get()) {
            return false;
        }

        return !bottomSensor.get();
    }

}