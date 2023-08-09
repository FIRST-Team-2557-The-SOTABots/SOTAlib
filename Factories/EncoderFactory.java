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

/** Add your docs here. */
public class EncoderFactory {
    public static SOTA_RelativeEncoder generateRelativeEncoder(EncoderConfig config) throws NullConfigException {
        if (config == null) { throw new NullConfigException("EncoderFactory: Null Config");}
        return new QuadratureEncoder(config);
    }

    public static SOTA_AbsoulteEncoder generateAbsoluteEncoder(EncoderConfig config) throws Exception {
        if (config == null) {throw new NullConfigException("EncoderFacotry: Null Config");}
        switch (config.getEncoderType()) {
            case "DUTYCYCLE":
                return new SOTA_DutyCycle(config); 
            case "ANALOG":
                return new SOTA_AnalogEncoder(config);
            default:
                throw new Exception("EncoderFactory: generateAbsoluteEncoder invaild encoder type, only accepts 'DUTYCYCLE' and 'ANALOG'");
        }
    }
}
