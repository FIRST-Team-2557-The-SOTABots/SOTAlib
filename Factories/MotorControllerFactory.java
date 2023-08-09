package SOTAlib.Factories;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import SOTAlib.Config.EncoderConfig;
import SOTAlib.Config.MotorControllerConfig;
import SOTAlib.Config.MotorLimitsConfig;
import SOTAlib.MotorController.SOTA_FalconFX;
import SOTAlib.MotorController.MotorPositionLimits;
import SOTAlib.MotorController.NullConfigException;
import SOTAlib.MotorController.SOTA_MotorController;
import SOTAlib.MotorController.SOTA_SparkMax;
import SOTAlib.MotorController.SOTA_TalonSRX;

public class MotorControllerFactory {

    public static SOTA_MotorController generateMotorController(MotorControllerConfig config)
            throws IllegalMotorModel, NullConfigException {
        switch (config.getMotorModel()) {
            case "Falcon":
                return new SOTA_FalconFX(config);
            case "SparkMax":
                return new SOTA_SparkMax(config);
            case "Talon":
                return new SOTA_TalonSRX(config);
        }
        throw new IllegalMotorModel(
                "Illegal Motor Model, check config has valid motor types 'Falcon', 'SparkMax', or 'Talon'");
    }

    // public static SOTA_MotorController generateTalon(MotorControllerConfig
    // config){
    // WPI_TalonSRX motor = new WPI_TalonSRX(config.getPort());
    // motor.setInverted(config.getIsInverted());
    // SOTA_Encoder encoder = generateEncoder(config.getEncoderConfig());
    // MotorPositionLimits limits = generateLimits(config.getMotorLimitsConfig());
    // return new SOTA_TalonSRX(motor, encoder, limits, config);
    // }

    // public static MotorPositionLimits generateLimits(MotorLimitsConfig config){
    // if(config == null) return null;//TODO: throw null exception
    // return new MotorPositionLimits(config.getLowerLimit(), // config.getUpperLimit(), config.getFinalLimits());

    // }

    // public static SOTA_Encoder generateEncoder(EncoderConfig encoderConfig) {
    //     if (encoderConfig == null) {
    //         return null;// TODO: throw null exception
    //     }
    //     switch (encoderConfig.getEncoderType()) {
    //         case "ANALOG":
    //             AnalogInput analogInput = new AnalogInput(encoderConfig.getPort());
    //             return new AnalogInputEncoder(analogInput, encoderConfig);
    //         case "DUTYCYCLE":
    //             DutyCycleEncoder dutyCycle = new DutyCycleEncoder(encoderConfig.getPort());
    //             return new SOTADutyCycleEncoder(dutyCycle, encoderConfig);
    //         default:
    //             throw new IllegalArgumentException("Illegal Encoder Type");
    //     }
    // }
}
