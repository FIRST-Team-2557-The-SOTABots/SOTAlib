package SOTAlib.MotorController;

import java.util.Optional;
import java.util.function.Supplier;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.SparkFlex;

import SOTAlib.Config.MotorControllerConfig;

public class SOTA_SparkFlex implements SOTA_MotorController {

    private final SparkFlex mMotor;
    private Optional<MotorPositionLimits> mMotorLimits;
    private Supplier<NullConfigException> mNullExceptionSupplier;

    public SOTA_SparkFlex(MotorControllerConfig config, SparkFlex motor) throws NullConfigException {
        this(config, motor, null);
    }

    public SOTA_SparkFlex(MotorControllerConfig config, SparkFlex motor, MotorPositionLimits limits)
            throws NullConfigException {
        if (config == null) {
            throw new NullConfigException("SOTA_SparkFlex: Config not created properly");
        }
        this.mMotor = motor;
        this.mMotorLimits = Optional.ofNullable(limits);

    }

    @Override
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

    @Override
    public double get() {
        return mMotor.get();
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
    public void disable() {
        mMotor.disable();
    }

    @Override
    public void stopMotor() {
        mMotor.stopMotor();
    }

    @Override
    public void setNeutralOperation(NeutralOperation neutralOperation) {
        SparkFlexConfig config = new SparkFlexConfig();
        switch (neutralOperation) {
            case kBrake:
                config.idleMode(IdleMode.kBrake);
                break;
            case kCoast:
                config.idleMode(IdleMode.kCoast);
                break;
        }
        mMotor.configure(config, SparkFlex.ResetMode.kNoResetSafeParameters, SparkFlex.PersistMode.kNoPersistParameters);
    }

    @Override
    public double getEncoderVelocity() {
        return mMotor.getEncoder().getVelocity();
    }

    @Override
    public double getEncoderPosition() {
        return mMotor.getEncoder().getPosition();
    }

    @Override
    public double getMotorTemperature() {
        return mMotor.getMotorTemperature();
    }

    @Override
    public double getMotorCurrent() {
        return mMotor.getOutputCurrent();
    }

    @Override
    public void setCurrentLimit(int amps) {
        SparkFlexConfig config = new SparkFlexConfig();
        config.smartCurrentLimit(amps);
        mMotor.configure(config, SparkFlex.ResetMode.kNoResetSafeParameters, SparkFlex.PersistMode.kNoPersistParameters);
    }

    @Override
    public MotorPositionLimits getLimits() throws NullConfigException {
        return mMotorLimits.orElseThrow(mNullExceptionSupplier);
    }

    @Override
    public void setPositionLimits(MotorPositionLimits limits) {
        mMotorLimits = Optional.ofNullable(limits);
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
            throw new NullConfigException("SOTA_SparkFlex: no limits");
        }
    }

    @Override
    public void resetEncoder() {
        mMotor.getEncoder().setPosition(0);
    }

    @Override
    public NeutralOperation getNeutralOperation() throws NullNeutralOperationException {
        switch (mMotor.configAccessor.getIdleMode()) {
            case kBrake:
                return NeutralOperation.kBrake;
            case kCoast:
                return NeutralOperation.kCoast;
            default:
                throw new NullNeutralOperationException("SOTA_SparkFlex: no idle mode returned by SparkFlex");
        }
    }

    @Override
    public void setEncoderPosition(double position) {
        mMotor.getEncoder().setPosition(position);
    }

}
