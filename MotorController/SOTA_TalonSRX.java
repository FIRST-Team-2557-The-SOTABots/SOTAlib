package SOTAlib.MotorController;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import SOTAlib.Config.MotorControllerConfig;
import SOTAlib.Encoder.SOTA_Encoder;

public class SOTA_TalonSRX implements SOTA_MotorController{

    private WPI_TalonSRX motor;
    private SOTA_Encoder encoder;

    public SOTA_TalonSRX(WPI_TalonSRX motor){
        this.motor = motor;
    }

    public SOTA_TalonSRX(WPI_TalonSRX motor, SOTA_Encoder encoder, MotorLimits limits, MotorControllerConfig config) {
        this.motor = motor;
        this.encoder = encoder; //TODO: test this
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

    @Override
    public void set(double speed) {
        motor.set(ControlMode.PercentOutput, speed);   
    }

    @Override
    public double get() {
        return motor.get();
    }

    @Override
    public void disable() {
        motor.disable();
    }

    @Override
    public void stopMotor() {
        motor.disable();        
    }

    @Override
    public void setInverted(boolean isInverted) {
        motor.setInverted(isInverted);        
    }

    @Override
    public boolean getInverted() {
        return motor.getInverted();
    }

    @Override
    public void setNeutralOperation(NeutralOperation neutralOperation) {
        switch(neutralOperation){ //TODO: test this
            case kBrake:
                motor.setNeutralMode(NeutralMode.Brake);
                break;
            case kCoast:
                motor.setNeutralMode(NeutralMode.Coast);
                break;
        }
    }

    @Override
    public SOTA_Encoder getEncoder() {
        return null;
    }

    @Override
    public double getEncoderVelocity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getEncoderPosition() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getNativeEncoderVelocity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getNativeEncoderPosition() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getNativeCountsPerRevolution() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void resetNativeEncoder() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public double getMotorTemperature() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getMotorCurrent() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setCurrentLimit(int amps) {
        SupplyCurrentLimitConfiguration config = new SupplyCurrentLimitConfiguration(true, amps, amps, 1.0);
        motor.configSupplyCurrentLimit(config); //TODO: test this
        
    }

    @Override
    public MotorLimits getLimits() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLimits(MotorLimits limits) {
        // TODO Auto-generated method stub
        
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
