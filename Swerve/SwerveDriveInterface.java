package lib.Swerve;


import com.pathplanner.lib.PathPlannerTrajectory.PathPlannerState;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj2.command.Subsystem;

public interface SwerveDriveInterface extends Subsystem{
    
    default void drive(double fwd, double str, double rot){
        drive(fwd, str, rot, getCurrentAngle());
    }
    default void drive(double fwd, double str, double rot, Rotation2d currentAngle){
        drive(fwd, str, rot, currentAngle, new Translation2d());
    }
    default void drive(double fwd, double str, double rot, Translation2d pointOfRotation){
        drive(fwd, str, rot, getCurrentAngle(), pointOfRotation);
    }

    void drive(double fwd, double str, double rot, Rotation2d currentAngle, Translation2d pointOfRotation);

    void drive(ShiftingSwerveModuleState state);

    void drive(ChassisSpeeds speeds);


    void shift(int gear);

    double getMaxWheelSpeed();

    void updatePose(ShiftingSwerveModuleInterface[] modulePositions, Rotation2d angle);

    void updatePose(PathPlannerState state);

    void updatePose(Pose2d pose2d);

    SwerveModulePosition[] getModulePositions();

    Translation2d[] getModuleTranslations();

    void setFieldCentricActive();

    boolean getFieldCentricActive();

    Pose2d getPose();

    double getAngle();
    Rotation2d getCurrentAngle();

    double getPitch();

    double getRoll();
    
    void resetGyro();

    void setGyro();
}
