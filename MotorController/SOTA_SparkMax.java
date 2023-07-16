package SOTAlib.MotorController;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import SOTAlib.Config.MotorControllerConfig;
import SOTAlib.Encoder.SOTA_Encoder;

public class SOTA_SparkMax implements SOTA_MotorController{
    private final CANSparkMax mMotor;
    private final SOTA_Encoder mEncoder;
    private MotorPositionLimits mMotorLimits;
    // TODO: check these constructors
    public SOTA_SparkMax(CANSparkMax motor, MotorControllerConfig config) {
        this(motor, (SOTA_Encoder) null, config);
    }

    public SOTA_SparkMax(CANSparkMax motor, SOTA_Encoder encoder, MotorControllerConfig config) {
        this(motor, encoder, (MotorPositionLimits) null, config);
    }

    public SOTA_SparkMax(CANSparkMax motor, MotorPositionLimits limits, MotorControllerConfig config){
        this(motor, (SOTA_Encoder) null, limits ,config);
    }

    public SOTA_SparkMax(CANSparkMax motor, SOTA_Encoder encoder, MotorPositionLimits limits, MotorControllerConfig config){
        this.mMotor = motor;
        this.mEncoder = encoder;
        this.mMotorLimits = limits;
    }

    public void set(double speed) {
        if(mMotorLimits != null){
            if(speed < 0){
                if(mMotorLimits.getLowerLimit() > getEncoderPosition()) speed = 0;
            }else if(speed > 0){
                if(mMotorLimits.getUpperLimit() < getEncoderPosition()) speed = 0;
            }
            
        }
        mMotor.set(speed);          
    }

    public void setVoltage(double voltage) {
        if(mMotorLimits != null){
            if(voltage < 0){
                if(mMotorLimits.getLowerLimit() > getEncoderPosition()) voltage = 0;
            }else if(voltage > 0){
                if(mMotorLimits.getUpperLimit() < getEncoderPosition()) voltage = 0;
            }
            
        }
        mMotor.setVoltage(voltage);
    }

    public double get() {
        return mMotor.get();
    }

    public void setInverted(boolean isInverted) {
        mMotor.setInverted(isInverted);        
    }

    public boolean getInverted() {
        return mMotor.getInverted();
    }

    public void setNeutralOperation(NeutralOperation neutralOperation) {
        switch(neutralOperation) {
            case kBrake :
                mMotor.setIdleMode(IdleMode.kBrake);
                break;
            case kCoast :
                mMotor.setIdleMode(IdleMode.kCoast);
                break;
        }        
    }

    public SOTA_Encoder getEncoder() {
        return mEncoder;
    }

    public double getEncoderVelocity() {
        if (mEncoder == null) return getIntegratedEncoderVelocity();
        return mEncoder.getVelocity();
    }

    public double getEncoderPosition() {
        if (mEncoder == null) return getIntegratedEncoderPosition();
        return mEncoder.get();
    }

    public double getIntegratedEncoderVelocity() {
        return mMotor.getEncoder().getVelocity();
    }

    public double getIntegratedEncoderPosition() {
        return mMotor.getEncoder().getPosition();
    }

    public void resetNativeEncoder() {
        mMotor.getEncoder().setPosition(0.0);
    }

    public double getMotorCurrent() {
        return mMotor.getOutputCurrent();
    }

    public void setCurrentLimit(int amps) {
        mMotor.setSmartCurrentLimit(amps);
    }

    public double getMotorTemperature() {
        return mMotor.getMotorTemperature();
    }

    public MotorPositionLimits getLimits() {
        return mMotorLimits;
    }

    public void setPositionLimits(MotorPositionLimits limits) {
        mMotorLimits = limits;
    }

    public void disable() {
        mMotor.disable();        
    }

    public void stopMotor() {
        mMotor.stopMotor();
        
    }

    @Override
    public void resetIntegratedEncoder() {
       mMotor.getEncoder().setPosition(0); 
    }

    @Override
    public double getLowerLimit() {
       return mMotorLimits.getLowerLimit(); 
    }

    @Override
    public double getUpperLimit() {
       return mMotorLimits.getUpperLimit(); 
    }

    @Override
    public boolean atUpperLimit() {
       if (getLimitState() == MotorPositionLimitStates.AT_UPPER_LIMIT) {
        return true;
       }else {
        return false;
       } 
    }

    @Override
    public boolean atLowerLimit() {
      if (getLimitState() == MotorPositionLimitStates.AT_LOWER_LIMIT){
        return true;
      }else {
        return false;
      } 
    }

    @Override
    public MotorPositionLimitStates getLimitState() {
        double position = getEncoderPosition();
        if (position < mMotorLimits.getLowerLimit()) {
            return MotorPositionLimitStates.TOO_LOW;
        }else if(position > mMotorLimits.getUpperLimit()) {
            return MotorPositionLimitStates.TOO_HIGH;
        }else if (position == mMotorLimits.getLowerLimit()) {
            return MotorPositionLimitStates.AT_LOWER_LIMIT;
        }else if (position == mMotorLimits.getUpperLimit()) {
            return MotorPositionLimitStates.AT_UPPER_LIMIT;
        }else {
            return MotorPositionLimitStates.IN_RANGE;
        }
    }

    @Override
    public void resetEncoder() {
       mEncoder.reset(); 
    }

    @Override
    public NeutralOperation getNeutralOperation() throws NullNeutralOperationException {
       switch (mMotor.getIdleMode()) {
        case kBrake:
            return NeutralOperation.kBrake; 
        case kCoast:
            return NeutralOperation.kCoast;
        default:
            throw new NullNeutralOperationException("SOTA_SparkMax no idle mode returned by SparkMax");
       }
    }
}