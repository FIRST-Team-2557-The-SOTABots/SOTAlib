// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package lib.Encoder;

import com.revrobotics.RelativeEncoder;

/** Add your docs here. */
public class SparkMaxIntegratedEncoder implements SOTA_Encoder {
    
    private final RelativeEncoder mEncoder;
    private double offset;

    public SparkMaxIntegratedEncoder(RelativeEncoder encoder) {
        this.mEncoder = encoder;
        this.offset = 0;
    }

    public SparkMaxIntegratedEncoder(RelativeEncoder encoder, double offset){
        this.mEncoder = encoder;
        this.offset = offset;
    }
    @Override
    public double get() {
        return mEncoder.getPosition() - offset;
    }

    public void setPosition(double newPosition) {
        mEncoder.setPosition(newPosition);    
    }

    public double getVelocity() {
        return mEncoder.getVelocity();
    }

    public double getCountsPerRevolution() {
        return mEncoder.getCountsPerRevolution();
    }

    public void reset() {
        mEncoder.setPosition(0.0);
    }

    

    @Override
    public double getAbsolutePosition() {
        return mEncoder.getPosition();
    }

    @Override
    public void setPositionOffset(double offset) {
        this.offset = offset;
        
    }

    @Override
    public double getPositionOffset() {
        return offset;
    }

    @Override
    public void close() {
                
    }
}
