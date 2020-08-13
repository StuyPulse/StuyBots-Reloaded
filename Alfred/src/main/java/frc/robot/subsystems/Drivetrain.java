package frc.robot.subsystems;

import java.util.Arrays;

import javax.sound.sampled.Port;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;
import frc.robot.Constants.Ports;
import com.stuypulse.stuylib.math.Angle;
import com.stuypulse.stuylib.util.TankDriveEncoder;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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
        // 
        leftMotors = new CANSparkMax[] {
            new CANSparkMax(Ports.Drivetrain.LEFT_TOP, MotorType.kBrushless),
            new CANSparkMax(Ports.Drivetrain.LEFT_MIDDLE, MotorType.kBrushless),
            new CANSparkMax(Ports.Drivetrain.LEFT_BOTTOM, MotorType.kBrushless)
        };
        
        rightMotors = new CANSparkMax[] {
            new CANSparkMax(Ports.Drivetrain.RIGHT_TOP, MotorType.kBrushless),
            new CANSparkMax(Ports.Drivetrain.RIGHT_MIDDLE, MotorType.kBrushless),
            new CANSparkMax(Ports.Drivetrain.RIGHT_TOP, MotorType.kBrushless),
        };

        configureMotors();

        drivetrain = new DifferentialDrive(
            makeControllerGroup(leftMotors), 
            makeControllerGroup(rightMotors)
        );
        
        // TODO: why?
        drivetrain.setSafetyEnabled(false); 

        // Gearshift
        gearShift = new Solenoid(Ports.GEAR_SHIFT);    
        setGear(Gear.LOW);
        
        // NAV X
        navX = new AHRS(SPI.Port.kMXP);
        
        // NEO Encoders
        leftNEO = leftMotors[1].getEncoder();
        rightNEO = rightMotors[1].getEncoder();

        // Greyhill Encoders
        Encoder leftGreyhill = new Encoder(Ports.Drivetrain.Greyhill.LEFT_A, Ports.Drivetrain.Greyhill.LEFT_B);
        Encoder rightGreyhill = new Encoder(Ports.Drivetrain.Greyhill.RIGHT_A, Ports.Drivetrain.Greyhill.RIGHT_B);
    
        leftGreyhill.setDistancePerPulse(Constants.Drivetrain.Greyhill.INCHES_PER_PULSE);
        rightGreyhill.setDistancePerPulse(-1.0 * Constants.Drivetrain.Greyhill.INCHES_PER_PULSE);
        
        // Wrap up greyhills in TankDriveEncoder Class
        greyhills = new TankDriveEncoder(leftGreyhill, rightGreyhill);
    }

    private void configureMotors() {
        // TODO: figure out why one was in brake
        for(CANSparkMax motor : leftMotors) {
            motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
            motor.setInverted(true);
            motor.setSmartCurrentLimit(Constants.Drivetrain.CURRENT_LIMIT);
        }

        for(CANSparkMax motor : rightMotors) {
            motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
            motor.setInverted(true);
            motor.setSmartCurrentLimit(Constants.Drivetrain.CURRENT_LIMIT);
        }
    }

    public void setGear(Gear gear) {
        gearShift.set(gearToSolenoid((this.gear = gear)));
    }

    
    /**
     * @return the navx on the drivetrain used for positioning
     */
    public AHRS getNavX() {
        return navX;
    }

    /**
     * @return get the angle of the robot
     */
    public Angle getGyroAngle() {
        return Angle.fromDegrees(navX.getAngle());
    }
    
    /**
     * @return DifferentialDrive class based on current gear
     */
    public DifferentialDrive getCurrentDrive() {
        return drivetrain;
    }

    /**
     * Stops drivetrain from moving
     */
    public void stop() {
        tankDrive(0, 0);
    }

    /**
     * Drives using tank drive
     * 
     * @param left  speed of left side
     * @param right speed of right side
     */
    public void tankDrive(double left, double right) {
        getCurrentDrive().tankDrive(left, right, false);
    }

    /**
     * Drives using arcade drive
     * 
     * @param speed    speed of drive train
     * @param rotation amount that it is turning
     */
    public void arcadeDrive(double speed, double rotation) {
        getCurrentDrive().arcadeDrive(speed, rotation, false);
    }

    /**
     * Drives using curvature drive algorithm
     * 
     * @param speed     speed of robot
     * @param rotation  amount that it turns
     * @param quickturn overrides constant curvature
     */
    public void curvatureDrive(double speed, double rotation, boolean quickturn) {
        getCurrentDrive().curvatureDrive(speed, rotation, quickturn);
    }
}