package lib.MotorController;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import lib.Config.MotorControllerConfig;
import lib.Encoder.SOTA_Encoder;

public class Falcon implements SOTA_MotorController {
    private static final double kNativeCountsPerRevolution = 2048;
    private final WPI_TalonFX mMotor;
    private final SOTA_Encoder mEncoder;
    private MotorLimits mMotorLimits;

    // TODO: check these constructors
    public Falcon(WPI_TalonFX motor, MotorControllerConfig config){
        this(motor, (SOTA_Encoder) null, config);
    }

    public Falcon(WPI_TalonFX motor, SOTA_Encoder encoder, MotorControllerConfig config) {
        this(motor, encoder, (MotorLimits) null, config);
    }

    public Falcon(WPI_TalonFX motor, MotorLimits limits, MotorControllerConfig config) {
        this(motor, (SOTA_Encoder) null, limits, config);
    }

    public Falcon(WPI_TalonFX motor, SOTA_Encoder encoder, MotorLimits limits, MotorControllerConfig config) {
        this.mMotor = motor;
        this.mEncoder = encoder;
        setInverted(config.getIsInverted());
        switch (config.getNeutralOperation()) {
            case "BRAKE" :
                setNeutralOperation(NeutralOperation.kBrake);
                break;
            case "COAST" : 
                setNeutralOperation(NeutralOperation.kCoast);
                break;
        }
        if (config.getCurrentLimit() != 0.0) {
            setCurrentLimit(config.getCurrentLimit());
        }

    }

    public void set(double speed) {
        if(mMotorLimits != null){
            if(speed < 0){
                if(mMotorLimits.getLowerLimit() > getEncoderPosition()) speed = 0;
            }else if(speed > 0){
                if(mMotorLimits.getUpperLimit() < getEncoderPosition()) speed = 0;
            }
            
        }
        mMotor.set(TalonFXControlMode.PercentOutput, speed);        
    }

    

    public double get() {
        return mMotor.getMotorOutputPercent();
    }

    public void setInverted(boolean isInverted) {
        mMotor.setInverted(isInverted);        
    }

    public boolean getInverted() {
        return mMotor.getInverted();
    }

    public void setNeutralOperation(NeutralOperation neutralOperation) {
        switch (neutralOperation) {
            case kBrake :
                mMotor.setNeutralMode(NeutralMode.Brake);
                break;
            case kCoast :
                mMotor.setNeutralMode(NeutralMode.Coast);
                break;
        }
        
    }

    public SOTA_Encoder getEncoder() {
        return mEncoder;
    }

    public double getEncoderVelocity() {
        return mMotor.getSelectedSensorVelocity();
    }

    public double getEncoderPosition() {
        return mEncoder.get();
    }

    public double getNativeEncoderVelocity() {
        return mMotor.getSelectedSensorVelocity();
    }

    public double getNativeEncoderPosition() {
        return mMotor.getSelectedSensorPosition();
    }

    public double getNativeCountsPerRevolution() {
        return kNativeCountsPerRevolution;
    }

    public void resetNativeEncoder() {
        mMotor.setSelectedSensorPosition(0.0);
    }

    public double getMotorTemperature() {
        return mMotor.getTemperature();
    }

    public double getMotorCurrent() {
        return mMotor.getStatorCurrent();
    }

    // TODO: arbitrary number for current limits works for swerve but who knows what will happen
    public void setCurrentLimit(int amps) {
        StatorCurrentLimitConfiguration config = new StatorCurrentLimitConfiguration(true, amps, amps, 1.0); 
        mMotor.configStatorCurrentLimit(config);
    }

    public void setLimits(MotorLimits limits) {
        mMotorLimits = limits;
    }

    public MotorLimits getLimits() {
        return mMotorLimits;
    }

    public void disable() {
        mMotor.neutralOutput();        
    }

    public void stopMotor() {
        mMotor.neutralOutput();
        
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setNeutralOperation() {
        // TODO Auto-generated method stub
        
    }

    

}
