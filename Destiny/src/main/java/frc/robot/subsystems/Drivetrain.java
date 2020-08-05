/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// Get the constant values relevent to us
import frc.robot.Constants.Ports;

public class Drivetrain extends SubsystemBase {

    private DifferentialDrive drivetrain;

    /**
     * Initialize Drivetrain subsystem
     */
    public Drivetrain() {
        // Make all the motors with the correct port numbers
        WPI_TalonSRX frontLeft = new WPI_TalonSRX(Ports.Drivetrain.FRONT_LEFT);
        WPI_TalonSRX frontRight = new WPI_TalonSRX(Ports.Drivetrain.FRONT_RIGHT);
        WPI_TalonSRX backLeft = new WPI_TalonSRX(Ports.Drivetrain.BACK_LEFT);
        WPI_TalonSRX backRight = new WPI_TalonSRX(Ports.Drivetrain.BACK_RIGHT);
        
        // Create two SpeedControllerGroups for the left and right side
        SpeedControllerGroup left = new SpeedControllerGroup(frontLeft, backLeft);
        SpeedControllerGroup right = new SpeedControllerGroup(frontRight, backRight);

        // Make the drivetrain
        drivetrain = new DifferentialDrive(left, right);
    }

    // This function gets called 50 times a second
    @Override
    public void periodic() {
        // Nothing is called here as the drivetrain is relatively simple
    }

    public void tankDrive(double leftSpeed, double rightSpeed) {
        drivetrain.arcadeDrive(leftSpeed, rightSpeed);
    }
    
    public void arcadeDrive(double speed, double turn) {
        drivetrain.arcadeDrive(speed, turn);
    }
}
