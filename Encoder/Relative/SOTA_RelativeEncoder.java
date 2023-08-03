package SOTAlib.Encoder.Relative;

public interface SOTA_RelativeEncoder {

    /**
     * Gets the position of the encoder in rotations
     * 
     * @return position of encoder in rotations
     */
    public double getPosition();

    /**
     * Gets the velocity of the encoder in RPM
     * 
     * @return velocity in RPM
     */
    public double getVelocity();

    /**
     * Resets the encoder to read 0
     */
    public void reset();

    /**
     * Gets the amount of encoder ticks per revolution of the encoder. Probably not
     * useful anymore
     * 
     * @return ticks per revolution
     */
    public double getCountsPerRevolution();
}
