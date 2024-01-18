package SOTAlib.MotorController;

import java.util.Optional;
import java.util.function.Supplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class SOTA_TalonSRX implements SOTA_MotorController {

    private WPI_TalonSRX mMotor;
    private double kNativeCountsPerRevolution = 0;
    private Optional<MotorPositionLimits> mMotorLimits;
    private Supplier<NullConfigException> mExceptionSupplier;

    public SOTA_TalonSRX(WPI_TalonSRX motor, double countsPerRevolution) {
        this(motor, null, countsPerRevolution);
    }

    public SOTA_TalonSRX(WPI_TalonSRX motor, MotorPositionLimits limits, double countsPerRevolution) {
        this.mMotor = motor;
        this.kNativeCountsPerRevolution = countsPerRevolution;
        this.mMotorLimits = Optional.ofNullable(limits);
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
        return mMotorLimits.orElseThrow();
    }

    private double nativeVelocityToRPM(double ticks) {
        return ticks * 600 / kNativeCountsPerRevolution;
    }

    private double nativePositionToRotations(double ticks) {
        return ticks / kNativeCountsPerRevolution;
    }

    @Override
    public void setPositionLimits(MotorPositionLimits limits) {
        this.mMotorLimits = Optional.ofNullable(limits);
    }

    @Override
    public double getLowerLimit() throws NullConfigException {
        return mMotorLimits.orElseThrow(mExceptionSupplier).getLowerLimit();
    }

    @Override
    public double getUpperLimit() throws NullConfigException {
        return mMotorLimits.orElseThrow(mExceptionSupplier).getUpperLimit();
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
            throw new NullConfigException("SOTA_TalonSRX: no limits");
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

    @Override
    public void setEncoderPosition(double position) {
        mMotor.setSelectedSensorPosition(position * kNativeCountsPerRevolution);
    }

}
