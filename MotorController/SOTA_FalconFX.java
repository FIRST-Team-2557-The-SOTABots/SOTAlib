package SOTAlib.MotorController;

import java.util.Optional;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import SOTAlib.Config.MotorControllerConfig;
import SOTAlib.Encoder.SOTA_Encoder;
import SOTAlib.Factories.EncoderFactory;

public class SOTA_FalconFX implements SOTA_MotorController {
    private double kNativeCountsPerRevolution = 2048;
    private final WPI_TalonFX mMotor;
    private Optional<SOTA_Encoder> mEncoder;
    private MotorPositionLimits mMotorLimits; // TODO: make optional

    public SOTA_FalconFX(MotorControllerConfig config) throws NullConfigException {
        if (config == null)
            throw new NullConfigException("SOTA_FalconFX: config not created");
        this.mMotor = new WPI_TalonFX(config.getPort());
        mMotor.setInverted(config.getIsInverted());
        switch (config.getNeutralOperation()) {
            case "BRAKE":
                mMotor.setNeutralMode(NeutralMode.Brake);
            case "COAST":
                mMotor.setNeutralMode(NeutralMode.Coast);
        }

        if (config.getCurrentLimit() != 0) {
            StatorCurrentLimitConfiguration currentConfig = new StatorCurrentLimitConfiguration(true,
                    config.getCurrentLimit(), config.getCurrentLimit(), 1.0);
            mMotor.configStatorCurrentLimit(currentConfig);
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

        try {
            SOTA_Encoder encoder = EncoderFactory.generateEncoder(config.getEncoderConfig());
            this.mEncoder = Optional.ofNullable(encoder);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate Encoder", e); // TODO: make work
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

    public SOTA_Encoder getEncoder() throws NullConfigException {
        return mEncoder.orElseThrow(NullConfigException::nullEncoder);
    }

    public double getEncoderVelocity() {
        if (mEncoder.isEmpty()) {
            return getIntegratedEncoderVelocity();
        } else {
            return mEncoder.get().getVelocity();
        }
    }

    public double getEncoderPosition() {
        if (mEncoder.isEmpty()) {
            return getIntegratedEncoderPosition();
        } else {
            return mEncoder.get().get();
        }
    }

    public double getIntegratedEncoderVelocity() {
        return nativeVelocityToRPM(mMotor.getSelectedSensorVelocity());
    }

    public double getIntegratedEncoderPosition() {
        return nativePositionToRotations(mMotor.getSelectedSensorPosition());
    }

    public void resetIntegratedEncoder() {
        mMotor.setSelectedSensorPosition(0.0);
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
        mMotorLimits = limits;
    }

    public MotorPositionLimits getLimits() {
        return mMotorLimits;
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
    public void resetEncoder() throws NullConfigException {
        mEncoder.orElseThrow(NullConfigException::nullEncoder).reset();
    }

    @Override
    public NeutralOperation getNeutralOperation() throws NullNeutralOperationException {
        throw new NullNeutralOperationException(
                "SOTA_FalconFX: Ctre didn't implement getting the Neutral Operation from the motor. If you really need to get the Neutral Operation keep track of it in a seperate variable or get build to use a different motor. If you actually get this error email me howardwalz@gmail.com, good luck! :)");
    }
}
