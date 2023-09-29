// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package SOTAlib.Config;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;

/** Add your docs here. */
public class EncoderConfig {
    private int port; // for analog and DutyCycle/PWM encoders
    private int sourceA; // for Quadrature encoders only
    private int sourceB; // for Quadrature encoders only
    private Integer sourceI; // for Quadrature encoders only
    private EncodingType encodingType; // for Quadrature encoders only
    private boolean isInverted; //for Quadrature encoders only
    private Double encoderOffset; // for analog and DutyCycle/pwm encoders
    private int countsPerRevolution; // for Quadrature encoders only
    private String encoderType; // "QUAD", "DUTYCYCLE", "SPARKMAX", or "ANALOG"

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
