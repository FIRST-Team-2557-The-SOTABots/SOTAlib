package SOTAlib.Encoder.Absolute;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import SOTAlib.Config.EncoderConfig;

/**
 * Universal Phoenix 6 Absolute Encoder wrapper for the CTRE CANcoder.
 */
public class SOTA_CANcoder implements SOTA_AbsoluteEncoder {
    private final CANcoder mEncoder;
    private final int mPort;
    private double mOffset;
    private boolean isInverted;

    public SOTA_CANcoder(EncoderConfig config, CANcoder encoder) {
        this.mEncoder = encoder;
        this.mPort = config.getPort();
        this.mOffset = config.getEncoderOffset() != null ? config.getEncoderOffset() : 0.0;
        this.isInverted = config.getIsInverted();
        
        configureEncoder();
    }

    private void configureEncoder() {
        CANcoderConfiguration cancoderConfigs = new CANcoderConfiguration();
        
        // Phoenix 6 sets the absolute hardware offset directly on the device
        cancoderConfigs.MagnetSensor.MagnetOffset = mOffset;
        
        // Match the interface standard: CounterClockwise_Positive is the default forward direction
        cancoderConfigs.MagnetSensor.SensorDirection = isInverted 
            ? SensorDirectionValue.Clockwise_Positive 
            : SensorDirectionValue.CounterClockwise_Positive;
            
        mEncoder.getConfigurator().apply(cancoderConfigs);
    }

    @Override
    public double getPosition() {
        // Returns the hardware-offsetted position in rotations
        return mEncoder.getAbsolutePosition().getValueAsDouble();
    }

    @Override
    public double getRawPosition() {
        // Pulls the raw position status signal from the encoder sensor loop
        return mEncoder.getPosition().getValueAsDouble();
    }

    @Override
    public void setPositionOffset(double offset) {
        this.mOffset = offset;
        configureEncoder(); // Re-apply configuration settings down to the device
    }

    @Override
    public double getPositionOffset() {
        return this.mOffset;
    }

    @Override
    public int getPort() {
        return this.mPort;
    }

    @Override
    public void setInverted(boolean isInverted) {
        this.isInverted = isInverted;
        configureEncoder();
    }

    @Override
    public boolean getInverted() {
        return this.isInverted;
    }
}
