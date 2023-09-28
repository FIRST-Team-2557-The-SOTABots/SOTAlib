package SOTAlib.Encoder.Relative;

import SOTAlib.Config.EncoderConfig;
import SOTAlib.MotorController.NullConfigException;
import edu.wpi.first.wpilibj.Encoder;

public class QuadratureEncoder implements SOTA_RelativeEncoder {

    private Encoder mEncoder;
    private int countsPerRevolution;

    public QuadratureEncoder(Encoder encoder, int countsPerRevolution) {
        this.mEncoder = encoder;
        this.countsPerRevolution = countsPerRevolution;
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
