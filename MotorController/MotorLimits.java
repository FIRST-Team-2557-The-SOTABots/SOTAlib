package lib.MotorController;

public class MotorLimits {
    private Double upperLimit;
    private Double lowerLimit;
    private boolean finalLimits;

    public MotorLimits() {
        this(null, null);
    }

    public MotorLimits(Double lower, Double upper){
        this(lower, upper, true);
    }
    public MotorLimits(Double lower, Double upper, boolean finalLimits){
        this.lowerLimit = lower; this.upperLimit = upper; this.finalLimits = finalLimits;
    }

    public double getLowerLimit(){
        return lowerLimit == null ? -Double.MAX_VALUE : lowerLimit;
    }

    public double getUpperLimit(){
        return upperLimit == null ? Double.MAX_VALUE : upperLimit;
    }

    public void setLimits(double lower, double upper){
        if(finalLimits) throw new IllegalAccessError("limits are set as final");
        this.lowerLimit = lower; this.upperLimit = upper;
    }

    public void setLimits(MotorLimits limits){
        if(finalLimits) throw new IllegalAccessError("limits are set as final");
        this.lowerLimit = limits.getLowerLimit(); this.upperLimit = limits.getUpperLimit();
    }
    
    public void setUpperLimit(double limit){
        setLimits(this.lowerLimit, limit);
    }
    
    public void setLowerLimit(double limit){
        setLimits(limit, this.upperLimit);
    }
}
