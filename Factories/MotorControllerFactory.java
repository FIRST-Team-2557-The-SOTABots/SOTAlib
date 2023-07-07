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
import SOTAlib.Encoder.AnalogInputEncoder;
import SOTAlib.Encoder.SOTADutyCycleEncoder;
import SOTAlib.Encoder.SOTA_Encoder;
import SOTAlib.MotorController.Falcon;
import SOTAlib.MotorController.MotorLimits;
import SOTAlib.MotorController.SOTA_MotorController;
import SOTAlib.MotorController.SparkMaxDelegate;
import SOTAlib.MotorController.SOTA_TalonSRX;
public class MotorControllerFactory {

    public static SOTA_MotorController generateMotorController(MotorControllerConfig config) throws IllegalMotorModel{
        switch (config.getMotorModel()) {
            case "Falcon":
                return generateFalconDelegate(config);
            case "SparkMax":
                return generateSparkDelegate(config);
            case "Talon":
                return generateTalon(config);
        }
        throw new  IllegalMotorModel("Illegal Motor Model, check config has valid motor types 'Falcon', 'SparkMax', or 'Talon'");
    }
    
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

    public static SOTA_MotorController generateTalon(MotorControllerConfig config){
        WPI_TalonSRX motor = new WPI_TalonSRX(config.getPort());
        motor.setInverted(config.getIsInverted());
        SOTA_Encoder encoder = generateEncoder(config.getEncoderConfig());
        MotorLimits limits = generateLimits(config.getMotorLimitsConfig());
        return new SOTA_TalonSRX(motor, encoder, limits, config);
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
