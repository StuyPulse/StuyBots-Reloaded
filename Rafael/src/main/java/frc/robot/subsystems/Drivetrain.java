package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import static frc.robot.Constants.Drivetrain.*;

public class Drivetrain extends SubsystemBase {

    public static enum Gear {
        HIGH(true), LOW(false);

        private final boolean value;

        private Gear(boolean value) {
            this.value = value;
        }

        public boolean getValue() {
            return value;
        }
    };

    // TODO: check motor type with engineering
    // old motor class does not exist anymore
    private WPI_TalonSRX topLeft;
    private WPI_TalonSRX topRight;

    private WPI_TalonSRX bottomLeft;
    private WPI_TalonSRX bottomRight;

    private DifferentialDrive drivetrain;

    private Solenoid gearShift;
    private Gear currentGear;

    private AHRS gyro;

    public Drivetrain() {

        // initialize motors & drive
        topLeft = new WPI_TalonSRX(Ports.LEFT_TOP);
        topRight = new WPI_TalonSRX(Ports.RIGHT_TOP);

        bottomLeft = new WPI_TalonSRX(Ports.LEFT_BOTTOM);
        bottomRight = new WPI_TalonSRX(Ports.RIGHT_BOTTOM);

        drivetrain = new DifferentialDrive(
            new SpeedControllerGroup(topLeft, bottomLeft),
            new SpeedControllerGroup(topRight, bottomRight)
        );

        configureMotors();

        // initialize gear shifting
        gearShift = new Solenoid(Ports.GEAR_SHIFT);
        setGear(Gear.LOW);

        // sensors (gyro, encoder)
        gyro = new AHRS(SPI.Port.kMXP);
        resetGyro();
    }

    private void configureMotors() {
        topLeft.setInverted(true);
        bottomLeft.setInverted(true);
        bottomRight.setInverted(true);
        topRight.setInverted(true);
        
        // TODO: set brake mode
    }

    // Gear Interface
    public void setGear(Gear newGear) {
        gearShift.set((this.currentGear = newGear).getValue());
    }

    public Gear getGear() {
        return currentGear;
    }
    
    // Gyro Interface
    public void resetGyro() {
        gyro.reset();
    }

    public AHRS getGyro() {
        return gyro;
    }

    public Angle getAngle() {
        return Angle.fromDegrees(gyro.getAngle());
    }

    // Drivetrain driving interface
    public void arcadeDrive(double xSpeed, double zRotation) {
        drivetrain.arcadeDrive(xSpeed, zRotation, false);
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        drivetrain.tankDrive(leftSpeed, rightSpeed, false);
    }

    public void stop() {
        tankDrive(0, 0);
    }

}
