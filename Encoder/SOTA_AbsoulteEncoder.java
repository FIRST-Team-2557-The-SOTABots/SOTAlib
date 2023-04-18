// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package lib.Encoder;

/** Add your docs here. */
public interface SOTA_AbsoulteEncoder extends SOTA_Encoder {
    public double getPositionNoOffset();
    public double getPositionOffset();
    public double getOffset();
}
