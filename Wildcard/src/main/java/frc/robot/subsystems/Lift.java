
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Lift.Encoders;
import frc.robot.Constants.Lift.Ports;

public class Lift extends SubsystemBase {

    // Motors
    private WPI_TalonSRX master, follower;
    private WPI_VictorSPX left, right;

    // Limit switches (top / bottom detector)
    private DigitalInput top, bottom;

    public Lift() {
        // Motors
        master = new WPI_TalonSRX(Ports.INNER_LEFT_MOTOR);
        follower = new WPI_TalonSRX(Ports.INNER_RIGHT_MOTOR);

        left = new WPI_VictorSPX(Ports.OUTER_LEFT_MOTOR);
        right = new WPI_VictorSPX(Ports.OUTER_RIGHT_MOTOR);

        configureMotors();

        // Encoders
        follower.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        master.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        // Sensors
        top = new DigitalInput(Ports.TOP_LIMIT_SWITCH);
        bottom = new DigitalInput(Ports.BOTTOM_LIMIT_SWITCH);

    }

    private void configureMotors() {
        // Everything follows the master
        follower.follow(master);
        left.follow(master);
        right.follow(master);

        // Brake mode
        follower.setNeutralMode(NeutralMode.Brake);
        master.setNeutralMode(NeutralMode.Brake);
        left.setNeutralMode(NeutralMode.Brake);
        right.setNeutralMode(NeutralMode.Brake);
    }

    // Movement
    public void move(double speed) {
        // Safety checks
        if ((isAtBottom() && speed < 0) || (isAtTop() && speed > 0)) {
            stop();
            return;
        }

        master.set(ControlMode.PercentOutput, speed);
    }

    public void stop() {
        master.stopMotor();
    }

    // Sensors (limit switches)
    public boolean isAtBottom() {
        return !bottom.get();
    }

    public boolean isAtTop() {
        return !top.get();
    }

    // Encoders
    public void resetEncoders() {
        master.setSelectedSensorPosition(0);
        follower.setSelectedSensorPosition(0);
    }

    // TODO: check if the multipliers were used correctly
    public double getHeight() {
        return Encoders.RAW_MULTIPLIER * (master.getSelectedSensorPosition() + follower.getSelectedSensorPosition()) / 2.0;
    }

}