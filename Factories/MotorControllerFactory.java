package lib.Factories;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import lib.Config.EncoderConfig;
import lib.Config.MotorControllerConfig;
import lib.Config.MotorLimitsConfig;
import lib.Encoder.AnalogInputEncoder;
import lib.Encoder.SOTADutyCycleEncoder;
import lib.Encoder.SOTA_Encoder;
import lib.MotorController.Falcon;
import lib.MotorController.MotorLimits;
import lib.MotorController.SOTA_MotorController;
import lib.MotorController.SparkMaxDelegate;

public class MotorControllerFactory {
    
    public static SOTA_MotorController generateFalconDelegate(MotorControllerConfig config){
        if(config == null) return null;
        WPI_TalonFX motor = new WPI_TalonFX(config.getPort());        
        SOTA_Encoder encoder =  generateEncoder(config.getEncoderConfig());
        MotorLimits limits = generateLimits(config.getMotorLimitsConfig());

        return new Falcon(motor, encoder, limits, config);
    }

    public static SOTA_MotorController generateSparkDelegate(MotorControllerConfig config) {
        if(config == null) {
            return null;
        }
        MotorType motorType;
        switch(config.getMotorType()) {
            case("BRUSHLESS"):
                motorType = MotorType.kBrushless;
                break;
            case("BRUSHED"):
                motorType = MotorType.kBrushed;
                break;
            default:
                throw new IllegalArgumentException("Illegal motor type");
        }
        CANSparkMax sparkMax = new CANSparkMax(config.getPort(), motorType);
        SOTA_Encoder encoder = generateEncoder(config.getEncoderConfig());
        MotorLimits limits = generateLimits(config.getMotorLimitsConfig());
        sparkMax.setInverted(config.getIsInverted());
        return new SparkMaxDelegate(sparkMax, encoder, limits, config);
    }

    public static MotorLimits generateLimits(MotorLimitsConfig config){
        if(config == null) return null;
        return new MotorLimits(config.getLowerLimit(), config.getUpperLimit(), config.getFinalLimits());
        
    }

    public static SOTA_Encoder generateEncoder(EncoderConfig encoderConfig){
        if (encoderConfig == null) {
            return null;
        }
        switch(encoderConfig.getEncoderType()){
            case "ANALOG":
                AnalogInput analogInput = new AnalogInput(encoderConfig.getPort());
                return new AnalogInputEncoder(analogInput, encoderConfig);
            case "DUTYCYCLE":
                DutyCycleEncoder dutyCycle = new DutyCycleEncoder(encoderConfig.getPort());
                return new SOTADutyCycleEncoder(dutyCycle, encoderConfig);
            default :
                throw new IllegalArgumentException("Illegal Encoder Type");
        }
    }
}
