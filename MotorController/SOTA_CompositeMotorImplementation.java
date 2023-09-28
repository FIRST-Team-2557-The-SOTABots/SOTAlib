package SOTAlib.MotorController;

import java.util.Optional;
import java.util.function.Supplier;

import SOTAlib.Encoder.Absolute.SOTA_AbsoulteEncoder;
import SOTAlib.Encoder.Relative.SOTA_RelativeEncoder;

public class SOTA_CompositeMotorImplementation implements SOTA_CompositeMotor {

    private SOTA_MotorController mMotor;
    private Optional<SOTA_AbsoulteEncoder> mAbsEncoder;
    private Optional<SOTA_RelativeEncoder> mRelEncoder;
    private Supplier<NullConfigException> mNullExceptionSupplier;

    public SOTA_CompositeMotorImplementation(SOTA_MotorController motor, SOTA_RelativeEncoder relEncoder) {
        this(motor, null, relEncoder);
    }

    public SOTA_CompositeMotorImplementation(SOTA_MotorController motor, SOTA_AbsoulteEncoder absEncoder) {
        this(motor, absEncoder, null);
    }

    public SOTA_CompositeMotorImplementation(SOTA_MotorController motor, SOTA_AbsoulteEncoder absEncoder,
            SOTA_RelativeEncoder relEncoder) {
        this.mMotor = motor;
        this.mAbsEncoder = Optional.ofNullable(absEncoder);
        this.mRelEncoder = Optional.ofNullable(relEncoder);
    }

    @Override
    public SOTA_MotorController getMotor() {
        return mMotor;
    }

    @Override
    public SOTA_AbsoulteEncoder getAbsEncoder() throws NullConfigException {
        return mAbsEncoder.orElseThrow(mNullExceptionSupplier);
    }

    @Override
    public SOTA_RelativeEncoder getRelEncoder() throws NullConfigException {
       return mRelEncoder.orElseThrow(mNullExceptionSupplier); 
    }

}
