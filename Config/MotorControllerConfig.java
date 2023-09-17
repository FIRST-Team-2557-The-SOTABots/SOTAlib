package SOTAlib.Config;


public class MotorControllerConfig  {
    private int port; //required
    private boolean isInverted;
    private String motorModel; //Motor Cotroller Model, SparkMax, Talon, Falcon, etc.
    private String motorType; //needed for sparkmax, Brushed or brushless
    private int countsPerRevolution; //needed for Talon becuase it can have many different encoders
    private String neutralOperation; //required brake or coast
    private EncoderConfig encoderConfig; //optional 
    private MotorLimitsConfig motorLimitsConfig; //optional
    private int currentLimit; //optional

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
    public EncoderConfig getEncoderConfig(){
        return encoderConfig;
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
