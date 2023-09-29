package SOTAlib.Swerve;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public interface SwerveModuleInterface {
    /**
     * Tells the module how it should be positioned
     * @param state swerve module state (from drive subsystem)
     */
    public void setModule(SwerveModuleState state); 

    /**
     * 
     * @return module position in a rotation2d object
     */
    public Rotation2d getRotation2d();

    /**
     * 
     * @return module position in radians
     */
    public double getRadians();

    /**
     * 
     * @return Maximum speed of the module in meters per second
     */
    public double getMaxSpeed();

    /**
     * 
     * @return the distance the module has travelled (meters)
     */
    public Double getDistance();

    /**
     * 
     * @return the position of the module for odometry
     */
    public SwerveModulePosition getPosition();

    /**
     * updates the internal distance travelled variable
     */
    public void updateDistance();


}
