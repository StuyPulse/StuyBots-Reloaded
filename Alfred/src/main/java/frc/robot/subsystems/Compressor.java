package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Compressor.*;

import com.stuypulse.stuylib.network.SmartBoolean;

/**
 * This is an implementation of the Compressor for Alfred.
 * 
 * The compressor wasn't made as a subsystem in previous years, but it is the cleanest way to manage it.
 * 
 * The compressors job is to fill up the air outside of a match.
 * 
 * This one is controlled through smart dashboard, but can also be called directly.
 */
public class Compressor extends SubsystemBase {

    // If the compressor should be enabled
    private final SmartBoolean enabled;

    // Compressor object
    private final edu.wpi.first.wpilibj.Compressor compressor;

    // Init Compressor
    public Compressor() {
        enabled = new SmartBoolean(SMART_DASHBOARD_VALUE, false);
        compressor = new edu.wpi.first.wpilibj.Compressor(PneumaticsModuleType.CTREPCM);
    }
    
    // Update compressor with value
    public void periodic() {
        boolean shouldEnable = enabled.get();

        if(compressor.enabled() != shouldEnable) {
            if(shouldEnable) {
                compressor.start();
            } else {
                compressor.stop();
            }
        }
    }

    // Control the compressor 
    public void enable() {
        set(true);
    }

    public void disable() {
        set(false);
    }

    public void set(boolean value) {
        enabled.set(value);
    }
    
}