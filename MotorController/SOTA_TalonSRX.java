package SOTAlib.MotorController;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import SOTAlib.Config.MotorControllerConfig;
import SOTAlib.Encoder.SOTA_Encoder;
import SOTAlib.Factories.EncoderFactory;

public class SOTA_TalonSRX implements SOTA_MotorController {

    private WPI_TalonSRX mMotor;
    private double kNativeCountsPerRevolution = 0;
    private MotorPositionLimits mMotorLimits; // TODO: make optional

    public SOTA_TalonSRX(MotorControllerConfig config) throws NullConfigException {
        if (config == null)
            throw new NullConfigException("SOTA_TalonSRX: No config");

        this.mMotor = new WPI_TalonSRX(config.getPort());
        mMotor.setInverted(config.getIsInverted());

        switch (config.getNeutralOperation()) {
            case "BRAKE":
                mMotor.setNeutralMode(NeutralMode.Brake);
            case "COAST":
                mMotor.setNeutralMode(NeutralMode.Coast);
        }

        if (config.getCurrentLimit() != 0) {
            mMotor.configPeakCurrentLimit(config.getCurrentLimit());
        } else {
            System.out.println("SOTA_FalconFX: INFO: no current limit");
        }

        try {
            MotorPositionLimits limits = new MotorPositionLimits(config.getMotorLimitsConfig().getLowerLimit(),
                    config.getMotorLimitsConfig().getUpperLimit(), config.getMotorLimitsConfig().getFinalLimits());
            this.mMotorLimits = limits;
        } catch (NullPointerException e) {
            System.out.println("SOTA_FalconFX: INFO: no motor limits");
        }

        this.kNativeCountsPerRevolution = config.getCountsPerRevolution();
    }

    @Override
    public void set(double speed) {
        mMotor.set(ControlMode.PercentOutput, speed);
    }

    @Override
    public double get() {
        return mMotor.get();
    }

    @Override
    public void disable() {
        mMotor.disable();
    }

    @Override
    public void stopMotor() {
        mMotor.disable();
    }

    @Override
    public void setInverted(boolean isInverted) {
        mMotor.setInverted(isInverted);
    }

    @Override
    public boolean getInverted() {
        return mMotor.getInverted();
    }

    @Override
    public void setNeutralOperation(NeutralOperation neutralOperation) {
        switch (neutralOperation) { // TODO: test this
            case kBrake:
                mMotor.setNeutralMode(NeutralMode.Brake);
                break;
            case kCoast:
                mMotor.setNeutralMode(NeutralMode.Coast);
                break;
        }
    }

    public double getEncoderVelocity() {
        return nativeVelocityToRPM(mMotor.getSelectedSensorVelocity());
    }

    public double getEncoderPosition() {
        return nativePositionToRotations(mMotor.getSelectedSensorPosition());
    }

    @Override
    public double getMotorTemperature() {
        return mMotor.getTemperature();
    }

    @Override
    public double getMotorCurrent() {
        return mMotor.getStatorCurrent();
    }

    @Override
    public void setCurrentLimit(int amps) {
        SupplyCurrentLimitConfiguration config = new SupplyCurrentLimitConfiguration(true, amps, amps, 1.0);
        mMotor.configSupplyCurrentLimit(config); // TODO: test this

    }

    @Override
    public MotorPositionLimits getLimits() {
        return mMotorLimits;
    }

    private double nativeVelocityToRPM(double ticks) {
        return ticks * 600 / kNativeCountsPerRevolution;
    }

    private double nativePositionToRotations(double ticks) {
        return ticks / kNativeCountsPerRevolution;
    }

    @Override
    public void setPositionLimits(MotorPositionLimits limits) {
        this.mMotorLimits = limits;
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
        } else {
            return false;
        }
    }

    @Override
    public boolean atLowerLimit() {
        if (getLimitState() == MotorPositionLimitStates.AT_LOWER_LIMIT) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public MotorPositionLimitStates getLimitState() {
        double position = getEncoderPosition();
        if (position < mMotorLimits.getLowerLimit()) {
            return MotorPositionLimitStates.TOO_LOW;
        } else if (position > mMotorLimits.getUpperLimit()) {
            return MotorPositionLimitStates.TOO_HIGH;
        } else if (position == mMotorLimits.getLowerLimit()) {
            return MotorPositionLimitStates.AT_LOWER_LIMIT;
        } else if (position == mMotorLimits.getUpperLimit()) {
            return MotorPositionLimitStates.AT_UPPER_LIMIT;
        } else {
            return MotorPositionLimitStates.IN_RANGE;
        }
    }

    public void resetEncoder() {
        mMotor.setSelectedSensorPosition(0.0);
    }

    @Override
    public NeutralOperation getNeutralOperation() throws NullNeutralOperationException {
        throw new NullNeutralOperationException(
                "SOTA_TalonSRX: Ctre didn't implement getting the Neutral Operation from the motor. If you really need to get the Neutral Operation keep track of it in a seperate variable or get build to use a different motor. If you actually get this error email me howardwalz@gmail.com, good luck! :)");
    }

}
