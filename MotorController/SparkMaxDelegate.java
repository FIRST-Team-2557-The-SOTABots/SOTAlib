package lib.MotorController;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import lib.Config.MotorControllerConfig;
import lib.Encoder.SOTA_Encoder;

public class SparkMaxDelegate implements SOTA_MotorController{
    private final CANSparkMax mMotor;
    private final SOTA_Encoder mEncoder;
    private MotorLimits mMotorLimits;
    // TODO: check these constructors
    public SparkMaxDelegate(CANSparkMax motor, MotorControllerConfig config) {
        this(motor, (SOTA_Encoder) null, config);
    }

    public SparkMaxDelegate(CANSparkMax motor, SOTA_Encoder encoder, MotorControllerConfig config) {
        this(motor, encoder, (MotorLimits) null, config);
    }

    public SparkMaxDelegate(CANSparkMax motor, MotorLimits limits, MotorControllerConfig config){
        this(motor, (SOTA_Encoder) null, limits ,config);
    }

    public SparkMaxDelegate(CANSparkMax motor, SOTA_Encoder encoder, MotorLimits limits, MotorControllerConfig config){
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
        if (mEncoder == null) return getNativeEncoderVelocity();
        return mEncoder.getVelocity();
    }

    public double getEncoderPosition() {
        if (mEncoder == null) return getNativeEncoderPosition();
        return mEncoder.get();
    }

    public double getNativeEncoderVelocity() {
        return mMotor.getEncoder().getVelocity();
    }

    public double getNativeEncoderPosition() {
        return mMotor.getEncoder().getPosition();
    }

    public double getNativeCountsPerRevolution() {
        return mMotor.getEncoder().getCountsPerRevolution();
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

    public MotorLimits getLimits() {
        return mMotorLimits;
    }

    public void setLimits(MotorLimits limits) {
        mMotorLimits = limits;
    }

    public void disable() {
        mMotor.disable();        
    }

    public void stopMotor() {
        mMotor.stopMotor();
        
    }

    @Override
    public double getLowerLimit() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getUpperLimit() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean atUpperLimit() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean atLowerLimit() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void resetEncoder() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean getNeutralOperation() {
        return mMotor.getIdleMode() == IdleMode.kBrake;
    }

    @Override
    public void setNeutralOperation() {
        // TODO Auto-generated method stub
        
    }


}