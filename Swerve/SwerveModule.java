// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package SOTAlib.Swerve;

import SOTAlib.Config.SwerveDriveConfig;
import SOTAlib.Config.SwerveModuleConfig;
import SOTAlib.Encoder.Absolute.SOTA_AbsoulteEncoder;
import SOTAlib.MotorController.SOTA_MotorController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveModule extends SubsystemBase implements SwerveModuleInterface {

  private String moduleName;

  private SOTA_MotorController speedMotor;
  private ProfiledPIDController speedPID;
  private SimpleMotorFeedforward speedFF;

  private SOTA_MotorController angleMotor;
  private SOTA_AbsoulteEncoder angleEncoder;
  private ProfiledPIDController anglePID;
  private SimpleMotorFeedforward angleFF;

  private double kWheelCircumference;
  private double kGearRatio;
  private double kMaxSpeed;
  private ShuffleboardTab sTab;
  private GenericEntry encoderPosEntry;

  /** Creates a new SwerveModule. */
  public SwerveModule(SOTA_MotorController speedMotor, SOTA_MotorController angleMotor,
      SOTA_AbsoulteEncoder angleEncoder, SwerveModuleConfig modConfig, SwerveDriveConfig drvConfig) {

    this.sTab = Shuffleboard.getTab("Swerve");
    this.encoderPosEntry = sTab.add("Encoder Output: " + moduleName, 0.0).getEntry();
    this.moduleName = modConfig.getName();
    this.kWheelCircumference = drvConfig.getWheelDiameter() * Math.PI;
    this.kGearRatio = drvConfig.getGearRatio();
    this.kMaxSpeed = drvConfig.getMaxSpeed();

    this.speedMotor = speedMotor;
    this.speedPID = new ProfiledPIDController(drvConfig.getSpeedP(), drvConfig.getSpeedI(), drvConfig.getSpeedD(),
        new Constraints(drvConfig.getMaxSpeed(), drvConfig.getMaxAcceleration()));
    this.speedFF = new SimpleMotorFeedforward(drvConfig.getSpeedKs(), drvConfig.getSpeedKv());

    this.angleMotor = angleMotor;
    this.angleEncoder = angleEncoder;
    this.anglePID = new ProfiledPIDController(modConfig.getAngleP(), modConfig.getAngleI(), modConfig.getAngleD(),
        new Constraints(modConfig.getAngleMaxVelocity(), modConfig.getAngleMaxAcceleration()));
    anglePID.enableContinuousInput(0, 1);
    this.angleFF = new SimpleMotorFeedforward(modConfig.getAngleKs(), modConfig.getAngleKv());
  }

  @Override
  public void setModule(SwerveModuleState state) {
    state = SwerveModuleState.optimize(state, getRotation2d());

    double angleRotations = Units.radiansToRotations(state.angle.getRadians());
    double anglePIDOutput = anglePID.calculate(angleMotor.getEncoderPosition(), angleRotations);
    double angleFFOutput = angleFF.calculate(anglePID.getSetpoint().velocity);

    double speedRPM = metersPerSecondToRPM(state.speedMetersPerSecond);
    double speedPIDOutput = speedPID.calculate(speedMotor.getEncoderVelocity(), speedRPM);
    double speedFFOutput = speedFF.calculate(speedRPM);

    angleMotor.setVoltage(state.speedMetersPerSecond == 0 ? 0 : anglePIDOutput + angleFFOutput);
    speedMotor.setVoltage(speedPIDOutput + speedFFOutput);
  }

  private double metersPerSecondToRPM(double speedMetersPerSecond) { //TODO: move to SOTAUnits class?
    return (60 / ((2 * Math.PI) * (kWheelCircumference / 2))) * speedMetersPerSecond;
  }

  @Override
  public Rotation2d getRotation2d() {
    return new Rotation2d(getRadians());
  }

  @Override
  public double getRadians() {
    return Units.rotationsToRadians(angleEncoder.getConstrainedPositon());
  }

  @Override
  public double getMaxSpeed() {
    return kMaxSpeed;
  }

  @Override
  public Double getDistance() {
    return speedMotor.getEncoderPosition()  * ((Units.inchesToMeters(kWheelCircumference)) / kGearRatio);
  }

  @Override
  public SwerveModulePosition getPosition() {
    return new SwerveModulePosition(getDistance(), getRotation2d());
  }

  private void updateSD() {
    encoderPosEntry.setDouble(angleEncoder.getConstrainedPositon());
  }

  @Override
  public void periodic() {
      updateSD();
  }
  
}
