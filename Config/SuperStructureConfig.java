package lib.Config;

public class SuperStructureConfig {
    private double encoderAtZeroDegrees;
    private double encoderPerDegree;
    private int armBaseLength;
    private double encoderPerInch;
    private double height;
    private double bOffset;
    private double fOffset;
    private double fAbsoluteOffset;
    private double bAbsoluteOffset;
    private double maxExtension;
    private double roatationEncoderOffset;
    private double rotationalDelta;
    private double rotationalDeltaProportional;

    public double getRotationalDeltaProportional() {
        return rotationalDeltaProportional;
    }

    public double getRotationalDelta() {
        return rotationalDelta;
    }
    
    public double getEncoderAtZeroDegrees(){
        return encoderAtZeroDegrees;
    }
    public double getEncoderPerDegree(){
        return encoderPerDegree;
    }
    public int getArmBaseLength(){
        return armBaseLength;
    }
    public double getEncoderPerInch(){
        return encoderPerInch;
    }
    public double getHeight(){
        return height;
    }
    public double getbOffset(){
        return bOffset;
    }
    public double getfOffset(){
        return fOffset;
    }
    public double getfAbsoluteOffset(){
        return fAbsoluteOffset;
    }
    public double getbAbsoluteOffset(){
        return bAbsoluteOffset;
    }
    public double getMaxExtension(){
        return maxExtension;
    }

    public double getRoatationEncoderOffset(){
        return roatationEncoderOffset;
    }

}

