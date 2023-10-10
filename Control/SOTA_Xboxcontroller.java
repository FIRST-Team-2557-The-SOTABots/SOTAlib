package SOTAlib.Control;



import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class SOTA_Xboxcontroller extends CommandXboxController{

    public SOTA_Xboxcontroller(int port) {
        super(port);
    }
    
    public boolean getStart(){
        return start().getAsBoolean();
    }

    public boolean getBack() {
        return back().getAsBoolean();
    }

    public boolean getA(){
        return super.a().getAsBoolean();
    }
    
    public boolean getB(){
        return super.b().getAsBoolean();
    }

    public boolean getX(){
        return super.x().getAsBoolean();
    }

    public boolean getY(){
        return super.y().getAsBoolean();
    }

    public boolean getLeftBumper(){
        return leftBumper().getAsBoolean();
    } 

    public boolean getRightBumper(){
        return rightBumper().getAsBoolean();
    }

    public double getLeftX() {
        return Math.abs(super.getLeftX()) < 0.1 ? 0 : super.getLeftX();
    }
    public double getLeftY() {
        return Math.abs(super.getLeftY()) < 0.1 ? 0 : super.getLeftY();
    }

    public double getRightX() {
        return Math.abs(super.getRightX()) < 0.1 ? 0 : super.getRightX();
    }

    public double getRightY() {
        return Math.abs(super.getRightY()) < 0.1 ? 0 : super.getRightY();
    }

    public Trigger getLeftTrigger() {
        return super.axisGreaterThan(2, 0.05);
    }

    public Trigger getRightTrigger() {
        return super.axisGreaterThan(3, 0.05);
    }
}
