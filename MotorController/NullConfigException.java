package SOTAlib.MotorController;

public class NullConfigException extends Exception {
   public NullConfigException(String errormsg) {
    super(errormsg);
   }
   
   public static NullConfigException nullEncoder(){
      return new NullConfigException("No SOTA_Encoder atttached to this motor");
   }
}
