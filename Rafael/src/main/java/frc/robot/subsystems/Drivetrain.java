package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.math.SLMath;

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

    // Stops drivetrain from moving
    public void stop() {
        drivetrain.stopMotor();
    }

    // Drives using tank drive
    public void tankDrive(double left, double right) {
        drivetrain.tankDrive(left, right, false);
    }

    // Drives using arcade drive
    public void arcadeDrive(double speed, double rotation) {
        drivetrain.arcadeDrive(speed, rotation, false);
    }

    // Drives using curvature drive algorithm
    public void curvatureDrive(double xSpeed, double zRotation, double baseTS) {
        // Clamp all inputs to valid values;
        xSpeed = SLMath.clamp(xSpeed, -1.0, 1.0);
        zRotation = SLMath.clamp(zRotation, -1.0, 1.0);
        baseTS = SLMath.clamp(baseTS, 0.0, 1.0);

        // Find the amount to slow down turning by.
        // This is proportional to the speed but has a base value
        // that it starts from (allows turning in place)
        double turnAdj = Math.max(baseTS, Math.abs(xSpeed));

        // Find the speeds of the left and right wheels
        double lSpeed = xSpeed + zRotation * turnAdj;
        double rSpeed = xSpeed - zRotation * turnAdj;

        // Find the maximum output of the wheels, so that if a wheel tries to go > 1.0
        // it will be scaled down proportionally with the other wheels.
        double scale = Math.max(1.0, Math.max(Math.abs(lSpeed), Math.abs(rSpeed)));

        lSpeed /= scale;
        rSpeed /= scale;

        // Feed the inputs to the drivetrain
        drivetrain.tankDrive(lSpeed, rSpeed, false);
    }

    // Drives using curvature drive algorithm with automatic quick turn
    public void curvatureDrive(double xSpeed, double zRotation) {
        this.curvatureDrive(xSpeed, zRotation, 0.4);
    }
}
