package SOTAlib.Encoder.Absolute;

import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class SOTA_DutyCycle implements SOTA_AbsoluteEncoder {
    private final DutyCycleEncoder mEncoder;
    private double mOffset;
    private boolean isInverted;

    public SOTA_DutyCycle(DutyCycleEncoder encoder, double offset, boolean isInverted) {
        this.mEncoder = encoder;
        this.mOffset = offset;
        this.isInverted = isInverted;
    }

    @Override
    public double getPosition() {
        // 1. Get raw position
        double raw = mEncoder.get();
        
        // 2. Apply inversion to raw reading if active
        if (isInverted) {
            raw = 1.0 - raw;
        }
        
        // 3. Subtract offset and wrap cleanly within 0.0 to 1.0 range
        double output = (raw - mOffset) % 1.0;
        
        // Handle negative result caused by Java modulo on a negative number
        if (output < 0) {
            output += 1.0;
        }

        return output;
    }

    @Override
    public double getRawPosition() {
        return mEncoder.get();
    }

    @Override
    public void setPositionOffset(double offset) {
        // Keep offset bounded within 0.0 and 1.0
        mOffset = (offset % 1.0 + 1.0) % 1.0;
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
