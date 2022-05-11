package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.stuypulse.stuylib.network.SmartNumber;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.LinearQuadraticRegulator;
import edu.wpi.first.math.estimator.KalmanFilter;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.LinearSystemLoop;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Feeder extends SubsystemBase {
    private final CANSparkMax motor;
    private final RelativeEncoder encoder;
    private final SmartNumber target;

    private final LinearSystem<N1, N1, N1> model = LinearSystemId.identifyVelocitySystem(Constants.Feeder.kV,
            Constants.Feeder.kV);

    private final KalmanFilter<N1, N1, N1> observer = new KalmanFilter<>(
            Nat.N1(),
            Nat.N1(),
            model,
            VecBuilder.fill(3.0), // How accurate we think our model is
            VecBuilder.fill(0.01), // How accurate we think our encoder data is
            0.020);

    // A LQR uses feedback to create voltage commands.
    private final LinearQuadraticRegulator<N1, N1, N1> controller = new LinearQuadraticRegulator<>(
            model,
            VecBuilder.fill(5.0), // qelms. Velocity error tolerance, in radians per second. Decrease
            // this to more heavily penalize state excursion, or make the controller behave
            // more
            // aggressively.
            VecBuilder.fill(12.0), // relms. Control effort (voltage) tolerance. Decrease this to more
            // heavily penalize control effort, or make the controller less aggressive. 12
            // is a good
            // starting point because that is the (approximate) maximum voltage of a
            // battery.
            0.020); // Nominal time between loops. 0.020 for TimedRobot, but can be lower if using
                    // notifiers.

    private final LinearSystemLoop<N1, N1, N1> loop = new LinearSystemLoop<>(model, controller,
            observer, 12.0, 0.020);

    public Feeder() {
        motor = new CANSparkMax(Constants.Ports.FEEDER, MotorType.kBrushless);
        encoder = motor.getEncoder();

        encoder.setVelocityConversionFactor(Units.rotationsPerMinuteToRadiansPerSecond(1));

        target = new SmartNumber("Feeder/Target", 0.0);
    }

    @Override
    public void periodic() {
        loop.setNextR(VecBuilder.fill(target.get()));
        loop.correct(VecBuilder.fill(encoder.getVelocity()));
        loop.predict(0.020);
        
        motor.setVoltage(loop.getU(0));
    
        SmartDashboard.putNumber("Debug/Feeder/radians per sec", encoder.getVelocity());
    }
}
