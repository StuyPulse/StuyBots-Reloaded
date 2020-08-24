package frc.robot.util;

import com.stuypulse.stuylib.network.SmartBoolean;

import edu.wpi.first.wpilibj.Compressor;

public class CompressorControl {
    
    private static final SmartBoolean ENABLED = new SmartBoolean("COMPRESSOR ENABLED", false);

    private final Compressor compressor;

    public CompressorControl() {
        compressor = new Compressor();
    }

    public void update() {
        boolean en = ENABLED.get();

        if (en) {
            compressor.start();
        }

        else {
            compressor.stop();
        }
    }

}