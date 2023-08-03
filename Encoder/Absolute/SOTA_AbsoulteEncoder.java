// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package SOTAlib.Encoder.Absolute;

/** Add your docs here. */
public interface SOTA_AbsoulteEncoder {

    /**
     * Gets the position of the encoder in rotations
     * 
     * @return position of encoder in rotations
     */
    public double getPosition();

    /**
     * Gets the position of the encoder without offset
     * 
     * @return position of encoder in rotations
     */
    public double getPositionNoOffset();

    /**
     * Gets the velocity of the encoder in RPM
     * 
     * @return velocity in RPM
     */
    public double getVelocity();

    /**
     * Sets the offset of the encoder
     * 
     * @param offset encoder reading at desired zero point
     */
    public double setPositionOffset(double offset);

    /**
     * Gets the offset of the encoder
     * 
     * @return offset of the encoder
     */
    public double getPositionOffset();
}
