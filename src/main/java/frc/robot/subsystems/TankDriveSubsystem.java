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
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TankDriveSubsystem extends SubsystemBase {
    private final TalonFX leftMotor;
    private final TalonFX rightMotor;
    
    //I'm using DutyCycleOut because it allows us to control the motors as a percentage of how much voltage is available.
    //Joysticks give valus in range from -1.0 to 1.0, which corresponds to the percent of the voltage applied to the motor. 
    private final DutyCycleOut dutyCycleRequest = new DutyCycleOut(0.0);

    public TankDriveSubsystem(){
        leftMotor = new TalonFX(1);
        rightMotor = new TalonFX(2);

        TalonFXConfigurator leftConfigurator = leftMotor.getConfigurator();
        TalonFXConfigurator rightConfigurator = rightMotor.getConfigurator();

        CurrentLimitsConfigs currentLimits = new CurrentLimitsConfigs()
        .withStatorCurrentLimitEnable(true)
        .withStatorCurrentLimit(65)
        .withSupplyCurrentLimitEnable(true)
        .withSupplyCurrentLimit(30);

        leftConfigurator.apply(currentLimits);
        rightConfigurator.apply(currentLimits);
    }
    //leftSpeed and rightSpeed are changeable inputs. This way, you can just call for example "drive(0.5, -0.5)" to turn left
    public void drive(double leftSpeed, double rightSpeed){
        leftMotor.setControl(dutyCycleRequest.withOutput(leftSpeed));
        rightMotor.setControl(dutyCycleRequest.withOutput(rightSpeed));
    }
}
