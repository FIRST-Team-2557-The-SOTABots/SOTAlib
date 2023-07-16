package SOTAlib.MotorController;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import SOTAlib.Encoder.SOTA_Encoder;

public interface SOTA_MotorController extends MotorController {

    /**
     * @param neutralOperation
     */
    void setNeutralOperation(NeutralOperation neutralOperation);

    SOTA_Encoder getEncoder();

    double getEncoderVelocity();

    double getEncoderPosition();

    double getNativeEncoderVelocity();

    double getNativeEncoderPosition();

    double getNativeCountsPerRevolution();

    void resetNativeEncoder();

    double getMotorTemperature();

    double getMotorCurrent();

    void setCurrentLimit(int amps);

    MotorLimits getLimits();

    void setLimits(MotorLimits limits);

    double getLowerLimit();

    double getUpperLimit();

    boolean atUpperLimit();

    boolean atLowerLimit();

    void resetEncoder();

    boolean getNeutralOperation();

    void setNeutralOperation();

}
