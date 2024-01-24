package SOTAlib.Math;

public class Conversions {
    public static final double METERS_PER_INCH = 0.0254;

    /**
     * Converts from a meters per second value to a feet per second value
     * 
     * @param mps speed in meters per second
     * @return speed in feet per second
     */
    public static double metersPerSecToFeetPerSec(double mps) {
        return mps * 3.281;
    }

    /**
     * Converts from a feet per second value to a meters per second value
     * @param fps speed in feet per second
     * @return speed in meters per second
     */
    public static double feetPerSecToMetersPerSec(double fps) {
        return fps / 3.281;
    }

    /**
     * Converts from rotations to Radians
     * @param rots angle measurement in rotations
     * @return angle measurement in radians
     */
    public static double rotsToRads(double rots) {
        return rots * 2 * Math.PI;
    }

    /**
     * Converts from radians to rotations
     * @param rads angle measurement in radians
     * @return angle measurement in rotations
     */
    public static double radsToRots(double rads) {
        return rads / (2 * Math.PI);
    }
}
