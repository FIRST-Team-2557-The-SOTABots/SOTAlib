package SOTAlib.MotorController;

import SOTAlib.Encoder.Absolute.SOTA_AbsoulteEncoder;
import SOTAlib.Encoder.Relative.SOTA_RelativeEncoder;

public interface SOTA_CompositeMotor {

    /**
     * 
     * @return SOTA_MotorController Object
     */
    public SOTA_MotorController getMotor();

    /**
     * 
     * @return SOTA_AbsoluteEncoder Object
     */
    public SOTA_AbsoulteEncoder getAbsEncoder();

    /**
     * 
     * @return SOTA_RelativeEncoder Object
     */
    public SOTA_RelativeEncoder getRelEncoder();
}