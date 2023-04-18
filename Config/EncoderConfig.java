// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package lib.Config;

/** Add your docs here. */
public class EncoderConfig {
    private int port;
    private double encoderOffset;
    private double countsPerRevolution;
    private String portType;
    private String encoderType;

    public int getPort() {
        return port;
    }

    public double getCountsPerRevolution() {
        return countsPerRevolution;
    }

    public double getEncoderOffset() {
        return encoderOffset;
    }
    
    public String getPortType(){
        return portType;
    }
    
    public String getEncoderType() {
        return encoderType;
    }
    
}
