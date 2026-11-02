package SOTAlib.Config;

/**
 * Clean data tracking carrier for PID Closed-Loop Tuning parameters.
 */
public class PIDConfig {
    private double kP = 0.0;
    private double kI = 0.0;
    private double kD = 0.0;
    private double kF = 0.0; // Feed-forward component parameter

    public PIDConfig() {}

    public PIDConfig(double p, double i, double d) {
        this.kP = p;
        this.kI = i;
        this.kD = d;
    }

    public double getP() { return kP; }
    public void setP(double p) { this.kP = p; }

    public double getI() { return kI; }
    public void setI(double i) { this.kI = i; }

    public double getD() { return kD; }
    public void setD(double d) { this.kD = d; }

    public double getF() { return kF; }
    public void setF(double f) { this.kF = f; }
}
