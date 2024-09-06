package SOTAlib.Config;

/**
 * Wrapper class for uniting an EncoderConfig and MotorConfig. Used to create a CompositeMotor.
 */
public class CompositeMotorConfig {
    private MotorControllerConfig motorConfig;
    private EncoderConfig encoderConfig;

    /**
     * @return the MotorControllerConfig object to pass to the factory.
     */
    public MotorControllerConfig getMotorConfig() {
        return motorConfig;
    }

    /**
     * @return the EncoderConfig object to pass to the factory.
     */
    public EncoderConfig getEncoderConfig() {
        return encoderConfig;
    }
}
