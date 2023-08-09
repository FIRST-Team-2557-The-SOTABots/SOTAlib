package SOTAlib.Encoder.Relative;

import SOTAlib.Config.EncoderConfig;
import SOTAlib.MotorController.NullConfigException;
import edu.wpi.first.wpilibj.Encoder;

public class QuadratureEncoder implements SOTA_RelativeEncoder {

    private Encoder mEncoder;
    private int countsPerRevolution;

    public QuadratureEncoder(EncoderConfig config) throws NullConfigException {
        if (config == null) {
            throw new NullConfigException("Null Encoder Config");
        }

        this.countsPerRevolution = config.getCountsPerRevolution();

        if (config.getEncodingType() != null) {
            this.mEncoder = new Encoder(config.getSourceA(), config.getSourceB(), false, config.getEncodingType());
        } else {
            this.mEncoder = new Encoder(config.getSourceA(), config.getSourceB());
        }

        if (config.getSourceI() != null) {
            mEncoder.setIndexSource(config.getSourceI());
        }

        if (config.getIsInverted())
            mEncoder.setReverseDirection(true);
    }

    @Override
    public double getPosition() {
        return mEncoder.get() / countsPerRevolution;
    }

    @Override
    public double getVelocity() {
        return (mEncoder.getRate() / countsPerRevolution) * 60;
    }

    @Override
    public void reset() {
        mEncoder.reset();
    }

    @Override
    public double getCountsPerRevolution() {
        return countsPerRevolution;
    }
}
