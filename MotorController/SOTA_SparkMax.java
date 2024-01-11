package SOTAlib.MotorController;

import java.util.Optional;
import java.util.function.Supplier;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkMax;

import SOTAlib.Config.MotorControllerConfig;

public class SOTA_SparkMax implements SOTA_MotorController {
    private final CANSparkMax mMotor;
    private Optional<MotorPositionLimits> mMotorLimits;
    private Supplier<NullConfigException> mNullExceptionSupplier;

    public SOTA_SparkMax(MotorControllerConfig config, CANSparkMax motor) throws NullConfigException {
        this(config, motor, null);
    }

    public SOTA_SparkMax(MotorControllerConfig config, CANSparkMax motor, MotorPositionLimits limits)
            throws NullConfigException {
        if (config == null) {
            throw new NullConfigException("SOTA_SparkMax: Config not created properly");
        }
        this.mMotor = motor;
        this.mMotorLimits = Optional.ofNullable(limits);

    }

    public void set(double speed) {
        if (mMotorLimits.isPresent()) {
            if (speed < 0) {
                if (mMotorLimits.get().getLowerLimit() > getEncoderPosition())
                    speed = 0;
            } else if (speed > 0) {
                if (mMotorLimits.get().getUpperLimit() < getEncoderPosition())
                    speed = 0;
            }

        }
        mMotor.set(speed);
    }

    public void setVoltage(double voltage) {
        if (mMotorLimits.isPresent()) {
            if (voltage < 0) {
                if (mMotorLimits.get().getLowerLimit() > getEncoderPosition())
                    voltage = 0;
            } else if (voltage > 0) {
                if (mMotorLimits.get().getUpperLimit() < getEncoderPosition())
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

    public MotorPositionLimits getLimits() throws NullConfigException {
        return mMotorLimits.orElseThrow(mNullExceptionSupplier);
    }

    public void setPositionLimits(MotorPositionLimits limits) {
        mMotorLimits = Optional.ofNullable(limits);
    }

    public void disable() {
        mMotor.disable();
    }

    public void stopMotor() {
        mMotor.stopMotor();

    }

    @Override
    public double getLowerLimit() throws NullConfigException {
        return mMotorLimits.orElseThrow(mNullExceptionSupplier).getLowerLimit();
    }

    @Override
    public double getUpperLimit() throws NullConfigException {
        return mMotorLimits.orElseThrow(mNullExceptionSupplier).getUpperLimit();
    }

    @Override
    public boolean atUpperLimit() throws NullConfigException {
        if (getLimitState() == MotorPositionLimitStates.AT_UPPER_LIMIT) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean atLowerLimit() throws NullConfigException {
        if (getLimitState() == MotorPositionLimitStates.AT_LOWER_LIMIT) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public MotorPositionLimitStates getLimitState() throws NullConfigException {
        if (mMotorLimits.isPresent()) {
            double position = getEncoderPosition();
            if (position < mMotorLimits.get().getLowerLimit()) {
                return MotorPositionLimitStates.TOO_LOW;
            } else if (position > mMotorLimits.get().getUpperLimit()) {
                return MotorPositionLimitStates.TOO_HIGH;
            } else if (position == mMotorLimits.get().getLowerLimit()) {
                return MotorPositionLimitStates.AT_LOWER_LIMIT;
            } else if (position == mMotorLimits.get().getUpperLimit()) {
                return MotorPositionLimitStates.AT_UPPER_LIMIT;
            } else {
                return MotorPositionLimitStates.IN_RANGE;
            }
        } else {
            throw new NullConfigException("SOTA_SparkMax: no limits");
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

    @Override
    public void setEncoderPosition(double position) {
        mMotor.getEncoder().setPosition(position);
    }
}