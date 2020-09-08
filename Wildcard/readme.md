# Wildcard Reloaded

This is a complete recoding of Wildcard for the 2020 WPILib.

## Notes

There is no cube sensor in the intake due to weird class usage.

There are no sensors (ultrasonic or line sensor) in the drivetrain.

There is no "ramping" algorithm in the lift.

## Subsystems

* Drivetrain
  * Moves the robot
  * Reads Encoders
  * Tank Drive
  * Arcade Drive
* Intake
  * Hold cube ball at a time
* Lift
  * Moves the intake vertically
  * Limit switches for bottom / top safety checks

## Commands

* DrivetrainDriveCommand
  * Filtered arcade drive
* LiftMoveCommand
  * Filtered gamepad readings
* Intake Toggleable Commands
  * Are present as `new IntakeCommand(intake::method, intake)` inside of [RobotContainer](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Wildcard/src/main/java/frc/robot/RobotContainer.java]