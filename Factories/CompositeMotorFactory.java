package SOTAlib.Factories;

import java.util.Optional;

import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import SOTAlib.Config.CompositeMotorConfig;
import SOTAlib.Config.EncoderConfig;
import SOTAlib.Config.MotorControllerConfig;
import SOTAlib.Encoder.Absolute.SOTA_AbsoluteEncoder;
import SOTAlib.Encoder.Absolute.SOTA_SparkAbsEncoder;
import SOTAlib.MotorController.MotorPositionLimits;
import SOTAlib.MotorController.NullConfigException;
import SOTAlib.MotorController.SOTA_CompositeMotor;
import SOTAlib.MotorController.SOTA_CompositeMotorImplementation;
import SOTAlib.MotorController.SOTA_MotorController;
import SOTAlib.MotorController.SOTA_SparkMax;

/**
 * Creates SOTA_CompositeMotor objects
 */
public class CompositeMotorFactory {
    private static final String kSparkMax = "SPARKMAX";

    /**
     * Generates a SOTA_CompositeMotor.
     * !!! DOES NOT WORK WITH A RELATIVE ENCODER !!! ONLY GENERATES ABSOLUTE ENCODERS !!! todo: fix this
     * @param inputConfig CompositeMotorConfig to generate CompositeMotor based on
     * @return SOTA_CompositeMotor object @see SOTA_CompositeMotor
     * @throws IllegalMotorModel Throws if model of motor is not supported
     * @throws Exception Either a NullConfigException or an invalid motor type (BRUSHED vs BRUSHLESS)
     */
    public SOTA_CompositeMotor generateCompositeMotor(CompositeMotorConfig inputConfig)
            throws IllegalMotorModel, Exception {
        Optional<CompositeMotorConfig> config = Optional.ofNullable(inputConfig);

        if (config.isEmpty())
            throw new NullConfigException("CompositeMotorFactory: Null config");

        if (config.get().getEncoderConfig().getEncoderType().equals(kSparkMax)) {
            return sparkmaxEncoderGeneration(config);
        } else {
            return new SOTA_CompositeMotorImplementation(
                    MotorControllerFactory.generateMotorController(config.get().getMotorConfig()),
                    EncoderFactory.generateAbsoluteEncoder(config.get().getEncoderConfig()));
        }
    }

    private SOTA_CompositeMotor sparkmaxEncoderGeneration(Optional<CompositeMotorConfig> config)
            throws NullConfigException {
        MotorControllerConfig motorConfig = config.get().getMotorConfig();
        EncoderConfig encoderConfig = config.get().getEncoderConfig();

        SOTA_MotorController motorController;
        MotorType motorType;
        switch (motorConfig.getMotorType()) {
            case ("BRUSHLESS"):
                motorType = MotorType.kBrushless;
                break;
            case ("BRUSHED"):
                motorType = MotorType.kBrushed;
                break;
            default:
                throw new IllegalArgumentException("Illegal motor type");
        }

        SparkMax mMotor = new SparkMax(motorConfig.getPort(), motorType);
        
        SparkMaxConfig sparkConfig = new SparkMaxConfig();
        
        sparkConfig.inverted(motorConfig.getIsInverted());
        
        if (motorConfig.getNeutralOperation().equals("BRAKE")) {
            sparkConfig.idleMode(IdleMode.kBrake);
        } else if (motorConfig.getNeutralOperation().equals("COAST")) {
            sparkConfig.idleMode(IdleMode.kCoast);
        }
        
        if (motorConfig.getCurrentLimit() != -1) {
            sparkConfig.smartCurrentLimit(motorConfig.getCurrentLimit());
        } else {
            System.out.println("SOTA_SparkMax: INFO: No current limit set");
        }

        sparkConfig.absoluteEncoder.inverted(encoderConfig.getIsInverted());
        sparkConfig.absoluteEncoder.zeroOffset(encoderConfig.getEncoderOffset());

        mMotor.configure(sparkConfig, SparkMax.ResetMode.kResetSafeParameters, SparkMax.PersistMode.kPersistParameters);

        MotorPositionLimits limits = null;
        try {
            limits = new MotorPositionLimits(motorConfig.getMotorLimitsConfig().getLowerLimit(),
                    motorConfig.getMotorLimitsConfig().getUpperLimit(),
                    motorConfig.getMotorLimitsConfig().getFinalLimits());
        } catch (NullPointerException e) {
            System.out.println("SOTA_SparkMax: INFO: no motor limits");
        }

        if (Optional.ofNullable(limits).isPresent()) {
            motorController = new SOTA_SparkMax(motorConfig, mMotor, limits);
        } else {
            motorController = new SOTA_SparkMax(motorConfig, mMotor);
        }

        SparkAbsoluteEncoder mEncoder = mMotor.getAbsoluteEncoder();

        SOTA_AbsoluteEncoder absoulteEncoder = new SOTA_SparkAbsEncoder(mEncoder, mMotor.getDeviceId(), encoderConfig.getIsInverted(),
                encoderConfig.getEncoderOffset());

        return new SOTA_CompositeMotorImplementation(motorController, absoulteEncoder);
    }
}
