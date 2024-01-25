// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package SOTAlib.Config;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;

/**
 * Config class for generating encoders
 */
public class EncoderConfig {

    /**
     * Port of the encoder
     * For Analog and DutyCycle/PWM encoders
     * REQUIRED
     */
    private int port;

    /**
     * Source A
     * for Quadrature encoders only
     */
    private int sourceA;

    /**
     * Source B
     * for Quadrature encoders only
     */
    private int sourceB;

    /**
     * Source I
     * for Quadrature encoders only
     */
    private Integer sourceI;

    /**
     * Encoding type
     * For Quadrature encoders only
     */
    private EncodingType encodingType;

    /**
     * Whether the encoder is inverted
     * true if encoder IS inverted
     * false if encoder IS NOT inverted
     * For all encoders
     */
    private boolean isInverted;

    /**
     * Offset of the encoder
     * For absolute encoders only
     * Must set from get raw position
     */
    private Double encoderOffset;

    /**
     * Counts per revoution of the encoder
     * For Quadrature encoders only
     * REQUIRED FOR QUADRATURE ENCODERS
     */
    private int countsPerRevolution;

    /**
     * Type/Name of encoder. Options between:
     * "QUAD",
     * "DUTYCYCLE",
     * "SPARKMAX",
     * "ANALOG"
     */
    private String encoderType;

    public int getPort() {
        return port;
    }

    public int getCountsPerRevolution() {
        return countsPerRevolution;
    }

    public Double getEncoderOffset() {
        return encoderOffset;
    }

    public String getEncoderType() {
        return encoderType;
    }

    public int getSourceA() {
        return sourceA;
    }

    public int getSourceB() {
        return sourceB;
    }

    public Integer getSourceI() {
        return sourceI;
    }

    public boolean getIsInverted() {
        return isInverted;
    }

    public EncodingType getEncodingType() {
        return encodingType;
    }
}
