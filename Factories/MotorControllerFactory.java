package SOTAlib.Factories;

import java.util.Optional;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkFlex;

import SOTAlib.Config.MotorControllerConfig;
import SOTAlib.MotorController.MotorPositionLimits;
import SOTAlib.MotorController.NullConfigException;
import SOTAlib.MotorController.SOTA_TalonFX;
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
            throw new NullConfigException("SOTA_TalonFX: config not created");

        // Extract the network bus target name dynamically from the config block
        String busName = config.getCanBusConfig().getBusName();
        
        // Instantiate the TalonFX on the targeted CANivore or RIO bus cleanly!
        TalonFX falcon = new TalonFX(config.getPort(), busName);

        // Standard Phoenix 6 setup blocks
        com.ctre.phoenix6.configs.MotorOutputConfigs outputConfigs = new com.ctre.phoenix6.configs.MotorOutputConfigs();
        outputConfigs.Inverted = config.getIsInverted() 
            ? com.ctre.phoenix6.signals.InvertedValue.Clockwise_Positive 
            : com.ctre.phoenix6.signals.InvertedValue.CounterClockwise_Positive;
        falcon.getConfigurator().apply(outputConfigs);

        Optional.ofNullable(config.getNeutralOperation()).ifPresent((neutralOp) -> {
            switch (neutralOp) {
                case "BRAKE":
                    falcon.setNeutralMode(NeutralModeValue.Brake);
                    break;
                case "COAST":
                    falcon.setNeutralMode(NeutralModeValue.Coast);
                    break;
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
            System.out.println("SOTA_TalonFX: INFO: no motor limits");
            return new SOTA_TalonFX(config, falcon);
        }

        return new SOTA_TalonFX(config, falcon, limits);
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

        SparkMax mMotor = new SparkMax(config.getPort(), motorType);
        SparkMaxConfig sparkConfig = new SparkMaxConfig();
        
        sparkConfig.inverted(config.getIsInverted());
        if (Optional.ofNullable(config.getNeutralOperation()).isPresent()) {
            if (config.getNeutralOperation().equals("BRAKE")) {
                sparkConfig.idleMode(IdleMode.kBrake);
            } else if (config.getNeutralOperation().equals("COAST")) {
                sparkConfig.idleMode(IdleMode.kCoast);
            }

            if (config.getCurrentLimit() != -1) {
                sparkConfig.smartCurrentLimit(config.getCurrentLimit());
            } else {
                System.out.println("SOTA_SparkMax: INFO: No current limit set");
            }
        }

        mMotor.configure(sparkConfig, SparkMax.ResetMode.kResetSafeParameters, SparkMax.PersistMode.kPersistParameters);

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

    private static SOTA_MotorController generateSparkFlexDelegate(MotorControllerConfig config)
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

        SparkFlex mMotor = new SparkFlex(config.getPort(), motorType);
        SparkFlexConfig sparkConfig = new SparkFlexConfig();
        
        sparkConfig.inverted(config.getIsInverted());
        if (Optional.ofNullable(config.getNeutralOperation()).isPresent()) {
            if (config.getNeutralOperation().equals("BRAKE")) {
                sparkConfig.idleMode(IdleMode.kBrake);
            } else if (config.getNeutralOperation().equals("COAST")) {
                sparkConfig.idleMode(IdleMode.kCoast);
            }

            if (config.getCurrentLimit() != -1) {
                sparkConfig.smartCurrentLimit(config.getCurrentLimit());
            } else {
                System.out.println("SOTA_SparkFlex: INFO: No current limit set");
            }
        }

        mMotor.configure(sparkConfig, SparkFlex.ResetMode.kResetSafeParameters, SparkFlex.PersistMode.kPersistParameters);

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
            switch (neutralOp) {
                case "BRAKE":
                    motor.setNeutralMode(NeutralMode.Brake);
                    break;
                case "COAST":
                    motor.setNeutralMode(NeutralMode.Coast);
                    break;
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
