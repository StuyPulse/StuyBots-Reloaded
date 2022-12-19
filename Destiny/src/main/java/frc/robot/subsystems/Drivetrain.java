/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.stuypulse.stuylib.control.feedback.PIDController;
import com.stuypulse.stuylib.math.SLMath;

// Get the constant values relevent to us
import frc.robot.Constants.Ports;

public class Drivetrain extends SubsystemBase {

    private DifferentialDrive drivetrain;
    private final MotorControllerGroup left;
    private final MotorControllerGroup right;

    /**
     * Initialize Drivetrain subsystem
     */
    public Drivetrain() {
        // Make all the motors with the correct port numbers
        WPI_TalonSRX frontLeft = new WPI_TalonSRX(Ports.Drivetrain.FRONT_LEFT);
        WPI_TalonSRX frontRight = new WPI_TalonSRX(Ports.Drivetrain.FRONT_RIGHT);
        WPI_TalonSRX backLeft = new WPI_TalonSRX(Ports.Drivetrain.BACK_LEFT);
        WPI_TalonSRX backRight = new WPI_TalonSRX(Ports.Drivetrain.BACK_RIGHT);
        
        // Invert the motors
        frontLeft.setInverted(true);
        frontRight.setInverted(true);
        backLeft.setInverted(true);
        backRight.setInverted(true);

        // Create two array for motors for the left and right side
        left = new MotorControllerGroup(frontLeft, frontRight);
        right = new MotorControllerGroup(backLeft, backRight);

        // Make the drivetrain
        drivetrain = new DifferentialDrive(left, right);

    }

    // This function gets called 50 times a second
    @Override
    public void periodic() {
        // Nothing is called here as the drivetrain is relatively simple
    }

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
        this.curvatureDrive(xSpeed, zRotation, 0.8);
    }

}
