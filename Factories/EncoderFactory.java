// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package SOTAlib.Factories;

import SOTAlib.Config.EncoderConfig;
import SOTAlib.Encoder.Absolute.SOTA_AbsoulteEncoder;
import SOTAlib.Encoder.Absolute.SOTA_AnalogEncoder;
import SOTAlib.Encoder.Absolute.SOTA_DutyCycle;
import SOTAlib.Encoder.Relative.QuadratureEncoder;
import SOTAlib.Encoder.Relative.SOTA_RelativeEncoder;
import SOTAlib.MotorController.NullConfigException;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Encoder;

/** Add your docs here. */
public class EncoderFactory {
    public static SOTA_RelativeEncoder generateRelativeEncoder(EncoderConfig config) throws NullConfigException {
        return generateQuadratureDelegate(config);
    }

    // TODO: Make all of these consistent with the rest of the factories
    public static SOTA_AbsoulteEncoder generateAbsoluteEncoder(EncoderConfig config) throws Exception {
        if (config == null) {
            throw new NullConfigException("EncoderFacotry: Null Config");
        }
        switch (config.getEncoderType()) {
            case "DUTYCYCLE":
                return generateDutyCycleEncoder(config);
            case "ANALOG":
                return new SOTA_AnalogEncoder(config);
            default:
                throw new Exception(
                        "EncoderFactory: generateAbsoluteEncoder invaild encoder type, only accepts 'DUTYCYCLE', 'ANALOG', and 'CANCODER'");
        }
    }

    private static SOTA_AbsoulteEncoder generateDutyCycleEncoder(EncoderConfig config) {
        DutyCycleEncoder oEncoder = new DutyCycleEncoder(config.getPort());

        double offset = 0.0;
        if (config.getEncoderOffset() != null) {
            offset = config.getEncoderOffset();
        }

        return new SOTA_DutyCycle(oEncoder, offset, config.getIsInverted());
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
