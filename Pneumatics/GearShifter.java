package lib.Pneumatics;

public interface GearShifter {
    int getGear();
    void shift(int gear);
    default void shiftUp(){
        shift(getGear() + 1);
    };
    default void shiftDown(){
        shift(getGear() - 1);
    };
    double getCurrentGearRatio();
    double getRatioFromGear(int gear);
}
