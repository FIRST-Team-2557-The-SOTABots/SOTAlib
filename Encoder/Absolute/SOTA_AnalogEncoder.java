package SOTAlib.Encoder.Absolute;

import SOTAlib.Config.EncoderConfig;
import SOTAlib.MotorController.NullConfigException;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AnalogInput;

public class SOTA_AnalogEncoder implements SOTA_AbsoulteEncoder {
    private AnalogInput mEncoder;
    private double mOffset;
    private final double MAX_VOLTAGE = 5.0;

    public SOTA_AnalogEncoder(EncoderConfig config) throws NullConfigException {
        if (config == null) {
            throw new NullConfigException("SOTA_AnalogEncoder: No config");
        }

        this.mEncoder = new AnalogInput(config.getPort());

        if (config.getEncoderOffset() != null) {
            mOffset = config.getEncoderOffset();
        }
    }

    @Override
    public double getPosition() {
        // angle encoder increases with cw movement, this conversion makes it increase
        // with ccw movement
        // for compatibility with the radian based setpoint from kinematics class
        // -- Hayden Feeney (Robot2022)
        return (-1 * MathUtil.inputModulus(mEncoder.getAverageVoltage() - mOffset, 0, MAX_VOLTAGE) + MAX_VOLTAGE)
                / MAX_VOLTAGE;
    }

    @Override
    public double getPositionNoOffset() {
        return (-1 * MathUtil.inputModulus(mEncoder.getAverageVoltage(), 0, MAX_VOLTAGE) + MAX_VOLTAGE)
                / MAX_VOLTAGE;
    }

    @Override
    public void setPositionOffset(double offset) {
        this.mOffset = offset;
    }

    @Override
    public double getPositionOffset() {
        return mOffset;
    }

    @Override
    public int getPort() {
        return mEncoder.getChannel();
    }
}
