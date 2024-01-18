package SOTAlib.Gyro;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.math.geometry.Rotation2d;

public class Pigeon implements SOTA_Gyro {

    private PigeonIMU mPidgeon;

    public Pigeon(PigeonIMU pigeon) {
        this.mPidgeon = pigeon;
    }

    public double getAngle() {
        return getRotation2d().getRadians();
    }

    public Rotation2d getRotation2d() {
        return new Rotation2d(getYaw());
    }

    public void setGyro(double radians) {
        mPidgeon.setYaw(Math.toDegrees(radians));
    }

    public void setGyro(Rotation2d rotation2d) {
        setGyro(rotation2d.getRadians());
    }

    public void resetAngle() {
        mPidgeon.setYaw(0.0);
    }

    public double getPitch() {
        Rotation2d output = Rotation2d.fromDegrees(mPidgeon.getPitch());
        return output.getRadians();
    }

    public double getRoll() {
        Rotation2d output = Rotation2d.fromDegrees(mPidgeon.getRoll());
        return output.getRadians();
    }

    public double getYaw() {
        Rotation2d output = Rotation2d.fromDegrees(mPidgeon.getYaw());
        return output.getRadians();
    }

}
