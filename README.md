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

# How to use it:

The general process of creating an object using SOTAlib is as follows:

Json config file --mapped to--> config object --passed to--> factory --returns--> SOTAobject

## JSON config files:

All JSON files should be stored within the deploy/resources folder. They can be contained within subfolders such as "deploy/resources/arm/armconfig.json"

An abstract version of a JSON config looks like:

    {
        "variableName": value,
        "otherVaribleName": value,
        "otherConfig": {
            "varible name": value
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