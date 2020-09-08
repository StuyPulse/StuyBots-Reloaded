/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.stuypulse.stuylib.input.Gamepad;
import com.stuypulse.stuylib.streams.IStream;
import com.stuypulse.stuylib.streams.filters.LowPassFilter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Lift;


import com.stuypulse.stuylib.math.SLMath;

import static frc.robot.Constants.Lift.*;

/**
 * An example command that uses an example subsystem.
 */
public class LiftMoveCommand extends CommandBase {

  private final Lift lift;

  private IStream operatorInput;

  public LiftMoveCommand(Lift lift, Gamepad operator) {
    this.lift = lift;

    operatorInput = operator::getLeftY;
    operatorInput = operatorInput.filtered(SLMath::square, new LowPassFilter(LIFT_RC));

    addRequirements(lift);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    lift.move(operatorInput.get());
  }

}
