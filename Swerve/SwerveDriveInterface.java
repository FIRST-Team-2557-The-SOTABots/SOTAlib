package SOTAlib.Swerve;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Subsystem;

public interface SwerveDriveInterface extends Subsystem {

    /**
     * 
     * @param frwd forward Input (-1, 1)
     * @param strf strafe Input (-1, 1)
     * @param rttn rotational Input (-1, 1)
     */
    public default void drive(double frwd, double strf, double rttn) {
        drive(frwd, strf, rttn, new Translation2d());
    }

    /**
     * Drives from inputs with a specified center of rotation
     * 
     * @param frwd             forward Input (-1, 1)
     * @param strf             strafe Input (-1, 1)
     * @param rttn             rotational Input (-1, 1)
     * @param centerOfRotation center of rotation
     */
    public void drive(double frwd, double strf, double rttn, Translation2d centerOfRotation);

    /**
     * Drives from ChassisSpeeds object for autofollowing
     * 
     * @param speeds ChassisSpeeds object
     */
    public void drive(ChassisSpeeds speeds);

    /**
     * Desaturates and sets module states
     * 
     * @param states SwerveModule States
     */
    public void drive(SwerveModuleState[] states);

    /**
     * Enables/Disables Fieldcentric driving
     * @param state desired state
     */
    public void setFieldCentric(boolean state);

    /**
     * Returns the state of FieldCentric Driving
     * @return if fieldcentric is enabled
     */
    public boolean getFieldCentric();

    /**
     * Toggles field centric driving
     */
    public default void toggleFieldCentric() {
        if (getFieldCentric()) {
            setFieldCentric(false);
        } else {
            setFieldCentric(true);
        }
    }

    /**
     * Gets the max speed of the module for desaturation purposes
     * @return Maximum attainable speed in Meters Per Second.
     */
    public double getMaxSpeed();

    /**
     * 
     * @return Maximum angular velocity of the RobotChassis TODO: get units from Jon & Jonas
     */
    public double getMaxAngularVelocity();

    /**
     * Resets Gyro angle to 0
     */
    public void resetGyro();

    /**
     * Updates Pose of the robot
     */
    public void updatePose();

    /**
     * Sets the pose to a known value
     * @param pose Pose2d
     */
    public void resetPose(Pose2d pose);

    /**
     * Gets Pose2d of the robot
     * @return Pose2d of the robot
     */
    public Pose2d getPose2d();

    /**
     * 
     * @return SwerveDriveKinematics object of the drivetrain
     */
    public SwerveDriveKinematics getKinematics();
}
