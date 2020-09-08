
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import com.stuypulse.stuylib.math.Angle;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Drivetrain.Encoders;
import frc.robot.Constants.Drivetrain.Ports;

public class Drivetrain extends SubsystemBase {

    // Enum to store the current state of the gearbox
    public enum Gear {
        HIGH(true), LOW(false);

        private boolean solenoid;

        private Gear(boolean solenoid) {
            this.solenoid = solenoid;
        }

        public boolean getValue() {
            return solenoid;
        }
    }

    private WPI_VictorSPX leftTopMotor;
    private WPI_VictorSPX leftMiddleMotor;
    private WPI_TalonSRX leftBottomMotor;

    private WPI_VictorSPX rightTopMotor;
    private WPI_VictorSPX rightMiddleMotor;
    private WPI_TalonSRX rightBottomMotor;

    private DifferentialDrive motors;

    private AHRS gyro;
    private Solenoid gearSolenoid;
    private Gear gear;

    public Drivetrain() {
        // Create and configure motors
        leftTopMotor = new WPI_VictorSPX(Ports.LEFT_TOP);
        leftMiddleMotor = new WPI_VictorSPX(Ports.LEFT_MIDDLE);
        leftBottomMotor = new WPI_TalonSRX(Ports.RIGHT_BOTTOM);

        rightTopMotor = new WPI_VictorSPX(Ports.RIGHT_TOP);
        rightMiddleMotor = new WPI_VictorSPX(Ports.RIGHT_MIDDLE);
        rightBottomMotor = new WPI_TalonSRX(Ports.RIGHT_BOTTOM);

        configureMotors();

        // Setup encoders
        leftBottomMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        rightBottomMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        motors = new DifferentialDrive(
            new SpeedControllerGroup(leftTopMotor, leftMiddleMotor, leftBottomMotor),
            new SpeedControllerGroup(rightTopMotor, rightMiddleMotor, rightBottomMotor)
        );

        // Sensors and gear shifting
        gearSolenoid = new Solenoid(Ports.GEAR_SHIFT);
        setGear(Gear.LOW);

        gyro = new AHRS(SPI.Port.kMXP);
        resetGyro();

    }

    private void configureMotors() {
        // Potentially add settings for these in contants
        leftMiddleMotor.follow(leftBottomMotor);
        leftTopMotor.follow(leftBottomMotor);
        rightMiddleMotor.follow(rightBottomMotor);
        rightTopMotor.follow(rightBottomMotor);

        rightTopMotor.setInverted(true);
        rightMiddleMotor.setInverted(true);
        rightBottomMotor.setInverted(true);
        leftTopMotor.setInverted(true);
        leftMiddleMotor.setInverted(true);
        leftBottomMotor.setInverted(true);

        leftTopMotor.setNeutralMode(NeutralMode.Brake);
        leftMiddleMotor.setNeutralMode(NeutralMode.Brake);
        leftBottomMotor.setNeutralMode(NeutralMode.Brake);
        rightTopMotor.setNeutralMode(NeutralMode.Brake);
        rightMiddleMotor.setNeutralMode(NeutralMode.Brake);
        rightBottomMotor.setNeutralMode(NeutralMode.Brake);
    }

    // Gear Related Things
    public Gear getGear() {
        return this.gear;
    }

    public void setGear(Gear gear) {
        gearSolenoid.set((this.gear = gear).getValue());
    }

    // Gyro related things
    public Angle getAngle() {
        return Angle.fromDegrees(gyro.getAngle());
    }

    public AHRS getGyro() {
        return gyro;
    }

    public void resetGyro() {
        gyro.reset();
    }

    // Encoder related things
    public double getLeftDistance() {
        return Encoders.LEFT_GAIN * leftBottomMotor.getSelectedSensorPosition();
    }

    public double getRightDistance() {
        return Encoders.RIGHT_GAIN * rightBottomMotor.getSelectedSensorPosition();
    }

    public double getDistance() {
        return ((getLeftDistance() + getRightDistance()) / 2.0) * Encoders.RAW_MULTIPLIER;
    }

    public void resetEncoders() {
        leftBottomMotor.setSelectedSensorPosition(0);
        rightBottomMotor.setSelectedSensorPosition(0);
    }

    // Driving control
    public void stop() {
        tankDrive(0, 0);
    }

    public void arcadeDrive(double speed, double rotation) {
        motors.arcadeDrive(speed, rotation, false);
    }

    public void tankDrive(double lSpeed, double rSpeed) {
        motors.tankDrive(lSpeed, rSpeed, false);
    }
}