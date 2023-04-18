package lib.Encoder;

public interface SOTA_Encoder {
    public double get();
    public double getAbsolutePosition();
    public void reset();
    public void setPositionOffset(double offset);
    public double getPositionOffset();
    public void close();
    public double getVelocity();
    public double getCountsPerRevolution();
}
