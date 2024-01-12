package SOTAlib.Encoder.Absolute;

import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class SOTA_DutyCycle implements SOTA_AbsoulteEncoder {
    private DutyCycleEncoder mEncoder;
    private double mOffset;
    private boolean isInverted;

    public SOTA_DutyCycle(DutyCycleEncoder encoder, double offset, boolean isInverted) {

        this.mEncoder = encoder;
        this.mOffset = offset;
        this.isInverted = isInverted;
    }

    @Override
    public double getPosition() {
        double output;
        if (isInverted) {
            output = 1 - (mEncoder.getAbsolutePosition() - mOffset);
        } else {
            output = mEncoder.getAbsolutePosition() - mOffset;
        }

        return output;
    }

    @Override
    public double getRawPosition() {
        return mEncoder.getAbsolutePosition();
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
        return mEncoder.getSourceChannel();
    }

    @Override
    public void setInverted(boolean isInverted) {
        this.isInverted = isInverted;
    }

    @Override
    public boolean getInverted() {
        return this.isInverted;
    }
}