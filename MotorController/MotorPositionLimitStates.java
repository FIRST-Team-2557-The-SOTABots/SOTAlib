package SOTAlib.MotorController;

/**
 * Defines the states of the interaction of the encoder position and the MotorPositionLimts
 * IN_RANGE: normal operation
 * TOO_HIGH: ENCODER TOO HIGH!!! WILL BREAK THINGS!!! MOVE BACK OR DISABLE!!!
 * TOO_LOW: ENCODER TOO LOW!!! WILL BREAK THINGS!!! MOVE UP OR DISABLE!!!
 * AT_UPPER_LIMIT: normal operation, cannot move up farther
 * AT_LOWER_LIMIT: normal operation, cannot move lower
 */
public enum MotorPositionLimitStates {
   IN_RANGE, //normal operation
   TOO_HIGH, //ENCODER TOO HIGH!!! WILL BREAK THINGS!!! MOVE BACK OR DISABLE!!!
   TOO_LOW, //ENCODER TOO LOW!!! WILL BREAK THINGS!!! MOVE UP OR DISABLE!!!
   AT_UPPER_LIMIT, //normal operation, cannot move up farther
   AT_LOWER_LIMIT //normal operation, cannot move lower 
}