package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.stuypulse.stuylib.control.Controller;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.control.feedforward.Feedforward;
import com.stuypulse.stuylib.network.SmartBoolean;
import com.stuypulse.stuylib.network.SmartNumber;
import com.stuypulse.stuylib.streams.filters.MotionProfile;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import static frc.robot.Constants.Elevator.*;
import static frc.robot.Constants.Elevator.PID.*;
import static frc.robot.Constants.Elevator.FF.*;
import static frc.robot.Constants.Elevator.MotionProfile.*;



public class Elevator extends SubsystemBase {

    // Motors
    private WPI_TalonSRX master;
    private WPI_VictorSPX follower;

    // Solenoids
    private Solenoid brake; // Prevent the grabber/intake from falling after lifting
    private DoubleSolenoid tiltLock; // Controls the rotation of the elevator (because it can tilt away from the robot)

    // Sensors
    private SmartBoolean usingLimitSwitch;
    private DigitalInput bottomLimitSwitch; // Limit switch to detect grabber/intake at the bottom of the elevator

    // Control
    private final Controller position;
	private final SmartNumber targetHeight;


    public Elevator() {
        master = new WPI_TalonSRX(Ports.MASTER);
        follower = new WPI_VictorSPX(Ports.FOLLOWER);
        
        configureMotors();
        resetEncoder();

        brake = new Solenoid(PneumaticsModuleType.CTREPCM, Ports.BRAKE);
        tiltLock = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Ports.TILT_A, Ports.TILT_B);

        bottomLimitSwitch = new DigitalInput(Ports.LIMIT_SWITCH);
        usingLimitSwitch = new SmartBoolean("Elevator/Using Limit Switch", true);

        position = new PIDController(kP, kI, kD)
            .add(new Feedforward.Elevator(kG, kS, kV, kA).position())
            .setOutputFilter(new MotionProfile(VEL_LIMIT, ACCEL_LIMIT));
		targetHeight = new SmartNumber("Elevator Target Height", MIN_HEIGHT);

        releaseBrake();
        tiltForward();
    }

    // TODO: REMOVE !!!!
    private void configureMotors() {
        follower.follow(master);

        master.setNeutralMode(NeutralMode.Brake);
        follower.setNeutralMode(NeutralMode.Brake);

        master.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    }

    public void resetEncoder(){
        master.setSelectedSensorPosition(0, 0, 0);
    }

    public void move(double speed) {
        if (isAtBottom() && speed < 0) {
            stop();
            return;
        }

        releaseBrake();
        master.set(speed);
    }

    // Stop by turning off motor and braking
    public void stop() {
        move(0);
        enableBrake();
    }


    // TODO: CHECK IF DIV by 60
    public double getVelocity(){ 
        return master.getSelectedSensorVelocity() * -Constants.Elevator.Encoder.ENCODER_MULTIPLIER / 60.0;
    }

    public double getHeight() {
        return master.getSelectedSensorVelocity() * -Constants.Elevator.Encoder.ENCODER_MULTIPLIER;
    }

    

    // TODO: Check if inverted
    public boolean atBottom() {
        return bottomLimitSwitch.get();
    }
    
    private void setEncoder(double heightMeters) {
		master.setSelectedSensorPosition(heightMeters / ENCODER_RAW_MULTIPLIER);
	}

    private void setVoltage(double voltage) {
		if (atBottom() && voltage < 0) {
			DriverStation.reportWarning("Bottom Limit Switch reached", false);

			setEncoder(MIN_HEIGHT);
			
			voltage = 0.0;
		} 

		master.setVoltage(voltage);
		follower.setVoltage(voltage);
	}
    
    public void releaseBrake(){
        if(!isBraked()){
            brake.set(false);
        }
    }

    public void enableBrake(){
        if(isBraked()){
            brake.set(true);
        }
    }
    
    public boolean isBraked(){
        return brake.get() == BRAKED;
    }

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
    
    public boolean isTilted(){
        return tiltLock.get() == TILT_BACK;
    }

    
    
    public boolean isAtBottom() {
        if (!usingLimitSwitch.get()) {
            return false;
        }

        return !bottomLimitSwitch.get();
    }
    public double getTargetHeight(){
        return targetHeight.get();
    }

    public void setTargetHeight(double height){
        targetHeight.set(height);
    }

    @Override
    public void periodic() {
        setVoltage(position.update(getHeight(), getTargetHeight()));
        if (Constants.DEBUG_MODE.get()) {
            SmartDashboard.putBoolean("Elevator/At Bottom?", isAtBottom());
        }
    }
    

}