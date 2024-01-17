// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package SOTAlib.Gyro;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Rotation2d;

public class NavX implements SOTA_Gyro {
  private final AHRS mNavX;

  /** Creates a new NavX. */
  public NavX(AHRS navX) {
    this.mNavX = navX;
  }

  public double getAngle() {
    return getRotation2d().getRadians();
  }

  public Rotation2d getRotation2d() {
    return mNavX.getRotation2d();
  }

  public void setGyro(double radians) {
    mNavX.reset();
    mNavX.setAngleAdjustment(radians);
  }

  public void setGyro(Rotation2d rotation2d) {
    mNavX.reset();
    setGyro(rotation2d.getRadians());
  }

  public void resetAngle() {
    mNavX.reset();
    mNavX.setAngleAdjustment(0.0);
  }

  public double getPitch() {
    Rotation2d output = Rotation2d.fromDegrees(mNavX.getPitch());
    return output.getRadians();
  }

  public double getRoll() {
    Rotation2d output = Rotation2d.fromDegrees(mNavX.getRoll());
    return output.getRadians();
  }

  @Override
  public double getYaw() {
    Rotation2d output = Rotation2d.fromDegrees(mNavX.getYaw());
    return output.getRadians();
  }

}