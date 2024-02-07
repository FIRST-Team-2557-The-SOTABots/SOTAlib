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
     * 
     * @param fps speed in feet per second
     * @return speed in meters per second
     */
    public static double feetPerSecToMetersPerSec(double fps) {
        return fps / 3.281;
    }

    /**
     * Converts from rotations to Radians
     * 
     * @param rots angle measurement in rotations
     * @return angle measurement in radians
     */
    public static double rotsToRads(double rots) {
        return rots * 2 * Math.PI;
    }

    /**
     * Converts from radians to rotations
     * 
     * @param rads angle measurement in radians
     * @return angle measurement in rotations
     */
    public static double radsToRots(double rads) {
        return rads / (2 * Math.PI);
    }

    /**
     * Converts from a meters per second wheel value to RPM of the motor
     * 
     * @param MPS           meters per second
     * @param wheelDiameter wheel diameter in inches
     * @param gearRatio     gear ratio from output wheel to input motor
     * @return
     */
    public static double metersPerSecondToRPM(double MPS, double wheelDiameter, double gearRatio) {
        return (60 * MPS * gearRatio) / (0.0254 * wheelDiameter * Math.PI); // 0.0254 goes from inches to meters
    }

    public static double rpmToMetersPerSec(double rpm, double wheelDiameter, double gearRatio) {
        return (METERS_PER_INCH * Math.PI * rpm * wheelDiameter) / (60 * gearRatio);
    }

    public static double rotationsToMeters(double gearRatio, double wheelDiameter, double encoderPosition) {
        return (METERS_PER_INCH * Math.PI * encoderPosition * wheelDiameter) / (gearRatio);
    }

    /**
     * Converts from inches to meters.
     * @param inches measurement in inches
     * @return measurement in meters
     */
    public static double inchesToMeters(double inches) {
        return inches * METERS_PER_INCH;
    }
}
