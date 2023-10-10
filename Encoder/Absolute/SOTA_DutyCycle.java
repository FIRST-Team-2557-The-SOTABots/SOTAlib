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
        return (getPositionNoOffset() - mOffset < 0 ? 1 + (getPositionNoOffset() - mOffset) : getPositionNoOffset() - mOffset);
    }

    @Override
    public double getPositionNoOffset() {
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
}