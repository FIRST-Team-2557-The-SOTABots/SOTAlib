// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package SOTAlib.Gyro;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;

public class NavX implements SOTA_Gyro {
  private final AHRS mNavX;

/**
 * Creates a new NavX
 * @param navX AHRS navX to be created
 */
  public NavX(AHRS navX) {
    this.mNavX = navX;
  }

/**
 * Returns the angle of the navX in radians from a Rotation2d
 * @return Radian angle of the navX
 */
  public double getAngle() {
    return getRotation2d().getRadians();
  }

  /**
   * Returns the current Rotation2d of the navX
   * @return Rotation2d of the navX
   */
  public Rotation2d getRotation2d() {
    return mNavX.getRotation2d();
  }

  /**
   * Resets the Gyro and sets it to an angle in radians
   * @param radians angle to set the gyro to after resetting
   */
  public void setGyro(double radians) {
    mNavX.reset();
    mNavX.setAngleAdjustment(radians);
  }

  /**
   * Resets the gyro and sets it to an angle as specified by a Rotation2d object
   * @param rotation2d Sets the gyro to the radian angle of this Rotation2d after resetting the gyro
   */
  public void setGyro(Rotation2d rotation2d) {
    mNavX.reset();
    setGyro(rotation2d.getRadians());
  }

  /**
   * Resets the angle of the gyro and sets it to 0.0
   * Similar to setGyro, but always sets it to 0.0
   */
  public void resetAngle() {
    mNavX.reset();
    mNavX.setAngleAdjustment(0.0);
  }

  /**
   * Returns the pitch of the navX in radians
   * @return pitch of gyro in radians
   */
  public double getPitch() {
    Rotation2d output = Rotation2d.fromDegrees(mNavX.getPitch());
    return output.getRadians();
  }

  /**
   * Returns the roll of the navX in radians
   * @return roll of gyro in radians
   */
  public double getRoll() {
    Rotation2d output = Rotation2d.fromDegrees(mNavX.getRoll());
    return output.getRadians();
  }

  /**
   * Returns the yaw of the navX in radians
   * @return yaw of gyro in radians
   */
  @Override
  public double getYaw() {
    Rotation2d output = Rotation2d.fromDegrees(mNavX.getYaw());
    return output.getRadians();
  }

}