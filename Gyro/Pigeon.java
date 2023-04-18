package lib.Gyro;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.math.geometry.Rotation2d;

public class Pigeon implements SOTA_Gyro {

    private PigeonIMU mPidgeon;

    public Pigeon(PigeonIMU pigeon) {
        this.mPidgeon = pigeon;
    }

    public double getAngle() {
        return getYaw();
    }
    
    public Rotation2d getRotation2d() {
        return new Rotation2d(getYaw());
    }

    public void setAngle(double radians) {
        mPidgeon.setYaw(Math.toDegrees(radians));
    }

    public void setAngle(Rotation2d rotation2d) {
        setAngle(rotation2d.getRadians());    
    }

    public void resetAngle() {
        mPidgeon.setYaw(0.0);
    }

    public double getPitch() {
        return mPidgeon.getPitch();
    }

    public double getRoll() {
        return mPidgeon.getRoll();
    }

    public double getYaw() { 
        return mPidgeon.getYaw();
    }
    
}
