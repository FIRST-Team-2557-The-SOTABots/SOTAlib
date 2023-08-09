package SOTAlib.Encoder.Absolute;

import SOTAlib.Config.EncoderConfig;
import SOTAlib.MotorController.NullConfigException;
import edu.wpi.first.wpilibj.AnalogEncoder;

public class SOTA_AnalogEncoder implements SOTA_AbsoulteEncoder {
    private AnalogEncoder mEncoder;

    public SOTA_AnalogEncoder(EncoderConfig config) throws NullConfigException {
        if (config == null) {
            throw new NullConfigException("SOTA_AnalogEncoder: No config");
        }

        this.mEncoder = new AnalogEncoder(config.getPort());

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
