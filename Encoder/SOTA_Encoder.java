package SOTAlib.Encoder;

public interface SOTA_Encoder {

    /**
     * Gets the position of the encoder in rotations
     * @return position of encoder in rotations
     */
    public double getPosition();

    /**
     * Gets the position of the encoder without any offset
     * @return position in rotations without and offset
     */
    public double getPositionNoOffset();

    /**
     * Resets the encoder to read 0
     */
    public void reset();

    /**
     * Sets the offset for the encoder
     * @param offset the difference between the encoder position and 0 at your desired 0 point
     */
    public void setPositionOffset(double offset);

    /**
     * Gets the offset
     * @return offset of the encoder
     */
    public double getPositionOffset();
    
    /**
     * Gets the velocity of the encoder in RPM
     * @return velocity in RPM
     */
    public double getVelocity();

    /**
     * Gets the amount of encoder ticks per revolution of the encoder. Probably not usefull anymore
     * @return ticks per revolution
     */
    public double getCountsPerRevolution();
}
