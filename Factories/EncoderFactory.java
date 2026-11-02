// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package SOTAlib.Factories;

import SOTAlib.Config.EncoderConfig;
import SOTAlib.Encoder.Absolute.SOTA_AbsoluteEncoder;
import SOTAlib.Encoder.Absolute.SOTA_AnalogEncoder;
import SOTAlib.Encoder.Absolute.SOTA_DutyCycle;
import SOTAlib.Encoder.Absolute.SOTA_CANcoder;
import SOTAlib.Encoder.Relative.QuadratureEncoder;
import SOTAlib.Encoder.Relative.SOTA_RelativeEncoder;
import SOTAlib.MotorController.NullConfigException;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;
import com.ctre.phoenix6.hardware.CANcoder;

/**
 * Factory class for creating various encoder types based on configuration settings.
 */
public class EncoderFactory {

    public static SOTA_RelativeEncoder generateRelativeEncoder(EncoderConfig config) throws NullConfigException {
        return generateQuadratureDelegate(config);
    }

    public static SOTA_AbsoluteEncoder generateAbsoluteEncoder(EncoderConfig config) throws Exception {
        if (config == null) {
            throw new NullConfigException("EncoderFactory: Null Config");
        }
        switch (config.getEncoderType().toUpperCase()) {
            case "DUTYCYCLE":
                return generateDutyCycleEncoder(config);
            case "ANALOG":
                return new SOTA_AnalogEncoder(config);
            case "CANCODER":
                return generateCANcoderEncoder(config);
            default:
                throw new Exception(
                        "EncoderFactory: generateAbsoluteEncoder invalid encoder type, accepts 'DUTYCYCLE', 'ANALOG', and 'CANCODER'");
        }
    }

    private static SOTA_AbsoluteEncoder generateDutyCycleEncoder(EncoderConfig config) {
        DutyCycleEncoder oEncoder = new DutyCycleEncoder(config.getPort());

        double offset = 0.0;
        if (config.getEncoderOffset() != null) {
            offset = config.getEncoderOffset();
        }

        return new SOTA_DutyCycle(oEncoder, offset, config.getIsInverted());
    }

        private static SOTA_AbsoluteEncoder generateCANcoderEncoder(EncoderConfig config) {
        // Wrap the bus name inside the modern CTRE CANBus class topology object
        com.ctre.phoenix6.CANBus targetBus = new com.ctre.phoenix6.CANBus(config.getCanBusConfig().getBusName());
        
        // Use the preferred constructor method
        CANcoder rawEncoder = new CANcoder(config.getPort(), targetBus);
        
        return new SOTA_CANcoder(config, rawEncoder);
    }


    private static QuadratureEncoder generateQuadratureDelegate(EncoderConfig config) throws NullConfigException {
        if (config == null) {
            throw new NullConfigException("EncoderFactory: Null Config");
        }

        Encoder encoder;
        if (config.getEncodingType() != null) {
            encoder = new Encoder(config.getSourceA(), config.getSourceB(), false, config.getEncodingType());
        } else {
            encoder = new Encoder(config.getSourceA(), config.getSourceB());
        }

        if (config.getSourceI() != null) {
            encoder.setIndexSource(config.getSourceI());
        }

        if (config.getIsInverted())
            encoder.setReverseDirection(true);
        return new QuadratureEncoder(encoder, config.getCountsPerRevolution());
    }
}
