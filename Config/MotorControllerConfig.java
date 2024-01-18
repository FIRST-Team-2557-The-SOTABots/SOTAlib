package SOTAlib.Config;


/**
 * Config class for generating motor controllers.
 */
public class MotorControllerConfig  {
    /**
     * CAN ID of the motor
     * Required
     */
    private int port;

    /**
     * True if motor is inverted
     * Required
     */
    private boolean isInverted;

    /**
     * Model name of the motor. Possible values:
     * "SparkMax" for REV SparkMax
     * "SparkFlex" for REV SparkFlex
     * "Talon" for CTRE TalonSRX
     * "Falcon" for Talon FX motors (Falcon, Kracken)
     */
    private String motorModel;

    /**
     * Type of the motors. 
     * "BRUSHED" or "BRUSHLESS"
     * Requred for REV SparkMax & REV SparkFlex
     */
    private String motorType;

    /**
     * Integrated Encoder Counts per Revolution
     * Required for TalonSRX due to the varitety of encoders it supports. 
     */
    private int countsPerRevolution;

    /**
     * Neutral mode of the motor
     * Required: "BRAKE" or "COAST"
     */
    private String neutralOperation;

    /**
     * MotorLimitsConfig optional
     */
    private MotorLimitsConfig motorLimitsConfig;
    
    /**
     * Current limit of the motor optional
     */
    private int currentLimit;

    public boolean getIsInverted() {
        return isInverted;
    }

    public String getNeutralOperation() {
        return neutralOperation;
    }

    public String getMotorType() {
        return motorType;
    }

    public double getCountsPerRevolution() {
        return (double)countsPerRevolution;
    }

    public int getPort(){
        return this.port;
    }

    public MotorLimitsConfig getMotorLimitsConfig(){
        return motorLimitsConfig;
    }

    public int getCurrentLimit() {
        return currentLimit;
    }

    public String getMotorModel() {
        return motorModel;
    }
    
}
