package SOTAlib.Swerve;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
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
}
