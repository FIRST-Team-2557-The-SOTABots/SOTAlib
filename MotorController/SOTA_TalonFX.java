package SOTAlib.MotorController;

import java.util.Optional;
import java.util.function.Supplier;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import SOTAlib.Config.MotorControllerConfig;

/**
 * Universal Wrapper for CTRE Phoenix 6 devices including 
 * Falcon 500, Kraken X60, and Kraken X44 motor controllers.
 */
public class SOTA_TalonFX implements SOTA_MotorController {
    private double kNativeCountsPerRevolution = 2048;
    private final TalonFX mMotor;
    private Optional<MotorPositionLimits> mMotorLimits; 
    private Supplier<NullConfigException> mNullExceptionSupplier = () -> new NullConfigException("SOTA_TalonFX: null limit reference");

    public SOTA_TalonFX(MotorControllerConfig config, TalonFX motor) throws NullConfigException {
        this(config, motor, null);
    }

    public SOTA_TalonFX(MotorControllerConfig config, TalonFX falcon, MotorPositionLimits limits)
            throws NullConfigException {
        if (config == null)
            throw new NullConfigException("SOTA_TalonFX: config not created");
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

    @Override
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
        MotorOutputConfigs outputConfigs = new MotorOutputConfigs();
        outputConfigs.Inverted = isInverted 
            ? InvertedValue.Clockwise_Positive 
            : InvertedValue.CounterClockwise_Positive;
        mMotor.getConfigurator().apply(outputConfigs);
    }

    public boolean getInverted() {
        return mMotor.getAppliedRotorPolarity().getValue() == com.ctre.phoenix6.signals.AppliedRotorPolarityValue.PositiveIsClockwise;
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
     * Commands the TalonFX onboard closed-loop controller towards a reference target.
     * Maps to Phoenix 6 ControlRequests seamlessly.
     * 
     * @param reference The target value (Rotations for Position, RPS for Velocity)
     * @param type The control mode string ("POSITION" or "VELOCITY")
     */
    public void setReference(double reference, String type) {
        if (type.equalsIgnoreCase("POSITION")) {
            var request = new com.ctre.phoenix6.controls.PositionVoltage(reference);
            mMotor.setControl(request);
        } else if (type.equalsIgnoreCase("VELOCITY")) {
            var request = new com.ctre.phoenix6.controls.VelocityVoltage(reference);
            mMotor.setControl(request);
        }
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
        return getLimitState() == MotorPositionLimitStates.AT_UPPER_LIMIT;
    }

    @Override
    public boolean atLowerLimit() throws NullConfigException {
        return getLimitState() == MotorPositionLimitStates.AT_LOWER_LIMIT;
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
            throw new NullConfigException("SOTA_TalonFX: no position limits found");
        }
    }

    @Override
    public void resetEncoder() {
        mMotor.setPosition(0.0);
    }

    @Override
    public NeutralOperation getNeutralOperation() throws NullNeutralOperationException {
        throw new NullNeutralOperationException(
                "SOTA_TalonFX: Ctre didn't implement getting the Neutral Operation from the motor.");
    }

    @Override
    public void setEncoderPosition(double position) {
        mMotor.setPosition(position);
    }
}
