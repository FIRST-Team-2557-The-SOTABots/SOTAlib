package SOTAlib.MotorController;

import java.lang.StackWalker.Option;
import java.util.Optional;
import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import SOTAlib.Config.MotorControllerConfig;

public class SOTA_FalconFX implements SOTA_MotorController {
    private double kNativeCountsPerRevolution = 2048;
    private final WPI_TalonFX mMotor;
    private Optional<MotorPositionLimits> mMotorLimits; // TODO: make optional
    private Supplier<NullConfigException> mNullExceptionSupplier;

    public SOTA_FalconFX(MotorControllerConfig config, WPI_TalonFX motor) throws NullConfigException {
        this(config, motor, null);
    }

    public SOTA_FalconFX(MotorControllerConfig config, WPI_TalonFX falcon, MotorPositionLimits limits)
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

    public double getMotorTemperature() {
        return mMotor.getTemperature();
    }

    public double getMotorCurrent() {
        return mMotor.getStatorCurrent();
    }

    // TODO: arbitrary number for current limits works for swerve but who knows what
    // will happen
    public void setCurrentLimit(int amps) {
        StatorCurrentLimitConfiguration config = new StatorCurrentLimitConfiguration(true, amps, amps, 1.0);
        mMotor.configStatorCurrentLimit(config);
    }

    public void setPositionLimits(MotorPositionLimits limits) {
        mMotorLimits = Optional.ofNullable(limits);
    }

    public MotorPositionLimits getLimits() throws NullConfigException {
        return mMotorLimits.orElseThrow(mNullExceptionSupplier);
    }

    public void disable() {
        mMotor.neutralOutput();
    }

    public void stopMotor() {
        mMotor.neutralOutput();
    }

    private double nativeVelocityToRPM(double ticks) {
        return ticks * 600 / kNativeCountsPerRevolution;
    }

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
        mMotor.setSelectedSensorPosition(0.0);
    }

    @Override
    public NeutralOperation getNeutralOperation() throws NullNeutralOperationException {
        throw new NullNeutralOperationException(
                "SOTA_FalconFX: Ctre didn't implement getting the Neutral Operation from the motor. If you really need to get the Neutral Operation keep track of it in a seperate variable or get build to use a different motor. If you actually get this error email me howardwalz@gmail.com, good luck! :)");
    }

    @Override
    public void setEncoderPosition(double position) {
       mMotor.setSelectedSensorPosition(position * kNativeCountsPerRevolution); 
    }
}
