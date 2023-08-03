package SOTAlib.MotorController;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import SOTAlib.Config.MotorControllerConfig;

public class SOTA_SparkMax implements SOTA_MotorController {
    private final CANSparkMax mMotor;
    private MotorPositionLimits mMotorLimits; // TODO: make into an optional

    public SOTA_SparkMax(MotorControllerConfig config) throws NullConfigException {
        if (config == null) {
            throw new NullConfigException("SOTA_SparkMax: Config not created properly");
        }
        MotorType motorType;
        switch (config.getMotorType()) {
            case ("BRUSHLESS"):
                motorType = MotorType.kBrushless;
                break;
            case ("BRUSHED"):
                motorType = MotorType.kBrushed;
                break;
            default:
                throw new IllegalArgumentException("Illegal motor type");
        }

        mMotor = new CANSparkMax(config.getPort(), motorType);
        mMotor.setInverted(config.getIsInverted());
        switch (config.getNeutralOperation()) {
            case "BRAKE":
                mMotor.setIdleMode(IdleMode.kBrake);
            case "COAST":
                mMotor.setIdleMode(IdleMode.kCoast);
        }

        if (config.getCurrentLimit() != 0) {
            mMotor.setSmartCurrentLimit(config.getCurrentLimit());
        } else {
            System.out.println("SOTA_SparkMax: INFO: No current limit set");
        }

        try {
            MotorPositionLimits limits = new MotorPositionLimits(config.getMotorLimitsConfig().getLowerLimit(),
                    config.getMotorLimitsConfig().getUpperLimit(), config.getMotorLimitsConfig().getFinalLimits());
            this.mMotorLimits = limits;
        } catch (NullPointerException e) {
            System.out.println("SOTA_FalconFX: INFO: no motor limits");
        }
    }

    public void set(double speed) {
        if (mMotorLimits != null) {
            if (speed < 0) {
                if (mMotorLimits.getLowerLimit() > getEncoderPosition())
                    speed = 0;
            } else if (speed > 0) {
                if (mMotorLimits.getUpperLimit() < getEncoderPosition())
                    speed = 0;
            }

        }
        mMotor.set(speed);
    }

    public void setVoltage(double voltage) {
        if (mMotorLimits != null) {
            if (voltage < 0) {
                if (mMotorLimits.getLowerLimit() > getEncoderPosition())
                    voltage = 0;
            } else if (voltage > 0) {
                if (mMotorLimits.getUpperLimit() < getEncoderPosition())
                    voltage = 0;
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
        switch (neutralOperation) {
            case kBrake:
                mMotor.setIdleMode(IdleMode.kBrake);
                break;
            case kCoast:
                mMotor.setIdleMode(IdleMode.kCoast);
                break;
        }
    }

    public double getEncoderVelocity() {
        return mMotor.getEncoder().getVelocity();
    }

    public double getEncoderPosition() {
        return mMotor.getEncoder().getPosition();
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

    @Override
    public void resetEncoder() {
        mMotor.getEncoder().setPosition(0);
    }

    @Override
    public NeutralOperation getNeutralOperation() throws NullNeutralOperationException {
        switch (mMotor.getIdleMode()) {
            case kBrake:
                return NeutralOperation.kBrake;
            case kCoast:
                return NeutralOperation.kCoast;
            default:
                throw new NullNeutralOperationException("SOTA_SparkMax: no idle mode returned by SparkMax");
        }
    }
}