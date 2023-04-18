package lib.Config;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import lib.Math.Conversions;


import java.util.HashMap;
import java.util.Map;

public class ShiftingSwerveModuleConfig {

    private String modulePosition;

    private double[] gearRatios;

    private double wheelDiameter;  
    
    private double[] angles;
    private double[] offsets;

    private double speedKP;
    private double speedKI;
    private double speedKD;
    private double speedMaxAccel;
    private double speedMaxVel;

    private double maxWheelSpeed;

    private double speedKS;
    private double speedKV;

    private double angleKP;
    private double angleKI;
    private double angleKD;
    private double angleMaxAccel;

    private double angleKS;
    private double angleKV;

    private double anglePIDTolerance;
    private double speedPIDTolerance;

    public String getModulePosition() {
        return modulePosition;
    }

    public double[] getGearRatios(){
        return gearRatios;
    }

    /**
     * This needs to exist so that wheelDiameter is assigned a value with objectMapper
     * for now this is not used and would prefer to use getWheelCircumference
     */
    public double getwheelDiameter() {
        return wheelDiameter * Conversions.METERS_PER_INCH;
    }

    public double getWheelCircumference() {
        return getwheelDiameter() * Math.PI;
    }

    public double getSpeedKP() {
        return speedKP;
    }

    public double getSpeedKI() {
        return speedKI;
    }

    public double getSpeedKD() {
        return speedKD;
    }

    public double getSpeedMaxAccel() {
        return speedMaxAccel;
    }

    public double getSpeedMaxVel() {
        return speedMaxVel;
    }

    public double getSpeedKS() {
        return speedKS;
    }

    public double getSpeedKV() {
        return speedKV;
    }

    public double getMaxWheelSpeed() {
        return maxWheelSpeed;
    }

    public double[] getAngles() {
        return angles;
    }

    public double[] getOffsets() {
        return offsets;
    }

    public Map<Double, Double>  getAnglesToOffset() {
        Map<Double, Double> angleToOffset = new HashMap<Double, Double>();
        for (int i = 0; i < angles.length; i++) {
            angleToOffset.put(angles[i], offsets[i]);
        }
        // return new InterpolatingSwerveOffsetTreeMap(angleToOffset, ); 
        return angleToOffset;
    }

    public double getAngleKP() {
        return angleKP;
    }

    public double getAngleKI() {
        return angleKI;
    }

    public double getAngleKD() {
        return angleKD;
    }

    public double getAngleMaxAccel() {
        return angleMaxAccel;
    }

    public double getAngleMaxVel(double encoderCountsPerRevolution) {
        return angleMaxAccel * Math.sqrt((encoderCountsPerRevolution / 4) / angleMaxAccel);
    }

    public double getAngleKS() {
        return angleKS;
    }

    public double getAngleKV() {
        return angleKV;
    }

    public double getAnglePIDTolerance() {
        return anglePIDTolerance;
    }

    public double getSpeedPIDTolerance() {
        return speedPIDTolerance;
    }

    public SimpleMotorFeedforward angleFF(){
        return new SimpleMotorFeedforward(angleKS, angleKV);
    }

    public SimpleMotorFeedforward speedFF(){
        return new SimpleMotorFeedforward(speedKS, speedKV);
    }

    public ProfiledPIDController generateAnglePID(double countsPerRevolution) {
        TrapezoidProfile.Constraints constraints = 
            new TrapezoidProfile.Constraints(
                getAngleMaxVel(countsPerRevolution),
                getAngleMaxAccel()
        );
        ProfiledPIDController pid = 
            new ProfiledPIDController(
                getAngleKP(), 
                getAngleKI(),
                getAngleKD(), 
                constraints
        );
        pid.setTolerance(getAnglePIDTolerance());
        pid.enableContinuousInput(0, countsPerRevolution);
        return pid;
    }
    
    public PIDController generateSpeedPID() {
        
        PIDController pid =
            new PIDController(
                getSpeedKP(),
                getSpeedKI(), 
                getSpeedKD()                
        );
        return pid;
    }

    // public ProfiledPIDController anglePID(){
    //     ProfiledPIDController pid = new ProfiledPIDController(angleKP, angleKI, angleKD, 
    //     new TrapezoidProfile.Constraints(getAngleMaxVel(), angleMaxAccel));
    //     pid.setTolerance(anglePIDTolerance);
    //     pid.enableContinuousInput(0, angleEncoderCPR);
    //     return pid;
    // }
    // public ProfiledPIDController speedPID(){
    //     ProfiledPIDController pid = new ProfiledPIDController(speedKP, speedKI, speedKD,
    //     new TrapezoidProfile.Constraints(speedMaxVel, speedMaxAccel));
    //     pid.setTolerance(speedPIDTolerance);
    //     return pid;
    // }

    
}
