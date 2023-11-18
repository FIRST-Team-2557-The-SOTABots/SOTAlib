package SOTAlib.Config;


public class MotorControllerConfig  {
    private int port; //required
    private boolean isInverted; //required
    private String motorModel; //Motor Cotroller Model, SparkMax, Talon, Falcon
    private String motorType; //needed for sparkmax, Brushed or brushless
    private int countsPerRevolution; //needed for Talon becuase it can have many different encoders
    private String neutralOperation; //required brake or coast
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
