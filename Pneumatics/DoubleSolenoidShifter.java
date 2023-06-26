package SOTAlib.Pneumatics;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import SOTAlib.Config.DoubleSolenoidConfig;

public class DoubleSolenoidShifter implements GearShifter{
    
    private DoubleSolenoid shifter;
    private int[] gearRatios;

    private Value kHiGearValue;
    private Value kLoGearValue;

    public DoubleSolenoidShifter(DoubleSolenoid solenoid, DoubleSolenoidConfig config) {
        this.shifter = solenoid; gearRatios = config.getGearRatios(); this.kHiGearValue = Value.kForward; 
        this.kLoGearValue = Value.kReverse;
    }

    public int getGear() {
        if(shifter == null) throw new RuntimeException("Null swerve module");
        return shifter.get() == kHiGearValue ? 1 : 0;
    }

    public void shift(int gear) {
        MathUtil.clamp(gear, 0, 1);
        
        shifter.set(gear == 0 ? kLoGearValue : kHiGearValue); 
    }

    public double getCurrentGearRatio() {
        return gearRatios[getGear()];
    }

    public double getRatioFromGear(int gear) {
        return gearRatios[gear];
    }
    
}
