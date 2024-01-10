package SOTAlib.Encoder.Absolute;

import com.ctre.phoenix.sensors.CANCoder;

import SOTAlib.Config.EncoderConfig;
import SOTAlib.MotorController.NullConfigException;

public class SOTA_CANCoder implements SOTA_AbsoulteEncoder {
    private CANCoder mEncoder;

    public SOTA_CANCoder(EncoderConfig config) throws NullConfigException {
        if (config == null) {
            throw new NullConfigException("SOTA_CANCoder: no config");
        }

        this.mEncoder = new CANCoder(config.getPort());

        if (config.getEncoderOffset() != null) {
            mEncoder.configMagnetOffset(config.getEncoderOffset());
        }
    }

    @Override
    public double getPosition() {
        return mEncoder.getPosition() / 360;
    }

    @Override
    public double getRawPosition() {
        return mEncoder.getAbsolutePosition() / 360; //TODO: should test
    }

    @Override
    public void setPositionOffset(double offset) {
        mEncoder.configMagnetOffset(offset);
    }

    @Override
    public double getPositionOffset() {
        return mEncoder.configGetMagnetOffset();
    }
    @Override
    public int getPort() {
        return mEncoder.getDeviceID();
    }


}
