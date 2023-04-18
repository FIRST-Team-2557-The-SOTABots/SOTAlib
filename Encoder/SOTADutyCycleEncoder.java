package lib.Encoder;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import lib.Config.EncoderConfig;

public class SOTADutyCycleEncoder implements SOTA_Encoder{
    private double offset;
    private double distancePerRot = 1;
    private DutyCycleEncoder encoder;
    // TODO: big jon clean this up
    public SOTADutyCycleEncoder(DutyCycleEncoder dutyCycle, EncoderConfig config) {
        encoder = dutyCycle;
        this.offset = config.getEncoderOffset();
    }

    public void setDistancePerRotation(double distancePerRot) {
        this.distancePerRot = distancePerRot;
    }

    public void setPositionOffset(double offset){
        this.offset = offset;
    }
    
    @Override
    public double get() {
        return  (getAbsolutePosition() - offset < 0 ? 1 + (getAbsolutePosition() - offset) : getAbsolutePosition() - offset) * distancePerRot;
    }

    @Override
    public double getAbsolutePosition() {
        return encoder.getAbsolutePosition();
    }

    @Override
    public void reset() {
        encoder.reset();
        
    }

    @Override
    public double getPositionOffset() {
        return offset;
    }

    @Override
    public void close() {
        encoder.close();
        
    }
    //TODO: FIX
    @Override
    public double getVelocity() {
        return 0;
    }

    @Override
    public double getCountsPerRevolution() {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
