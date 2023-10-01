package SOTAlib.Config;

import SOTAlib.Math.Conversions;

public class SwerveDriveConfig {
    private double wheelBase;
    private double trackWidth;
    private double wheelDiameter;
    private double gearRatio; // of speed motor
    private double maxSpeed; //in feet per second
    private double maxAcceleration;
    private double maxAngularVelocity;
    private double speedP;
    private double speedI;
    private double speedD;
    private double speedKs;
    private double speedKv;
    private boolean defaultFieldCentric;

    /**
     * 
     * @return wheel base in meters
     */
    public double getWheelBase() {
        return this.wheelBase * Conversions.METERS_PER_INCH;
    }

    public void setWheelBase(double wheelBase) {
        this.wheelBase = wheelBase;
    }

    /**
     * 
     * @return track width in meters
     */
    public double getTrackWidth() {
        return this.trackWidth * Conversions.METERS_PER_INCH;
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

    /**
     * 
     * @return max speed in Meters/second
     */
    public double getMaxSpeed() {
        return Conversions.feetPerSecToMetersPerSec(this.maxSpeed);
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
