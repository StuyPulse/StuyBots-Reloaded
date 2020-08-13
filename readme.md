# StuyBots-Reloaded

The goal of these projects is not to create competition ready code, but to instead create simple, well documented code that will make future testing easier. It will also include the most recent updates of WPILib and StuyLib.

## Why

The purpose of this project is to maintain upto date versions of all of our old robots. While we do not need to maintain entire robot projects as there are no longer competitions for these bots, it is still helpful to have an upto date version of these robots for testing. 

We are not here to implement every feature, we just need to get the majority of robot functions done so that we can do testing on the robots.

## Note

Before pushing / commiting code, please run [test_build.sh](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/test_build.sh), which will build each of the robots and report any errors that come back.

Please run this before pushing any code!

## Index

While you can go click on the folders, here are links to each of the robot projects

### 2016: [Destiny](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Destiny)
- [X] Initialized
- [X] Completed
- [X] Documented

### 2017: [Rafael](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Rafael)
- [X] Initialized
- [ ] Completed
- [ ] Documented

### 2018: [Wildcard](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Wildcard)
- [X] Initialized
- [ ] Completed
- [ ] Documented

### 2019: [Alfred](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Alfred)
- [X] Initialized
- [ ] Completed
- [ ] Documented

### 2020: [Edwin](https://github.com/StuyPulse/StuyBots-Reloaded/blob/master/Edwin)
- [ ] Initialized
- [ ] Completed
- [ ] Documented


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