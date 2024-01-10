package SOTAlib.Encoder.Absolute;

import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class SOTA_DutyCycle implements SOTA_AbsoulteEncoder {
    private DutyCycleEncoder mEncoder;
    private double mOffset;

    public SOTA_DutyCycle(DutyCycleEncoder encdoer, double offset) {

        this.mEncoder = encdoer;
        this.mOffset = offset;
    }

    @Override
    public double getPosition() {
        return (getRawPosition() - mOffset < 0 ? 1 + (getRawPosition() - mOffset) : getRawPosition() - mOffset);
    }

    @Override
    public double getRawPosition() {
        return mEncoder.getAbsolutePosition(); //TODO: Test
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
}