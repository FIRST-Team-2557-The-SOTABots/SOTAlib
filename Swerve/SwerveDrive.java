// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package SOTAlib.Swerve;

import SOTAlib.Config.SwerveDriveConfig;
import SOTAlib.Gyro.SOTA_Gyro;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDrive extends SubsystemBase implements SwerveDriveInterface {
  private SwerveDriveKinematics mKinematics;
  private SwerveDriveOdometry mOdometry;
  private Pose2d currentPose2d;
  private SwerveModuleInterface[] modules;
  private SOTA_Gyro gyro;
  private boolean fieldCentric;
  private double kMaxSpeed;
  private double kMaxAngularVelocity;

  /** Creates a new SwerveDrive. */
  public SwerveDrive(SwerveModuleInterface[] modules, SOTA_Gyro gyro, SwerveDriveKinematics kinematics,
      SwerveDriveConfig config) {
    this.modules = modules;
    this.gyro = gyro;
    this.fieldCentric = config.getDefaultFieldCentric();
    this.mKinematics = kinematics;

    this.kMaxSpeed = config.getMaxSpeed();
    this.kMaxAngularVelocity = config.getMaxAngularVelocity();

    this.mOdometry = new SwerveDriveOdometry(mKinematics, new Rotation2d(this.gyro.getAngle()),
        new SwerveModulePosition[] {
            this.modules[0].getPosition(), this.modules[1].getPosition(), this.modules[2].getPosition(),
            this.modules[3].getPosition()
        });
  }

  @Override
  public void drive(double frwd, double strf, double rttn) {
    frwd = MathUtil.clamp(frwd, -1, 1) * kMaxSpeed;
    strf = MathUtil.clamp(strf, -1, 1) * kMaxSpeed;
    rttn = MathUtil.clamp(rttn, -1, 1) * kMaxAngularVelocity;
    drive(new ChassisSpeeds(frwd, strf, rttn));
  }

  @Override
  public void drive(ChassisSpeeds speeds) {
    if (getFieldCentric()) {
      speeds = ChassisSpeeds.fromFieldRelativeSpeeds(speeds, gyro.getRotation2d());
    }

    SwerveModuleState[] moduleStates = mKinematics.toSwerveModuleStates(speeds);
    drive(moduleStates);

  }

  @Override
  public void drive(SwerveModuleState[] states) {
    SwerveDriveKinematics.desaturateWheelSpeeds(states, kMaxSpeed);

    for (int i = 0; i < states.length; i++) {
      modules[i].setModule(states[i]);
    }

  }

  @Override
  public void setFieldCentric(boolean state) {
    this.fieldCentric = state;
  }

  @Override
  public boolean getFieldCentric() {
    return fieldCentric;
  }

  @Override
  public double getMaxSpeed() {
    return kMaxSpeed;
  }

  @Override
  public double getMaxAngularVelocity() {
    return kMaxAngularVelocity;
  }

  @Override
  public void resetGyro() {
    gyro.resetAngle();
  }

  @Override
  public void updatePose() {
    Rotation2d gyroAngle = new Rotation2d(gyro.getAngle());

    currentPose2d = mOdometry.update(gyroAngle, new SwerveModulePosition[] {
        modules[0].getPosition(), modules[1].getPosition(), modules[2].getPosition(), modules[3].getPosition()
    });

  }

  @Override
  public void resetPose(Pose2d pose) {
    mOdometry.resetPosition(new Rotation2d(gyro.getAngle()), new SwerveModulePosition[] {
      modules[0].getPosition(), modules[1].getPosition(), modules[2].getPosition(), modules[3].getPosition()
  }, pose);

  }

  @Override
  public Pose2d getPose2d() {
    return mOdometry.getPoseMeters();
  }

  @Override
  public SwerveDriveKinematics getKinematics() {
    return this.mKinematics;
  }
}
