package SOTAlib.Config;

public class SwerveDriveConfig {
    private double wheelBase;
    private double trackWidth;
    private double wheelDiameter;
    private double gearRatio; // of speed motor
    private double maxSpeed;
    private double maxAcceleration;
    private double maxAngularVelocity;
    private double speedP;
    private double speedI;
    private double speedD;
    private double speedKs;
    private double speedKv;
    private boolean defaultFieldCentric;

    public double getWheelBase() {
        return this.wheelBase;
    }

    public void setWheelBase(double wheelBase) {
        this.wheelBase = wheelBase;
    }

    public double getTrackWidth() {
        return this.trackWidth;
    }

    public void setTrackWidth(double trackWidth) {
        this.trackWidth = trackWidth;
    }

    public double getWheelDiameter() {
        return this.wheelDiameter;
    }

    public void setWheelDiameter(double wheelDiameter) {
        this.wheelDiameter = wheelDiameter;
    }

    public double getGearRatio() {
        return this.gearRatio;
    }

    public void setGearRatio(double gearRatio) {
        this.gearRatio = gearRatio;
    }

    public double getMaxSpeed() {
        return this.maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getMaxAcceleration() {
        return this.maxAcceleration;
    }

    public void setMaxAcceleration(double maxAcceleration) {
        this.maxAcceleration = maxAcceleration;
    }

    public double getMaxAngularVelocity() {
        return this.maxAngularVelocity;
    }

    public void setMaxAngularVelocity(double maxAngularVelocity) {
        this.maxAngularVelocity = maxAngularVelocity;
    }

    public double getSpeedP() {
        return this.speedP;
    }

    public void setSpeedP(double speedP) {
        this.speedP = speedP;
    }

    public double getSpeedI() {
        return this.speedI;
    }

    public void setSpeedI(double speedI) {
        this.speedI = speedI;
    }

    public double getSpeedD() {
        return this.speedD;
    }

    public void setSpeedD(double speedD) {
        this.speedD = speedD;
    }

    public double getSpeedKs() {
        return this.speedKs;
    }

    public void setSpeedKs(double speedKs) {
        this.speedKs = speedKs;
    }

    public double getSpeedKv() {
        return this.speedKv;
    }

    public void setSpeedKv(double speedKv) {
        this.speedKv = speedKv;
    }

    public boolean getDefaultFieldCentric() {
        return this.defaultFieldCentric;
    }
    
}
