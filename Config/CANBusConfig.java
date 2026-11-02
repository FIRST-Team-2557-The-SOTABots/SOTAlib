package SOTAlib.Config;

/**
 * Tracks CAN Bus routing targets.
 * Handles the distinction between the standard roboRIO CAN bus
 * and high-speed external loops like a CANivore.
 */
public class CANBusConfig {
    // Defaults to the built-in roboRIO CAN loop ("rio")
    private String busName = "rio"; 

    public CANBusConfig() {}

    public CANBusConfig(String busName) {
        this.busName = busName;
    }

    public String getBusName() {
        return this.busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public boolean isCustomBus() {
        return !this.busName.equalsIgnoreCase("rio");
    }
}
