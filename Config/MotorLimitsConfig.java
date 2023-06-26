package SOTAlib.Config;


public class MotorLimitsConfig {
    private Double upperLimit;
    private Double lowerLimit;
    private boolean finalLimits;

    public Double getUpperLimit(){
        return upperLimit;
    }

    public Double getLowerLimit(){
        return lowerLimit;
    }

    public boolean getFinalLimits(){
        return finalLimits;
    }
}
