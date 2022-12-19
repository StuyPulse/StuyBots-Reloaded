// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package frc.robot.subsystems;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;

// import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

// // Get the constant values relevent to us
// import frc.robot.Constants.Ports;
// import frc.robot.Constants.Tuning;

// /**
//  * ----- PLEASE READ -----
//  * 
//  * This part of the code contains the Take Back Half algorithm.
//  * 
//  * This algorithm is used to make sure that the robot shooter is spinning at a certain RPM.
//  * 
//  * This is a quite complicated part of the code, but it is needed to get a good shooter.
//  * 
//  * Here is what is important to know:
//  *  - The Take Back Half algorithm (TBHController) uses only one peice of information
//  *      - The difference between the current speed and the target speed
//  *      - This is called error
//  *  - Take Back Half will then return a value from -1.0 to 1.0 telling the shooter how fast to spin
//  *  - All that we need to do is set the target RPM and the controller will figure everthing else out
//  * 
//  * The code below is an example implementation of this. Aside from the getRPM() function, this code is 100% working code.
//  * 
//  * If you have any extra questions, please contact the heads of the 694 SE department 
//  * They will be happy to help. 
//  */
// public class Shooter extends SubsystemBase {

//     private TBHController controller;

//     private WPI_VictorSPX shooter;
//     private double targetRPM;

//     public Shooter() {
//         // Make the TBH Controller
//         controller = new TBHController(Tuning.GAIN.doubleValue());

//         // Make all the motors with the correct port numbers
//         shooter = new WPI_VictorSPX(Ports.SHOOTER);
//         targetRPM = 0;
//     }

//     // A function like this would ideally return the RPM of the shooter,
//     // but Destiny got gutted of all of its Encoders, So just pretend like this works
//     public double getRPM() {
//         return 0.0;
//     }

//     // This function gets called 50 times a second
//     @Override
//     public void periodic() {
//         // Update TBH Gain with the gain from smart dashboard
//         // This lets us tune it in real time
//         controller.setGain(Tuning.GAIN.doubleValue());

//         // Gets the error of the motor speed 
//         // ie. how fast it needs to go - how fast its going
//         double error = targetRPM - getRPM();

//         // Get the speed we should set the motor too based on error
//         double out = controller.update(error);

//         // Update the shooter with the correct speed
//         shooter.set(out);
//     }


//     public void setRPM(double rpm) {
//         targetRPM = rpm;
//     }
//     //put this in now shooter works
//     public void stop() {
//         shooter.set(0);
//     }
// }
