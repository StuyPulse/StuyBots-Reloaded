package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Grabber.*;

/*
 * Grabber, or the hatch subsystem. This subsystem is made for gamepad bindings to instant commands
 * that control the grabber.
 * 
 * If the IR sensor present a default command can be used alongside instant command bindings
 */
public class Grabber extends SubsystemBase {

    // Solenoid to move the grabber away from the robot and bring it back
    private Solenoid pusher;

    // Solenoid to open and close the tongs of the grabber
    private Solenoid opener;

    public Grabber() {
        opener = new Solenoid(Ports.FLOOP_CHANNEL);
        pusher = new Solenoid(Ports.PUSHER_MODULE, Ports.PUSHER_CHANNEL);

        close();
        retract();
    }

    // The Opening / Closing of the Grabber
    public boolean isOpen() {
        return opener.get() == OPENED;
    }

    public void open() {
        if (!isOpen()) {
            opener.set(OPENED);
        }
    }

    public void close() {
        if (isOpen()) {
            opener.set(!OPENED);
        }
    }

    // The Extending and Retracting of the Grabber
    public boolean isExtended() {
        return pusher.get() == EXTENDED;
    }

    public void extend() {
        if (!isExtended()) {
            pusher.set(EXTENDED);
        }
    }

    public void retract() {
        if (isExtended()) {
            pusher.set(!EXTENDED);
        }
    }

}