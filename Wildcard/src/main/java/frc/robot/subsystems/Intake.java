
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.Intake.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private WPI_VictorSPX motor; // Motor for intake/outtake
    private DoubleSolenoid grabber; // solenoid for grabbing
    // NOTE: cube sensor (ir) is not implemented

    public Intake() {
        motor = new WPI_VictorSPX(Ports.MOTOR);
        motor.setNeutralMode(NeutralMode.Brake);

        grabber = new DoubleSolenoid(Ports.SOLENOID_LEFT, Ports.SOLENOID_RIGHT);
    }

    // Motor related stuff
    public void set(double value) {
        motor.set(value);
    }

    public void stop() {
        motor.stopMotor();
    }

    public void acquire() {
        set(ACQUIRE_SPEED);
    }

    public void deacquire() {
        set(DEACQUIRE_SPEED);
    }

    // Grabber related stuff
    public void open() {
        if (!isOpen())
            grabber.set(Value.kForward);
    }

    public void close() {
        if (isOpen())
            grabber.set(Value.kReverse);
    }
    
    public void toggle() {
        if (isOpen())
            close();
        else 
            open();
    }

    public boolean isOpen() {
        return grabber.get() == Value.kForward;
    }

}