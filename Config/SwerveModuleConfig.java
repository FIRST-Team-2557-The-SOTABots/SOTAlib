package SOTAlib.Config;

public class SwerveModuleConfig {
    private String name;
    private double moduleOffset;
    private MotorControllerConfig speedConfig;
    private CompositeMotorConfig angleConfig;
    private double angleP;
    private double angleI;
    private double angleD;
    private double angleKs;
    private double angleKv;
    private double angleMaxVelocity;
    private double angleMaxAcceleration;

    public double getModuleOffset() {
        return this.moduleOffset;
    }

    public void setModuleOffset(double moduleOffset) {
        this.moduleOffset = moduleOffset;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MotorControllerConfig getSpeedConfig() {
        return this.speedConfig;
    }

    public void setSpeedConfig(MotorControllerConfig speedConfig) {
        this.speedConfig = speedConfig;
    }

    public CompositeMotorConfig getAngleConfig() {
        return this.angleConfig;
    }

    public void setAngleConfig(CompositeMotorConfig angleConfig) {
        this.angleConfig = angleConfig;
    }

    public double getAngleP() {
        return this.angleP;
    }

    public void setAngleP(double angleP) {
        this.angleP = angleP;
    }

    public double getAngleI() {
        return this.angleI;
    }

    public void setAngleI(double angleI) {
        this.angleI = angleI;
    }

    public double getAngleD() {
        return this.angleD;
    }

    public void setAngleD(double angleD) {
        this.angleD = angleD;
    }

    public double getAngleKs() {
        return this.angleKs;
    }

    public void setAngleKs(double angleKs) {
        this.angleKs = angleKs;
    }

    public double getAngleKv() {
        return this.angleKv;
    }

    public void setAngleKv(double angleKv) {
        this.angleKv = angleKv;
    }

    public double getAngleMaxVelocity() {
        return this.angleMaxVelocity;
    }

    public void setAngleMaxVelocity(double angleMaxVelocity) {
        this.angleMaxVelocity = angleMaxVelocity;
    }

    public double getAngleMaxAcceleration() {
        return this.angleMaxAcceleration;
    }

    public void setAngleMaxAcceleration(double angleMaxAcceleration) {
        this.angleMaxAcceleration = angleMaxAcceleration;
    }

}