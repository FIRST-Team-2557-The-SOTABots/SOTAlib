package SOTAlib.Factories;

import java.util.Optional;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import SOTAlib.Config.CompositeMotorConfig;
import SOTAlib.Config.EncoderConfig;
import SOTAlib.Config.MotorControllerConfig;
import SOTAlib.Encoder.Absolute.SOTA_AbsoulteEncoder;
import SOTAlib.Encoder.Absolute.SOTA_SparkAbsEncoder;
import SOTAlib.MotorController.MotorPositionLimits;
import SOTAlib.MotorController.NullConfigException;
import SOTAlib.MotorController.SOTA_CompositeMotor;
import SOTAlib.MotorController.SOTA_CompositeMotorImplementation;
import SOTAlib.MotorController.SOTA_MotorController;
import SOTAlib.MotorController.SOTA_SparkMax;

public class CompositeMotorFactory {
    private static final String kSparkMax = "SPARKMAX";

    public SOTA_CompositeMotor generateCompositeMotor(CompositeMotorConfig inputConfig)
            throws IllegalMotorModel, Exception {
        Optional<CompositeMotorConfig> config = Optional.ofNullable(inputConfig);
        MotorControllerFactory motorFactory = new MotorControllerFactory(); // Yes this only has static methods, did
                                                                            // this to not break things in future if we
                                                                            // need to unstatic those methods

        if (config.isEmpty())
            throw new NullConfigException("CompositeMotorFactory: Null config");

        if (config.get().getEncoderConfig().getEncoderType().equals(kSparkMax)) {
            return sparkmaxEncoderGeneration(config);
        } else {
            return new SOTA_CompositeMotorImplementation(
                    motorFactory.generateMotorController(config.get().getMotorConfig()),
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

        CANSparkMax mMotor;
        mMotor = new CANSparkMax(motorConfig.getPort(), motorType);
        mMotor.setInverted(motorConfig.getIsInverted());
        switch (motorConfig.getNeutralOperation()) {
            case "BRAKE":
                mMotor.setIdleMode(IdleMode.kBrake);
            case "COAST":
                mMotor.setIdleMode(IdleMode.kCoast);
        }

        if (motorConfig.getCurrentLimit() != -1) {
            mMotor.setSmartCurrentLimit(motorConfig.getCurrentLimit());
        } else {
            System.out.println("SOTA_SparkMax: INFO: No current limit set");
        }

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

        SOTA_AbsoulteEncoder absoulteEncoder;

        AbsoluteEncoder mEncoder = mMotor.getAbsoluteEncoder(Type.kDutyCycle);

        absoulteEncoder = new SOTA_SparkAbsEncoder(mEncoder, mMotor.getDeviceId(), encoderConfig.getIsInverted(), encoderConfig.getEncoderOffset());

        return new SOTA_CompositeMotorImplementation(motorController, absoulteEncoder);
    }
}
