package SOTAlib.Config;

public class CompositeMotorConfig {
    private MotorControllerConfig motorConfig;
    private EncoderConfig encoderConfig;

    public MotorControllerConfig getMotorConfig() {
        return motorConfig;
    }

    public EncoderConfig getEncoderConfig() {
        return encoderConfig;
    }
}
