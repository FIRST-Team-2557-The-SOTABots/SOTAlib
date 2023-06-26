package SOTAlib.Gyro;

import edu.wpi.first.math.geometry.Rotation2d;

public interface SOTA_Gyro {
    /**
     * @return returns the angle of the main axis in Radians
     */
    public double getAngle();

    /**
     * @return returns the Rotation2d of the main axis
     */
    public Rotation2d getRotation2d();

    /**
     * Sets the angle of the Gyro in Radians
     */
    public void setGyro(double radians);

    /**
     * sets the angle of the gyro to a Rotation2d
     * @param rotation2d
     */
    public void setGyro(Rotation2d rotation2d);

    /**
     * Resets the angle of the gyro
     */
    public void resetAngle();

    /**
     * @return Returns the Pitch of the gyro in Radians
     */
    public double getPitch();

    /**
     * @return Returns the Roll of the gyro in Radians
     */
    public double getRoll();

    /**
     * @return Returns the Yaw of the gyro in Radians
     */
    public double getYaw();
}
