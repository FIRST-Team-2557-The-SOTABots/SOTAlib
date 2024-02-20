package SOTAlib.MotorController;

import java.util.Optional;
import java.util.function.Supplier;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import SOTAlib.Config.MotorControllerConfig;

public class SOTA_FalconFX implements SOTA_MotorController {
    private double kNativeCountsPerRevolution = 2048;
    private final TalonFX mMotor;
    private Optional<MotorPositionLimits> mMotorLimits; 
    private Supplier<NullConfigException> mNullExceptionSupplier;

    public SOTA_FalconFX(MotorControllerConfig config, TalonFX motor) throws NullConfigException {
        this(config, motor, null);
    }

    public SOTA_FalconFX(MotorControllerConfig config, TalonFX falcon, MotorPositionLimits limits)
            throws NullConfigException {
        if (config == null)
            throw new NullConfigException("SOTA_FalconFX: config not created");
        this.mMotorLimits = Optional.ofNullable(limits);
        this.mMotor = falcon;
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
                mMotor.setNeutralMode(NeutralModeValue.Brake);
                break;
            case kCoast:
                mMotor.setNeutralMode(NeutralModeValue.Coast);
                break;
        }

    }

    public double getEncoderVelocity() {
        return mMotor.getRotorVelocity().getValueAsDouble();
    }

    public double getEncoderPosition() {
        return mMotor.getRotorPosition().getValueAsDouble();
    }

    public double getMotorTemperature() {
        return mMotor.getDeviceTemp().getValueAsDouble();
    }

    public double getMotorCurrent() {
        return mMotor.getSupplyCurrent().getValueAsDouble();
    }

    public void setCurrentLimit(int amps) {
        CurrentLimitsConfigs configuration = new CurrentLimitsConfigs();
        configuration.SupplyCurrentLimit = amps;
        configuration.SupplyCurrentLimitEnable = true;
        mMotor.getConfigurator().apply(configuration);
    }

    public void setPositionLimits(MotorPositionLimits limits) {
        mMotorLimits = Optional.ofNullable(limits);
    }

    public MotorPositionLimits getLimits() throws NullConfigException {
        return mMotorLimits.orElseThrow(mNullExceptionSupplier);
    }

    public void disable() {
        mMotor.disable();
    }

    public void stopMotor() {
        mMotor.stopMotor();
    }

    /**
     * Converts from native encoder velocity to RPM
     * 
     * @Deprecated CTRE switched to revolutions
     * @param ticks native encoder ticks
     * @return velocity in rotations per minute
     */
    private double nativeVelocityToRPM(double ticks) {
        return ticks * 600 / kNativeCountsPerRevolution;
    }

    /**
     * Converts from native encoder position to revolutions
     * 
     * @Deprecated CTRE switched to revolutions
     * @param ticks native encoder ticks
     * @return position in rotations
     */
    private double nativePositionToRotations(double ticks) {
        return ticks / kNativeCountsPerRevolution;
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
            throw new NullConfigException("SOTA_FalconFX: no limits");
        }
    }

    @Override
    public void resetEncoder() {
        mMotor.setPosition(0.0);
    }

    @Override
    public NeutralOperation getNeutralOperation() throws NullNeutralOperationException {
        throw new NullNeutralOperationException(
                "SOTA_FalconFX: Ctre didn't implement getting the Neutral Operation from the motor. If you really need to get the Neutral Operation keep track of it in a seperate variable or get build to use a different motor. If you actually get this error email me howardwalz@gmail.com, good luck! :)");
    }

    @Override
    public void setEncoderPosition(double position) {
        mMotor.setPosition(position);
    }
}
