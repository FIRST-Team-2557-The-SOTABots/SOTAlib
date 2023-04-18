package lib.Config;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import lib.Math.Conversions;

public class ShiftingSwerveDriveConfig {
    private double wheelBase;
    private double trackWidth;
    
    private double maxWheelSpeed;
    private double maxAngularVelocity;
    
    public double getWheelBase() {
        return wheelBase * Conversions.METERS_PER_INCH;
    }
    
    public double getTrackWidth() {
        return trackWidth * Conversions.METERS_PER_INCH;
    }

    /**
     * Order Front right, Front left, Back Left, Back Right
     */
    public Translation2d[] generateModuleTranslations() {
        Translation2d[] moduleTranslations = {
            
            
            new Translation2d(-getWheelBase() / 2, getTrackWidth() / 2),
            new Translation2d(getWheelBase() / 2, getTrackWidth() / 2),
            new Translation2d(getWheelBase() / 2, -getTrackWidth() / 2),
            new Translation2d(-getWheelBase() / 2, -getTrackWidth() / 2)
        };
        return moduleTranslations;
    }

    /*
     * Generates it's own module translations so that nothing else can disturb them
     */
    public SwerveDriveKinematics generateKinematics() {
        return new SwerveDriveKinematics(generateModuleTranslations());
    }

    public SwerveDriveOdometry generateOdometry(SwerveDriveKinematics kinematics, Rotation2d gyroAngle, SwerveModulePosition[] modulePositions) {
        return new SwerveDriveOdometry(kinematics, gyroAngle, modulePositions);
    }

    public double getMaxWheelSpeed() {
        return maxWheelSpeed;
    }

    public double getMaxAngularVelocity() {
        return maxAngularVelocity;
    }

}
