// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package lib.Encoder;

import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;

/** Add your docs here. */
@Deprecated // This just doesnt work
public class FalconIntegratedEncoder implements SOTA_Encoder {
    private static final double kCountsPerRevolution = 2048;
    private TalonFXSensorCollection mEncoder;
    private double offset;

    public FalconIntegratedEncoder(TalonFXSensorCollection encoder) {
        mEncoder = encoder;
        offset = 0;
    }
    public FalconIntegratedEncoder(TalonFXSensorCollection encoder, double offset){
        this.offset = offset;
    }

    public double get() {
        return mEncoder.getIntegratedSensorPosition() - offset;
    }
    
    public void setPosition(double newPosition) {
        mEncoder.setIntegratedSensorPosition(newPosition, 0);
    }

    public double getVelocity() {
        return mEncoder.getIntegratedSensorVelocity();
    }

    public double getCountsPerRevolution() {
        return kCountsPerRevolution;
    }

    public void reset() {
        mEncoder.setIntegratedSensorPosition(0.0, 0);
    }

    @Override
    public double getAbsolutePosition() {
        return mEncoder.getIntegratedSensorAbsolutePosition();
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
