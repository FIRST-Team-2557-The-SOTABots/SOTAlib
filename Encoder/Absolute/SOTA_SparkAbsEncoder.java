package SOTAlib.Encoder.Absolute;

import com.revrobotics.AbsoluteEncoder;

import SOTAlib.Math.Conversions;

public class SOTA_SparkAbsEncoder implements SOTA_AbsoulteEncoder {

    private AbsoluteEncoder mEncoder;
    private int sparkCanId;
    private boolean inverted;
    private double mOffset = 0.0;

    public SOTA_SparkAbsEncoder(AbsoluteEncoder encoder, int sparkCanId, boolean inverted, Double offset) {
        this.mEncoder = encoder;
        this.sparkCanId = sparkCanId;
        this.inverted = inverted;
        this.mOffset = offset;
    }

    @Override
    public double getPosition() {
        double output = 0;
        if (inverted) {
            output = 1 - (mEncoder.getPosition() - mOffset);
        } else {
            output = mEncoder.getPosition() - mOffset;
        }

        return output;
    }

    @Override
    public double getRawPosition() {
        return mEncoder.getPosition();
    }

    @Override
    public void setPositionOffset(double offset) {
        mOffset = offset;
    }

    @Override
    public double getPositionOffset() {
        return mOffset;
    }

    @Override
    public int getPort() {
        return sparkCanId;
    }

    @Override
    public void setInverted(boolean isInverted) {
        this.inverted = isInverted;
    }

    @Override
    public boolean getInverted() {
        return inverted;
    }

}
