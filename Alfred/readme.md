# Alfred Reloaded

This is a complete recoding of Alfred for the 2020 WPILib.

## Notes

The drivetrain implementation differs from the original in a few ways (for simplicity):
 - It will not turnoff one of the motors when in high gear
 - The only encoders supported are the greyhills

Notable subsystems that are missing are:
 - The Abom (Suction Cup)
 - The Tail (What holds the suction cup)

Many of the commands are greatly simplified or just missing all together. This is because this is not made to replace the Alfred code, but be a base line for exending in the future.

Due to the complexity of the Elevator, it is not currently stable code, but it is there if necessary (tilting is important)

## Subsystems

* Drivetrain
  * Moves the robot
  * Reads Encoders
  * Tank Drive
  * Arcade Drive
  * Curviture Drive
* Intake
  * Hold one ball at a time
  * Filtered movement
* Grabber
  * Open and close to grab onto hatches
* Elevator
  * Move intake and penetrator vertically
  * Tilt forward & back
  * break and unbreak
  * Implement proper checks for top limit and bottom limit **TODO**


## Commands