package SOTAlib.MotorController;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public interface SOTA_MotorController extends MotorController {

    /**
     * @param neutralOperation desired neutral state
     */
    void setNeutralOperation(NeutralOperation neutralOperation);

    /**
     * @return encoder velocity in RPM
     */
    double getEncoderVelocity();

    /**
     * @return encoder position in rotations
     */
    double getEncoderPosition();

    /**
     * 
     * @return Motor Temp in C
     */
    double getMotorTemperature();

    /**
     * 
     * @return Motor current in Amps
     */
    double getMotorCurrent();

    /**
     * Sets the maximum amount of current a motor is allowed to draw
     * 
     * @param amps maximum amps
     */
    void setCurrentLimit(int amps);

    /**
     * Returns the MotorPositionLimits object that contains the upper and lower
     * bound of the
     * encoder range the motor is allowed to operate in
     * 
     * @return MotorLimits object
     * @throws NullConfigException
     */
    MotorPositionLimits getLimits() throws NullConfigException;

    /**
     * Sets the upper and lower bound of the encoder range the motor is allowed to
     * operate in
     * 
     * @param limits MotorLimits object with new limits
     */
    void setPositionLimits(MotorPositionLimits limits);

    /**
     * 
     * @return the lower bound of the encoder range the motor is allowed to operate
     *         in
     * @throws NullConfigException
     */
    double getLowerLimit() throws NullConfigException;

    /**
     * 
     * @return the upper bound of the encoder range the motor is allowed to operate
     *         in
     * @throws NullConfigException
     */
    double getUpperLimit() throws NullConfigException;

    /**
     * 
     * @return if the encoder is at the upper limit defined from the MotorLimits
     *         object
     * @throws NullConfigException
     */
    boolean atUpperLimit() throws NullConfigException;

    /**
     * 
     * @return if the encoder is at the lower limit defined from the MotorLimits
     *         object
     * @throws NullConfigException
     */
    boolean atLowerLimit() throws NullConfigException;

    /**
     * 
     * @return State of the interaction between the Encoder Position and the Range
     *         of the Limits, see EncoderLimitStates Enum for more information
     * @throws NullConfigException
     */
    MotorPositionLimitStates getLimitState() throws NullConfigException;

    /**
     * Resets the encoder to 0
     * 
     */
    void resetEncoder();

    /**
     * 
     * @return Current Neutral Operation from NeutralOperation Enum
     * @throws NullNeutralOperationException
     */
    NeutralOperation getNeutralOperation() throws NullNeutralOperationException;

    /**
     * Sets the internal encoder position
     * 
     * @param position desired encoder position in rotations
     */
    void setEncoderPosition(double position);

}
