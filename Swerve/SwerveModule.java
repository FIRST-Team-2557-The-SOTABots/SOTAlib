// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package SOTAlib.Swerve;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveModule extends SubsystemBase implements SwerveModuleInterface{
  /** Creates a new SwerveModule. */
  public SwerveModule() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void setModule(SwerveModuleState state) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Rotation2d getRotation2d() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double getRadians() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double getMaxSpeed() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public Double getDistance() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SwerveModulePosition getPosition() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void updateDistance() {
    // TODO Auto-generated method stub
    
  }
}
