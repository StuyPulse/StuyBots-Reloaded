# Rafael Reloaded

This is a complete recoding of Rafael for the 2020 WPILib.

## Notes

The drivetrain implementation differs from the original in a few ways (for simplicity):
 - It will not turnoff one of the motors when in high gear
 - The only encoders supported are the greyhills

Notable subsystems that are missing are:
 - Blender
 - Shooter
 - Winch
 - Gear pushcer and trapper

Many of the commands are greatly simplified or just missing all together. This is because this is not made to replace the Alfred code, but be a base line for exending in the future.

Due to the complexity of the Elevator, it is not currently stable code, but it is there if necessary (tilting is important)

## Subsystems

* Drivetrain
  * Moves the robot
  * Tank Drive
  * Arcade Drive

## Commands

* Drivetrain Drive Command
  * Filtered movement 
* Instant commands (inline commands, present in RobotContainer)
  * Gear Switching