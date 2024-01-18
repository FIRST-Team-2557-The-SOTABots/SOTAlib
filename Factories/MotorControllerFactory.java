package SOTAlib.Factories;

import java.util.Optional;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkFlex;
import com.revrobotics.CANSparkMax;

import SOTAlib.Config.MotorControllerConfig;
import SOTAlib.MotorController.MotorPositionLimits;
import SOTAlib.MotorController.NullConfigException;
import SOTAlib.MotorController.SOTA_FalconFX;
import SOTAlib.MotorController.SOTA_MotorController;
import SOTAlib.MotorController.SOTA_SparkFlex;
import SOTAlib.MotorController.SOTA_SparkMax;
import SOTAlib.MotorController.SOTA_TalonSRX;

public class MotorControllerFactory {

    public static SOTA_MotorController generateMotorController(MotorControllerConfig config)
            throws IllegalMotorModel, NullConfigException {
        switch (config.getMotorModel()) {
            case "Falcon":
                return generateFalconDelegate(config);
            case "Talon":
                return generateTalonSRXDelegate(config);
            case "SparkMax":
                return generateSparkMaxDelegate(config);
            case "SparkFlex":
                return generateSparkFlexDelegate(config);
        }
        throw new IllegalMotorModel(
                "Illegal Motor Model, check config has valid motor types 'Falcon', 'SparkMax', or 'Talon'");
    }

    private static SOTA_MotorController generateFalconDelegate(MotorControllerConfig config)
            throws NullConfigException {
        if (config == null)
            throw new NullConfigException("SOTA_FalconFX: config not created");

        TalonFX falcon = new TalonFX(config.getPort());

        Optional.ofNullable(config.getIsInverted()).ifPresent((invert) -> falcon.setInverted(invert));

        Optional.ofNullable(config.getNeutralOperation()).ifPresent((neutralOp) -> {
            switch (neutralOp) {
                case "BRAKE":
                    falcon.setNeutralMode(NeutralModeValue.Brake);
                case "COAST":
                    falcon.setNeutralMode(NeutralModeValue.Coast);
            }
        });

        if (config.getCurrentLimit() != 0) {
            CurrentLimitsConfigs configuration = new CurrentLimitsConfigs();
            configuration.SupplyCurrentLimit = config.getCurrentLimit();
            configuration.SupplyCurrentLimitEnable = true;
            falcon.getConfigurator().apply(configuration);
        }

        MotorPositionLimits limits;

        try {
            limits = new MotorPositionLimits(config.getMotorLimitsConfig().getLowerLimit(),
                    config.getMotorLimitsConfig().getUpperLimit(), config.getMotorLimitsConfig().getFinalLimits());

        } catch (NullPointerException e) {
            System.out.println("SOTA_FalconFX: INFO: no motor limits");
            return new SOTA_FalconFX(config, falcon);
        }

        return new SOTA_FalconFX(config, falcon, limits);
    }

    private static SOTA_MotorController generateSparkMaxDelegate(MotorControllerConfig config)
            throws NullConfigException {

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

        CANSparkMax mMotor;
        mMotor = new CANSparkMax(config.getPort(), motorType);
        mMotor.restoreFactoryDefaults();
        mMotor.setInverted(config.getIsInverted());
        if (Optional.ofNullable(config.getNeutralOperation()).isPresent()) {
            if (config.getNeutralOperation().equals("BRAKE")) {
                mMotor.setIdleMode(IdleMode.kBrake);
            } else if (config.getNeutralOperation().equals("COAST")) {
                mMotor.setIdleMode(IdleMode.kCoast);
            }

            if (config.getCurrentLimit() != -1) {
                mMotor.setSmartCurrentLimit(config.getCurrentLimit());
            } else {
                System.out.println("SOTA_SparkMax: INFO: No current limit set");
            }
        }

        MotorPositionLimits limits;
        try {
            limits = new MotorPositionLimits(config.getMotorLimitsConfig().getLowerLimit(),
                    config.getMotorLimitsConfig().getUpperLimit(), config.getMotorLimitsConfig().getFinalLimits());
        } catch (NullPointerException e) {
            System.out.println("SOTA_SparkMax: INFO: no motor limits");
            return new SOTA_SparkMax(config, mMotor);
        }

        return new SOTA_SparkMax(config, mMotor, limits);
    }



    private static SOTA_MotorController generateSparkFlexDelegate(MotorControllerConfig config) throws NullConfigException {
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

        CANSparkFlex mMotor;
        mMotor = new CANSparkFlex(config.getPort(), motorType);
        mMotor.restoreFactoryDefaults();
        mMotor.setInverted(config.getIsInverted());
        if (Optional.ofNullable(config.getNeutralOperation()).isPresent()) {
            if (config.getNeutralOperation().equals("BRAKE")) {
                mMotor.setIdleMode(IdleMode.kBrake);
            } else if (config.getNeutralOperation().equals("COAST")) {
                mMotor.setIdleMode(IdleMode.kCoast);
            }

            if (config.getCurrentLimit() != -1) {
                mMotor.setSmartCurrentLimit(config.getCurrentLimit());
            } else {
                System.out.println("SOTA_SparkFlex: INFO: No current limit set");
            }
        }

        MotorPositionLimits limits;
        try {
            limits = new MotorPositionLimits(config.getMotorLimitsConfig().getLowerLimit(),
                    config.getMotorLimitsConfig().getUpperLimit(), config.getMotorLimitsConfig().getFinalLimits());
        } catch (NullPointerException e) {
            System.out.println("SOTA_SparkFlex: INFO: no motor limits");
            return new SOTA_SparkFlex(config, mMotor);
        }

        return new SOTA_SparkFlex(config, mMotor, limits);
    }

    private static SOTA_TalonSRX generateTalonSRXDelegate(MotorControllerConfig config) {

        double nativeCPR = config.getCountsPerRevolution();

        WPI_TalonSRX motor;
        motor = new WPI_TalonSRX(config.getPort());
        motor.setInverted(config.getIsInverted());

        Optional.ofNullable(config.getNeutralOperation()).ifPresent((neutralOp) -> {
            switch (config.getNeutralOperation()) {
                case "BRAKE":
                    motor.setNeutralMode(NeutralMode.Brake);
                case "COAST":
                    motor.setNeutralMode(NeutralMode.Coast);
            }
        });

        if (config.getCurrentLimit() != 0) {
            motor.configPeakCurrentLimit(config.getCurrentLimit());
        } else {
            System.out.println("SOTA_TalonSRX: INFO: no current limit");
        }

        MotorPositionLimits limits;
        try {
            limits = new MotorPositionLimits(config.getMotorLimitsConfig().getLowerLimit(),
                    config.getMotorLimitsConfig().getUpperLimit(), config.getMotorLimitsConfig().getFinalLimits());
        } catch (NullPointerException e) {
            System.out.println("SOTA_FalconFX: INFO: no motor limits");
            return new SOTA_TalonSRX(motor, nativeCPR);
        }

        return new SOTA_TalonSRX(motor, limits, nativeCPR);
    }

}
