// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package lib.Swerve;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

/** Add your docs here. */
public class ShiftingSwerveModuleState extends SwerveModuleState {
    public int gear;

    public ShiftingSwerveModuleState(int gear) {
        this(new SwerveModuleState(), gear);
    }

    public ShiftingSwerveModuleState(SwerveModuleState state, int gear) {
        super(state.speedMetersPerSecond, state.angle);    
        this.gear = gear;
    }

    public static ShiftingSwerveModuleState[] toShiftingSwerveModuleState(SwerveModuleState[] state, int gear) {
        ShiftingSwerveModuleState[] shiftingStates = new ShiftingSwerveModuleState[state.length];
        for (int i = 0; i < state.length; i++) {
            shiftingStates[i] = new ShiftingSwerveModuleState(state[i], gear);
        }
        return shiftingStates;
    }
    
    public static ShiftingSwerveModuleState optimize(ShiftingSwerveModuleState state, Rotation2d rotation){
        return new ShiftingSwerveModuleState(SwerveModuleState.optimize(state, rotation), state.gear);
    }
    
}
