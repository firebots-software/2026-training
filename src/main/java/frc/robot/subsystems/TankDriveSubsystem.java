// Instructions:
// 1. create a branch off of this branch and title it firstname-lastname-week-4
// 2. you are responsible for instantiating the two motors for this subsystem
// 3. you are responsible for applying stator and supply current limits
//    for each of the motors (recommended 65 Amp stator current limits & 30 Amp limit for supply)
// 4. use the appropriate control mode for running the Tank Drive (and leave a comment for why you are using it)
// Hint: besides defining the motors in the constructor, the main method that your Tank Drive
// needs takes in input to drive the left and right motors each with a control request

package frc.robot.subsystems;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.DutyCycleOut;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TankDriveSubsystem extends SubsystemBase {

    //2. Instantiate motors
    private final TalonFX leftMotor;
    private final TalonFX rightMotor;

    // This was the example given in the slides, but it seems to be causing a type mismatch
    //final DutyCycleOut dutyCycleRequest = new VoltageOut(0.0);

    // 4. Set control mode, i used dutycycleout as the output control is normalized 
    //increasing consistency and can simplify logic (eg when turning)
    private final DutyCycleOut dutyCycleRequestLeft = new DutyCycleOut(0.0);
    private final DutyCycleOut dutyCycleRequestRight = new DutyCycleOut(0.0);

    public TankDriveSubsystem() {

        leftMotor = new TalonFX(1);
        rightMotor = new TalonFX(2);

        //3. Apply current limits
        TalonFXConfigurator masterConfiguratorLeft = leftMotor.getConfigurator();
        TalonFXConfigurator masterConfiguratorRight = rightMotor.getConfigurator();
        CurrentLimitsConfigs clcTankDrive = new CurrentLimitsConfigs()
            //constants defined in java\frc\robot\Constants.java
            .withStatorCurrentLimitEnable(true)
            .withStatorCurrentLimit(Constants.TankDriveConstants.STATOR_LIMIT)
            .withSupplyCurrentLimitEnable(true)
            .withSupplyCurrentLimit(Constants.TankDriveConstants.SUPPLY_LIMIT);
        
        masterConfiguratorLeft.apply(clcTankDrive);
        masterConfiguratorRight.apply(clcTankDrive);

    }

    public void drive(double leftMotorInput, double rightMotorInput) {
        // This is super simplified if each input (eg joystick) controls it's own motor, since it's not specified in the instructions
        leftMotor.setControl(dutyCycleRequestLeft.withOutput(leftMotorInput));
        rightMotor.setControl(dutyCycleRequestRight.withOutput(rightMotorInput));
    }
    
}
