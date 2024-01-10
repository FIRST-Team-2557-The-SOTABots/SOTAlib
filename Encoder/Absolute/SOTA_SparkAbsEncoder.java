package SOTAlib.Encoder.Absolute;

import com.revrobotics.AbsoluteEncoder;

public class SOTA_SparkAbsEncoder implements SOTA_AbsoulteEncoder {

    private AbsoluteEncoder mEncoder;
    private int sparkCanId;

    public SOTA_SparkAbsEncoder(AbsoluteEncoder encoder, int sparkCanId) {
        this.mEncoder = encoder;
        this.sparkCanId = sparkCanId;
    }

    @Override
    public double getPosition() {
        return mEncoder.getPosition();
    }

    @Override
    public double getRawPosition() {
        return mEncoder.getPosition() - mEncoder.getZeroOffset(); //TODO: Should test
    }

    @Override
    public void setPositionOffset(double offset) {
       mEncoder.setZeroOffset(offset); 
    }

    @Override
    public double getPositionOffset() {
       return mEncoder.getZeroOffset(); 
    }

    @Override
    public int getPort() {
        return sparkCanId;
    }
    
}
