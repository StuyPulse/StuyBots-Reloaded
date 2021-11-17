package frc.robot.subsystems;

import java.util.Arrays;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import static frc.robot.Constants.Drivetrain.*;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.util.TankDriveEncoder;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * This is a Drivetrain implementation for Alfred
 * 
 * Couple things to note about this implementation:
 *  - The encoders ONLY use the greyhills, not the built in NEOs
 *  - The drivetrain WILL NOT disable one of the motors in high gear
 * 
 * The reason for not using the NEOs is that the NEOs are behind the gear box.
 * This means what when shifting gears, the values of the NEOs break.
 * 
 * These things were made to simplify the implementation 
 */
public class Drivetrain extends SubsystemBase {

    // Turn a list of speed controllers into a speed controller group
    private static SpeedControllerGroup makeControllerGroup(SpeedController... controllers) {
        return new SpeedControllerGroup(controllers[0], Arrays.copyOfRange(controllers, 1, controllers.length));
    }
    
    // Convert Enum into Boolean
    private static boolean gearToSolenoid(Gear gear) {
        return gear == Gear.HIGH;
    }

    // Enum to store the current state of the gearbox
    public enum Gear {
        HIGH, LOW
    }

    // The NEO Encoders for the Drivetrain
    private CANEncoder leftNEO;
    private CANEncoder rightNEO;

    // Class that handles the greyhill encoders
    private TankDriveEncoder greyhills;

    // Gyroscope
    private AHRS navX;

    // Gear shifting
    private Gear gear;
    private Solenoid gearShift;
    
    // Arrays to store all of the motors
    // Arrays make configuring the motors easier
    private CANSparkMax[] leftMotors;
    private CANSparkMax[] rightMotors;
    
    // Drivetrain
    private DifferentialDrive drivetrain;

    public Drivetrain() {
        // Init
        leftMotors = new CANSparkMax[] {
            new CANSparkMax(Ports.LEFT_TOP, MotorType.kBrushless),
            new CANSparkMax(Ports.LEFT_MIDDLE, MotorType.kBrushless),
            new CANSparkMax(Ports.LEFT_BOTTOM, MotorType.kBrushless)
        };
        
        rightMotors = new CANSparkMax[] {
            new CANSparkMax(Ports.RIGHT_TOP, MotorType.kBrushless),
            new CANSparkMax(Ports.RIGHT_MIDDLE, MotorType.kBrushless),
            new CANSparkMax(Ports.RIGHT_TOP, MotorType.kBrushless),
        };

        configureMotors();

        drivetrain = new DifferentialDrive(
            makeControllerGroup(leftMotors), 
            makeControllerGroup(rightMotors)
        );
        
        // TODO: why?
        drivetrain.setSafetyEnabled(SAFTEY_ENABLED); 

        // Gearshift
        gearShift = new Solenoid(Ports.GEAR_SHIFT);    
        setGear(Gear.LOW);
        
        // NAV X
        navX = new AHRS(SPI.Port.kMXP);
        
        // NEO Encoders
        leftNEO = leftMotors[1].getEncoder();
        rightNEO = rightMotors[1].getEncoder();

        // Greyhill Encoders
        Encoder leftGreyhill = new Encoder(Ports.Greyhill.LEFT_A, Ports.Greyhill.LEFT_B);
        Encoder rightGreyhill = new Encoder(Ports.Greyhill.RIGHT_A, Ports.Greyhill.RIGHT_B);
    
        leftGreyhill.setDistancePerPulse(Greyhill.INCHES_PER_PULSE);
        rightGreyhill.setDistancePerPulse(-1.0 * Greyhill.INCHES_PER_PULSE);
        
        // Wrap up greyhills in TankDriveEncoder Class
        greyhills = new TankDriveEncoder(leftGreyhill, rightGreyhill);
    }

    private void configureMotors() {
        for(CANSparkMax motor : leftMotors) {
            motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
            motor.setInverted(true);
            motor.setSmartCurrentLimit(CURRENT_LIMIT);
        }

        for(CANSparkMax motor : rightMotors) {
            motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
            motor.setInverted(true);
            motor.setSmartCurrentLimit(CURRENT_LIMIT);
        }
    }

    // Gear Related Things
    public Gear getGear() {
        return this.gear;
    }

    public void setGear(Gear gear) {
        gearShift.set(gearToSolenoid((this.gear = gear)));
    }
    
    // Encoder Information
    // NAVX
    public AHRS getNavX() {
        return navX;
    }

    public Angle getGyroAngle() {
        return Angle.fromDegrees(navX.getAngle());
    }
    
    // Greyhills
    public void resetEncoders() {
        greyhills.reset();
    }

    public double getDistance() {
        return greyhills.getDistance();
    }
    
    public double getLeftDistance() {
        return greyhills.getLeftDistance();
    }
    
    public double getRightDistance() {
        return greyhills.getRightDistance();
    }

    // Drivetrain Control
    public DifferentialDrive getCurrentDrive() {
        return drivetrain;
    }

    public void stop() {
        tankDrive(0, 0);
    }


    public void tankDrive(double left, double right) {
        getCurrentDrive().tankDrive(left, right, false);
    }


    public void arcadeDrive(double speed, double rotation) {
        getCurrentDrive().arcadeDrive(speed, rotation, false);
    }

    public void curvatureDrive(double speed, double rotation, boolean quickturn) {
        getCurrentDrive().curvatureDrive(speed, rotation, quickturn);
    }
}