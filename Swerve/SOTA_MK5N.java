package SOTAlib.Swerve;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import SOTAlib.Config.EncoderConfig;
import SOTAlib.Config.MotorControllerConfig;
import SOTAlib.Encoder.Absolute.SOTA_AbsoluteEncoder;
import SOTAlib.Factories.EncoderFactory;
import SOTAlib.Factories.MotorControllerFactory;
import SOTAlib.MotorController.SOTA_TalonFX;

public class SOTA_MK5N {
  private final SOTA_TalonFX m_drivingMotor;
  private final SOTA_TalonFX m_turningMotor;
  private final SOTA_AbsoluteEncoder m_turningEncoder;

  private double m_chassisAngularOffset = 0;
  private SwerveModuleState m_desiredState = new SwerveModuleState(0.0, new Rotation2d());

  /**
   * Constructs a SOTA_MK5N swerve module and configures the driving and turning motor,
   * absolute encoder, and internal loop references.
   * Specific to the SDS MK5n module built with Kraken X60, Kraken X44, and a CANcoder.
   */
  public SOTA_MK5N(MotorControllerConfig drivingConfig, MotorControllerConfig turningConfig, EncoderConfig encoderConfig, double chassisAngularOffset) throws Exception {
    
    // 1. Initialize components through your custom SOTAlib factories
    m_drivingMotor = (SOTA_TalonFX) MotorControllerFactory.generateMotorController(drivingConfig);
    m_turningMotor = (SOTA_TalonFX) MotorControllerFactory.generateMotorController(turningConfig);
    m_turningEncoder = EncoderFactory.generateAbsoluteEncoder(encoderConfig);

    m_chassisAngularOffset = chassisAngularOffset;
    
    // 2. Synchronize integrated rotor position to match the absolute CANcoder alignment point
    resetToAbsolute();
    
    m_desiredState.angle = new Rotation2d(m_turningEncoder.getPosition());
    m_drivingMotor.resetEncoder();
  }

  /**
   * Resets the integrated steering motor encoder to match the absolute position profile
   * from the physical alignment offset on the CANcoder.
   */
  public void resetToAbsolute() {
    double absoluteRotations = m_turningEncoder.getConstrainedPositon();
    m_turningMotor.setEncoderPosition(absoluteRotations);
  }

  /**
   * Returns the current state of the module.
   *
   * @return The current state of the module.
   */
  public SwerveModuleState getState() {
    // Apply chassis angular offset to the encoder position to get the position relative to the chassis.
    return new SwerveModuleState(
        m_drivingMotor.getEncoderVelocity(),
        new Rotation2d(m_turningEncoder.getConstrainedPositon() - m_chassisAngularOffset)
    );
  }

  /**
   * Returns the current position of the module.
   *
   * @return The current position of the module.
   */
  public SwerveModulePosition getPosition() {
    // Apply chassis angular offset to the encoder position to get the position relative to the chassis.
    return new SwerveModulePosition(
        m_drivingMotor.getEncoderPosition(),
        new Rotation2d(m_turningEncoder.getConstrainedPositon() - m_chassisAngularOffset)
    );
  }

  /**
   * Sets the desired state for the module.
   *
   * @param desiredState Desired state with speed and angle.
   */
  public void setDesiredState(SwerveModuleState desiredState) {
    // Apply chassis angular offset to the desired state.
    SwerveModuleState correctedDesiredState = new SwerveModuleState();
    correctedDesiredState.speedMetersPerSecond = desiredState.speedMetersPerSecond;
    correctedDesiredState.angle = desiredState.angle.plus(Rotation2d.fromRadians(m_chassisAngularOffset));

    // Optimize the reference state to avoid spinning further than 90 degrees.
    correctedDesiredState.optimize(new Rotation2d(m_turningEncoder.getConstrainedPositon()));

    // Command driving and turning TalonFXs towards their respective onboard setpoints.
    // Driving expects Velocity control (RPS), Turning expects Position control (Rotations)
    m_drivingMotor.setReference(correctedDesiredState.speedMetersPerSecond, "VELOCITY");
    m_turningMotor.setReference(correctedDesiredState.angle.getRotations(), "POSITION");

    m_desiredState = desiredState;
  }

  /** Zeroes the driving motor encoder metrics. */
  public void resetEncoders() {
    m_drivingMotor.resetEncoder();
  }
}
