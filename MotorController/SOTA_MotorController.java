package lib.MotorController;


import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import lib.Encoder.SOTA_Encoder;

public interface SOTA_MotorController extends MotorController {
    void setInverted(boolean isInverted);
    boolean getInverted();
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

