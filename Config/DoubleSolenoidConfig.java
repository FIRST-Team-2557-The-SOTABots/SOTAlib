package SOTAlib.Config;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class DoubleSolenoidConfig {
    private String hiGearValue;
    private String loGearValue;
    private int[] gearRatios;

    private Value convertStringToValue(String str) {
        switch(str) {
            case("FORWARD"): return Value.kForward;
            case("REVERSE"): return Value.kReverse;
            default: throw new IllegalArgumentException("Illegal value ");
        }
    }

    public Value getHiGearValue() {
        return convertStringToValue(hiGearValue);
    }

    public Value getLoGearValue() {
        return convertStringToValue(loGearValue);
    }

    public int[] getGearRatios(){
        return gearRatios;
    }
}
