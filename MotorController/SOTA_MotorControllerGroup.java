// package lib.MotorController;

// import java.util.Arrays;

// import lib.Encoder.SOTAEncoder;
// TODO: Make SOTA_MotorControllerGroup

// public class SOTA_MotorControllerGroup {
//   private final SOTA_MotorController[] mMotorControllers;
//   private boolean kIsInverted;

//   /**
//    * Create a new MotorControllerGroup with the provided MotorControllers.
//    *
//    * @param motorController The first MotorController to add
//    * @param motorControllers The MotorControllers to add
//    */
//   public SOTA_MotorControllerGroup(
//     SOTA_MotorController motorController, SOTA_MotorController... motorControllers) {
//     mMotorControllers = new SOTA_MotorController[motorControllers.length + 1];
//     mMotorControllers[0] = motorController;
//   }

//   public SOTA_MotorControllerGroup(SOTA_MotorController[] motorControllers) {
//     mMotorControllers = Arrays.copyOf(motorControllers, motorControllers.length);
//   }

//   public void set(double speed) {
//     for (SOTA_MotorController motorController : mMotorControllers) {
//       motorController.set(kIsInverted ? -speed : speed);
//     }
//   }

// @Override
// public double get() {
//     return 0; //TODO: finish delegate
// }

// @Override
// public void setInverted(boolean isInverted) {
//     kIsInverted = isInverted;
    
// }

// @Override
// public boolean getInverted() {
//     return kIsInverted;
// }

// @Override
// public void disable() {
//     for(SOTA_MotorController motor : mMotorControllers){
//         motor.disable();
//     }
    
// }

// @Override
// public void stopMotor() {
//     for(SOTA_MotorController motor : mMotorControllers){
//         motor.stopMotor();
//     }
// }

// @Override
// public double getEncoderVelocity() {
//     // TODO Auto-generated method stub
//     return 0;
// }

// @Override
// public SOTAEncoder getEncoder() {
//     // TODO Auto-generated method stub
//     return null;
// }

// @Override
// public double getMotorCurrent() {
//     // TODO Auto-generated method stub
//     return 0;
// }

// public void setCurrentLimit(int amps) {
//     // TODO Auto-generated method stub
// }

// @Override
// public double getMotorTemperature() {
//     // TODO Auto-generated method stub
//     return 0;
// }

// @Override
// public double getPose() {
//     // TODO Auto-generated method stub
//     return 0;
// }

// @Override
// public double getNativeEncoderPosition() {
//     // TODO Auto-generated method stub
//     return 0;
// }
// @Override
//     public MotorLimits getMotorLimits() {
//         return null;
//     }

// @Override
// public void setVoltage(double voltage) {
//     // TODO Auto-generated method stub
    
// }

// @Override
// public double getEncoderPosition() {
//     // TODO Auto-generated method stub
//     return 0;
// }

// @Override
// public double getNativeEncoderVelocity() {
//     // TODO Auto-generated method stub
//     return 0;
// }

// @Override
// public double getNativePosition() {
//     // TODO Auto-generated method stub
//     return 0;
// }

// @Override
// public SOTAEncoder getNativeEncoder() {
//     // TODO Auto-generated method stub
//     return null;
// }

// }
