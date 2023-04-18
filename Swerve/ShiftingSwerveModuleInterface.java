package lib.Swerve;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj2.command.Subsystem;

public interface ShiftingSwerveModuleInterface extends Subsystem{

    void drive(ShiftingSwerveModuleState state);

    default double getSpeed(){
        return nativeToMetersPerSecond(getNativeVelocity(), getCurrentGear());
    };
    
    double getNativeVelocity();

    double getAngle();

    Rotation2d getRotation2d();

    int getCurrentGear();

    default double nativeToMetersPerSecond(double encoderVelocity, int currentGear){
        return encoderVelocity * 10 * getMetersPerCount(currentGear); 
    }

    double nativeToRadians(double encoderCounts);
    double radiansToNative(double radians);

    double metersPerSecondToNative(double metersPerSecond, double gearRatio);
    double getMetersPerCount(int gearRatio);

    SwerveModulePosition getMeasuredPosition();

    double getDistance();
    void updateDistance();
}
