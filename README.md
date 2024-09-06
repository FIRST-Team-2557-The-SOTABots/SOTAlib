# SOTAlib: The Most Adaptable Code in FIRST

SOTAlib is our library of java interfaces designed to offer adaptability and ease of use for integrating different types of sensors and actuators into our robot code. SOTAlib’s core principles focus on:

## Unit Standardization

All encoders, regardless of brand or model, communicate their readings in the same units: rotations and rotations per minute (RPM). Additionally, SOTAlib offers an inversion option to accommodate control systems where counter-clockwise movements must result in positive values. Ex: WPIlib’s radian based swerve code.

## Hardware Abstraction

SOTAlib treats all hardware as generic, interchangeable components. This way, instead of needing separate code for a CANSparkMax and TalonSRX, they are both created in code as a generic "motor controller" object. It works the same way for nearly all types of hardware, like gyros and encoders, with more support continuing to be added. This approach simplifies code maintenance and provides us the opportunity to reuse our robot code in future seasons. For example, much of our robot this year is very similar to our 2022 robot. Had we developed that robot with SOTAlib we could’ve completely reused our climber code.

## Benefits of SOTAlib

- Reduced Development Time: By abstracting away hardware specifics, SOTAlib allows developers to focus on robot logic and control systems instead of spending time learning and managing different hardware libraries.

- Improved Code Maintainability: Generic code for sensors and actuators reduces code duplication and simplifies maintenance as new hardware is integrated.

- Enhanced Adaptability: SOTAlib facilitates switching between different hardware components without significant code modifications, allowing for greater flexibility in project design and testing.

# Installation:
We don't know any fancy gradle magic so our installation process is just put the files in the "RobotProject/src/main/java/SOTAlib" directory. You can just download a zip and put all the files in the folder manually or you can use Git to clone the repository or use it as a submodule (we use it as a submodule).

## Dependencies:
Install these before building with SOTAlib. Follow WPIlib vendordep documentation.
- REVLib
- CTRE Phoenix 5
- CTRE Phoenix 6
- NavX

## Submodule Installation (assumes you already have a git repo for your project):
Make sure your current working directory is the main folder of your project.
    
    git submodule init
    git submodule add https://github.com/FIRST-Team-2557-The-SOTABots/SOTAlib.git ./src/main/java/SOTAlib

# How to use it:

The general process of creating an object using SOTAlib is as follows:

Json config file --mapped to--> config object --passed to--> factory --returns--> SOTAobject

## JSON config files:

All JSON files should be stored within the deploy/resources folder. They can be contained within subfolders such as "deploy/resources/arm/armconfig.json"

An abstract version of a JSON config looks like:

    {
        "variableName": value,
        "otherVariableName": value,
        "otherConfig": {
            "variable name": value
        }
    }

Documentation regarding what variables need to be within a desired config file can be found in the config file's JavaDoc. See the config files in SOTAlib/Config/*

Current configs in a good state are:
- MotorControllerConfig
- EncoderConfig
- CompositeMotorConfig

### Example Configs:

###### MotorControllerConfig:

    {
        "port": 18,
        "motorModel": "SparkMax",
        "motorType": "BRUSHLESS",
        "isInverted": false,
        "neutralOperation": "BRAKE",
        "currentLimit": 60
    }
    
###### EncoderConfig (Absolute):

    {
        "port": 0,
        "isInverted": false,
        "encoderType": "ANALOG",
        "encoderOffset": 0.16
    }

###### EncoderConfig (Relative DutyCycle):

    {
        "port": 4,
        "isInverted": false,
        "encoderType": "DUTYCYCLE"
    }

###### CompositeMotorConfig:

    "motorConfig": {
        "port": 8,
        "isInverted": false,
        "motorModel": "SparkMax",
        "motorType": "BRUSHLESS",
        "neutralOperation": "BRAKE",
        "currentLimit": 30
    },
    "encoderConfig": {
            "encoderType": "SPARKMAX",
            "isInverted": true,
            "encoderOffset": 0.0
    }

## Object Mapping:

Now that we have the config files created we need to map them to an Object to be later used with our factories.
To do this we use the ConfigUtils class, specifically the readFromClassPath method. The method takes to parameters: the class of the object you are attempting to map to, and the path to the file starting from the deploy/resources directory with the file extension omitted (although it has to end in ".json").

### Object Mapping Example:
    public class RobotContainer {
        
        private ConfigUtils mConfigUtils;

        public RobotContainer() {

            this.mConfigUtils = new ConfigUtils(); //Initialize ConfigUtils

            MotorControllerConfig motorConfig; //Initialize config object variable

            /* 
             * Maps the values stored in "deploy/motor.json" onto a MotorControllerConfig Object then assigns it to the variable.
             * */
            motorConfig = mConfigUtils.readFromClassPath(MotorControllerConfig.class, "motor");
        }
    }

## Factories:

Now that we got our configuration objects generated its time to send them to a factory to use their information to generate an SOTAObject. This is possibly the simplest section all of the factory generation methods have a single parameter (the config object). The only factory that has multiple public methods is the EncoderFactory where you have to pick generating either an Absolute or Relative Encoder. The CompositeMotorFactory is non-static so you need to initialize that before use.

### Factory Example:
    public class RobotContainer {
        
        private ConfigUtils mConfigUtils;

        public RobotContainer() {

            this.mConfigUtils = new ConfigUtils();

            MotorControllerConfig motorConfig;

            motorConfig = mConfigUtils.readFromClassPath(MotorControllerConfig.class, "motor");

            /* Only new code in this section */
            SOTA_MotorController myMotor = MotorControllerFactory.generateMotorController(motorConfig);
            
        }
    }

## Using SOTA_Objects:

All the SOTA_Objects (SOTA_MotorController, SOTA_RelativeEncoder, SOTA_AbsoluteEncoder) work very similarly to their WPIlib counterparts with some quality of life additions. Please refer to the javadoc found in the interfaces for more specific details.

SOTA_CompositeMotor is the only exception with no WPIlib counterpart. This object is used for unifying a Motor and an Encoder into a single object. This works similarly to how REV devices access their AbsoluteEncoders with SOTA_CompositeMotor.getMotor().otherMethod() or SOTA_CompositeMotor.getAbsoluteEncoder().otherMethod(). In practice this object only really exists because we wanted access to REV Through Bore encoders plugged into SparkMaxes for REV Swerve. We usually pass the SOTA_MotorController and SOTA_AbsoluteEncoder in to our subsystems on their own. 

# Currently Supported Objects Overview:
See javadoc for more specifics.

## SOTA_MotorController:
Abstraction layer for most popular motor controllers.
### Supported hardware:
- CTRE Falcon 500 (TalonFX object. Theoretically supports Kraken X60 but we don't use them so it's untested)
- CTRE TalonSRX
- REV SparkMax
- REV SparkFlex

## SOTA_AbsoluteEncoder:
Abstraction for Absolute encoders.
### Supported Hardware:
- Analog Absolute Encoders
- DutyCycle Absolute Encoders
- REV Absolute Encoder (only generated through a SOTA_CompositeMotor see documentation for that object)

## SOTA_RelativeEncoder:
Abstraction for relative encoders. We don't use these so its untested (submit a pr if you want to validate them).
### Supported Hardware:
- Quadrature Encoders

## SOTA_CompositeMotor:
Wrapper Class with One motor and One Encoder (Absolute or Relative).
! RELATIVE ENCODER CANNOT BE GENERATED IN FACTORY ! instead you must directly handled the creation of the object (or send a pr to make that factory work better).
The only reason this class exists at the moment is to create SOTA_AbsoluteEncoders for REV AbsoluteEncoders connected to SparkMaxes. We usually separate the two into a SOTA_MotorController and SOTA_AbsoulteEncoder as we pass them into our subsystems.
### Supported Hardware:
all hardware supported by SOTA_MotorController, SOTA_AbsoluteEncoder, SOTA_RelativeEncoder.

## SOTA_Gyro:
Gyro Abstraction. No factory, see the constructors for the objects in Gyro directory.
### Supported Hardware:
- NavX
- CTRE Pigeon

## SOTA_XboxController:
WPIlib CommandXboxController object with quality of life additions (controller deadband and triggers behave like the rest of the buttons).

## Math/Conversions:
Class with a bunch of static methods for converting between units.

# Currently Unsupported Objects:
The pneumatics and shifter objects were used in 2023 for our shifting swerve that year, but they were done weirdly and we haven't fixed them because we haven't needed them. 

# Full Robot Example/Best Practices:
As we are the namesake and only developer of this project Team 2557 The SOTAbots has used SOTAlib since 2023. With our best usage of the library so far being our Robot2024 repository (https://github.com/FIRST-Team-2557-The-SOTABots/Robot2024). 

## Best Practices:
### Config management:
We break up our configs into different directories for different subsystems and we usually create our own config classes to keep all the motors and encoders for the subsystem in the same file. Here's an example of our directory structure and our configuration for our Intake Subsystem. See the whole project for more information.

#### File Tree snippet:
    Deploy/
    ├─ Resources/
    │  ├─ Intake/
    │  │  ├─ Intake.json
    │  ├─ Swerve/
    │  │  ├─ BackLeft.json
    │  │  ├─ BackRight.json
    │  │  ├─ Drive.json
    │  │  ├─ FrontLeft.json
    │  │  ├─ FrontRight.json

#### Intake.json
    {
        "intakeVoltage": 1,
        "sensorTrigger": 200,
        "motorConfig": {
            "port" : 9,
            "isInverted": true,
            "motorModel": "SparkMax",
            "motorType" : "BRUSHLESS",
            "neutralOperation" : "BRAKE",
            "currentLimit" : 30
        }
    }

#### IntakeConfig.java (stored in frc/robot/subsystems/configs)

    package frc.robot.subsystems.configs;

    import SOTAlib.Config.MotorControllerConfig;

    public class IntakeConfig {
        private double intakeVoltage;
        private MotorControllerConfig motorConfig;
        private int sensorTrigger;

        public MotorControllerConfig getMotorConfig() {
            return motorConfig;
        }

        public double getIntakeVoltage() {
            return intakeVoltage;
        }

        public int getSensorTrigger() {
            return sensorTrigger;
        }
    }

### Initialization and Dependency Injection:
We generate all our motors and sensors in RobotContainer and pass them into our subsystems as Constructor parameters. The main practical benefit is that everything is created in the same place so when something doesn't load right you know where to look. Additionally this means it's harder to accidentally create multiple objects for the same hardware.
We also generate each subsystem independently within their own try catch statement preventing one initialization failure to take out the entire robot. Robot2024's RobotContainer is a good example of this.

#### Robot2024 RobotContainer.java Constructor

            try {
                DeliveryConfig deliveryConfig = mConfigUtils.readFromClassPath(DeliveryConfig.class, "delivery/delivery");
                SOTA_MotorController deliveryMotor = MotorControllerFactory
                        .generateMotorController(deliveryConfig.getDeliveryConfig());
                SOTA_MotorController shooterDeliveryMotor = MotorControllerFactory
                        .generateMotorController(deliveryConfig.getShooterDeliveryConfig());
                DigitalInput shooterNoteSensor = new DigitalInput(3);
                this.mDelivery = new Delivery(deliveryConfig, deliveryMotor, shooterDeliveryMotor, shooterNoteSensor);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ShooterConfig shooterConfig = mConfigUtils.readFromClassPath(ShooterConfig.class, "shooter/shooter");
                SOTA_MotorController linearActuator = MotorControllerFactory
                        .generateMotorController(shooterConfig.getLinearActuatorConfig());
                SOTA_AbsoulteEncoder linearEncoder = EncoderFactory
                        .generateAbsoluteEncoder(shooterConfig.getLinearEncoderConfig());
                SOTA_MotorController leftMotor = MotorControllerFactory
                        .generateMotorController(shooterConfig.getLeftShooterConfig());
                SOTA_MotorController rightMotor = MotorControllerFactory
                        .generateMotorController(shooterConfig.getRightShooterConfig());

                this.mShooter = new Shooter(shooterConfig, linearActuator, linearEncoder, leftMotor, rightMotor);
            } catch (Exception e) {
                e.printStackTrace();
            }

# Notes / Ideas for future improvements:

1) This is an entirely student programmed project. There are some inconsistencies and imperfections. However, the core product has worked flawlessly throughout our run of the 2024 season (even winning us the Creativity Award at the Clackamas Academy Event as well as the Robot Tournament).
2) Our programming team was very small during the development of this project and therefore we did not have the resources to properly unit test everything. Again, the core project has worked without issues for us, but your mileage may vary.
3) This project is a WIP that has been refined as we progress through the seasons. Probably not the ideal development environment, but we make it work.

With that said there are some things that could be improved down the line:
- Relative and Absolute Encoders should probably have separate configs.
- The CompositeMotor factory should probably support the creation of relative encoders.
- MotorPositionLimits sounds cool in theory but needs to have a closer look taken at its implementation. 
- SOTA pneumatics probably aren't necessary right now, but if Shifting Swerve makes a comeback take another look.
- There are objects that are misspelled, they work but a spell check on the library could be good.

## Pid control on the motor controllers:
The lower latency of on controller PIDs is a dramatic benefit for the control of mechanisms. Ideally we would be able to include methods for interacting with these systems in SOTA_MotorControllers, but right now the difference between CTRE's and REV's interfaces is too big to adapt easily. If you can figure out how to do it nicely submit a pr.
