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
     * GetPosition but constrained to an output between 0 and 1
     * @return encoder position constrained to between 0 and 1, rotations
     */
    public default double getConstrainedPositon() {
        Double input = getPosition();
        return Math.abs(input) - Math.floor(Math.abs(input));
    };

    /**
     * Gets the position of the encoder without offset
     * 
     * @return position of encoder in rotations
     */
    public double getPositionNoOffset();

    /**
     * Sets the offset of the encoder
     * 
     * @param offset encoder reading at desired zero point
     */
    public void setPositionOffset(double offset);

    /**
     * Gets the offset of the encoder
     * 
     * @return offset of the encoder
     */
    public double getPositionOffset();

    /**
     * Gets port of encoder
     * @return port Number
     */
    public int getPort();
}
