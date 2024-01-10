package SOTAlib.Encoder.Absolute;

import java.util.Optional;

import SOTAlib.Config.EncoderConfig;
import SOTAlib.MotorController.NullConfigException;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AnalogInput;

public class SOTA_AnalogEncoder implements SOTA_AbsoulteEncoder {
    private AnalogInput mEncoder;
    private double mOffset; //IN VOLTS IF YOU DO IT IN ENCODER POSITIONS IT WILL BE WRONG!!!
    private final double MAX_VOLTAGE = 5.0;

    public SOTA_AnalogEncoder(EncoderConfig config) throws NullConfigException {
        if (config == null) {
            throw new NullConfigException("SOTA_AnalogEncoder: No config");
        }

        this.mEncoder = new AnalogInput(config.getPort());

        Optional.ofNullable(config.getEncoderOffset()).ifPresentOrElse((offset) -> {this.mOffset = offset;}, () -> this.mOffset = 0);
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
    public double getRawPosition() {
        return mEncoder.getAverageVoltage(); //In VOLTS
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
