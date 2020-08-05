# Destiny Reloaded

This is a complete recoding of Destiny for the 2020 WPILib.

This can be used as example robot code as most of the code is written in a way that makes it a great example for how to write robot code.

Every part of this project contains useful information about how to code the robot.

This project contains implementations of the Shooter and Drivetrain for Destiny.

# How to Look through this

## Main Files

The first two files to look at are 
 - [Constants.java](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Destiny/src/main/java/frc/robot/Constants.java)
 - [RobotContainer.java](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Destiny/src/main/java/frc/robot/RobotContainer.java)

Note:
 - The [SmartNumber](https://stuypulse.github.io/StuyLib/com/stuypulse/stuylib/network/SmartNumber.html) class lets you interact with the constant on smart dashboard while the robot is running.

## Subsystems

The subsystems are the next thing to look at. A subsystem is basically a part of the code that talks to the hardware directly. This lets things like commands operate the robot without having to initialize motors and other low level things like that.

Some of the values used are from the [Constants.java](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Destiny/src/main/java/frc/robot/Constants.java) file. Be sure to look back if you forget what a value is.

You can look at them here [frc/robot/subsystems/](https://github.com/StuyPulse/Destiny-Reloaded/tree/master/src/main/java/frc/robot/subsystems). 

## Commands 

A command is the part of the code called from [RobotContainer.java](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Destiny/src/main/java/frc/robot/RobotContainer.java) that tells the robot what to do with the subsystems.  

When a command is created, it takes in everything it needs to use as a parameter. For example, the [DrivetrainDriveCommand.java](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Destiny/src/main/java/frc/robot/commands/DrivetrainDriveCommand.java) takes in a [Drivetrain](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Destiny/src/main/java/frc/robot/subsystems/Drivetrain.java) and a [Gamepad](https://stuypulse.github.io/StuyLib/com/stuypulse/stuylib/input/gamepads/package-summary.html) as input as they are required in the command.

You can look at them here [frc/robot/commands/](https://github.com/StuyPulse/Destiny-Reloaded/tree/master/src/main/java/frc/robot/commands).

Each of the commands is called and setup in the [RobotContainer.java](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Destiny/src/main/java/frc/robot/RobotContainer.java) file. Look for things like `.setDefaultCommand(...)` and `.whileHeld(...)`.

### Autons 

An Auton is a group of commands that operates the robot without user input during the first 30 seconds of the game. The auton is selected through smart dashboard. You can add autons to smard dashboard in [RobotContainer.java](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Destiny/src/main/java/frc/robot/RobotContainer.java).

You can find a DoNothingAuton as an example auton here [frc/robot/commands/autos/](https://github.com/StuyPulse/Destiny-Reloaded/tree/master/src/main/java/frc/robot/commands/autos).



## Misc. Files

Files like [Main.java](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Destiny/src/main/java/frc/robot/Main.java) and [Robot.java](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Destiny/src/main/java/frc/robot/Robot.java) are just for initialization and can be avoided when looking through the project.

# Heads Up

In the files [DrivetrainAlignCommand.java](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Destiny/src/main/java/frc/robot/commands/DrivetrainAlignCommand.java) and [Shooter.java](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Destiny/src/main/java/frc/robot/subsystems/Shooter.java), advanced control algorithms are used like PID and Take Back Half.

These aren't too complicated to wrap your head around, but you may need some more information than just this project. Please contact the heads of the SE Department for more information.

# Dependencies

If you look in the `vendordeps` folder, you will see some `.json` files.

These files contain the libraries that we need to move some of the motors on Destiny.

# Use of StuyLib

The project uses [StuyLib](https://github.com/StuyPulse/StuyLib), a library that we made to speed along development.

Look in the `build.gradle` file to see how Stuylib is imported.

It is recommended that you look at the [StuyLib JavaDocs](https://stuypulse.github.io/StuyLib/). In particular, here are some of the important ones.

 - [Gamepad](https://stuypulse.github.io/StuyLib/com/stuypulse/stuylib/input/Gamepad.html)
    - [PS4](https://stuypulse.github.io/StuyLib/com/stuypulse/stuylib/input/gamepads/PS4.html)
    - [Logitech DMode](https://stuypulse.github.io/StuyLib/com/stuypulse/stuylib/input/gamepads/Logitech.DMode.html)
    - [Logitech XMode](https://stuypulse.github.io/StuyLib/com/stuypulse/stuylib/input/gamepads/Logitech.XMode.html)
 - [Limelight](https://stuypulse.github.io/StuyLib/com/stuypulse/stuylib/network/limelight/Limelight.html)
 - [The Filters Library](https://stuypulse.github.io/StuyLib/com/stuypulse/stuylib/streams/filters/package-summary.html)
    - [LowPassFilter](https://stuypulse.github.io/StuyLib/com/stuypulse/stuylib/streams/filters/LowPassFilter.html)

and for those looking to implement PID

 - [Controller](https://stuypulse.github.io/StuyLib/com/stuypulse/stuylib/control/Controller.html) and  [PIDController](https://stuypulse.github.io/StuyLib/com/stuypulse/stuylib/control/PIDController.html)