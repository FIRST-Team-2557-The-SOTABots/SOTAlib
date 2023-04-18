package lib.Encoder;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AnalogInput;
import lib.Config.EncoderConfig;

public class AnalogInputEncoder implements SOTA_AbsoulteEncoder {
    private final AnalogInput mEncoder;
    private final double kCountsPerRevolution;
    private double kOffset;

    public AnalogInputEncoder(AnalogInput encoder, EncoderConfig config) {
        this.mEncoder = encoder;
        this.kCountsPerRevolution = config.getCountsPerRevolution();
        this.kOffset = config.getEncoderOffset();
    }

    public double get() {
        return MathUtil.inputModulus(getPositionNoOffset() - kOffset, 0, kCountsPerRevolution);
    }

    

    //TODO: you cant setposition on analog input
    public void setPosition(double newPosition) {
        
    }

    //TODO: perhaps add this but it's not really important rn
    public double getVelocity() {
        return 0;
    }

    public void reset() {
        mEncoder.resetAccumulator();        
    }

    public double getCountsPerRevolution() {
        return kCountsPerRevolution;
    }

    public double getPositionOffset() {
        return kOffset;
    }

    @Override
    public double getAbsolutePosition() {
        return mEncoder.getAverageVoltage();
    }

    @Override
    public void setPositionOffset(double offset) {
        kOffset = offset;        
    }

    @Override
    public void close() {
        mEncoder.close();        
    }

    @Override
    public double getPositionNoOffset() {
        return mEncoder.getAverageVoltage();
    }

    @Override 
    public double getOffset() {
        return kOffset;
    }

}