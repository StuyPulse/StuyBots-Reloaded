
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import static frc.robot.Constants.Drivetrain.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
    
    private WPI_VictorSPX leftTopMotor;
    private WPI_VictorSPX leftMiddleMotor;
    private WPI_TalonSRX leftBottomMotor;
    
    private WPI_VictorSPX rightTopMotor;
    private WPI_VictorSPX rightMiddleMotor;
    private WPI_TalonSRX rightBottomMotor;

    public Drivetrain() {
    }

}