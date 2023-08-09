package SOTAlib.Encoder.Absolute;

import SOTAlib.Config.EncoderConfig;
import SOTAlib.MotorController.NullConfigException;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class SOTA_DutyCycle implements SOTA_AbsoulteEncoder {
    private DutyCycleEncoder mEncoder;

    public SOTA_DutyCycle(EncoderConfig config) throws NullConfigException {
        if (config == null) {
            throw new NullConfigException("SOTA_DutyCycle: no config");
        }

        this.mEncoder = new DutyCycleEncoder(config.getPort());

        if (config.getEncoderOffset() != null) {
            mEncoder.setPositionOffset(config.getEncoderOffset());
        }
    }

    @Override
    public double getPosition() {
        return mEncoder.get();
    }

    @Override
    public double getPositionNoOffset() {
        return mEncoder.getAbsolutePosition();
    }

    @Override
    public void setPositionOffset(double offset) {
        mEncoder.setPositionOffset(offset);
    }

    @Override
    public double getPositionOffset() {
        return mEncoder.getPositionOffset();
    }
}