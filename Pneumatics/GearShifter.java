package SOTAlib.Pneumatics;

public interface GearShifter {
    int getGear();
    void shift(int gear); //Low gear 0, High Gear 1
    default void shiftUp(){
        shift(getGear() + 1);
    };
    default void shiftDown(){
        shift(getGear() - 1);
    };
    double getCurrentGearRatio();
    double getRatioFromGear(int gear);
}
