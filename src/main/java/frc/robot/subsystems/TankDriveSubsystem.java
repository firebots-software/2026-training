// Instructions:
// x. create a branch off of this branch and title it firstname-lastname-week-4
// 2. you are responsible for instantiating the two motors for this subsystem
// 3. you are responsible for applying stator and supply current limits
//    for each of the motors (recommended 65 Amp stator current limits & 30 Amp limit for supply)
// 4. use the appropriate control mode for running the Tank Drive (and leave a comment for why you are using it)
// Hint: besides defining the motors in the constructor, the main method that your Tank Drive
// needs takes in input to drive the left and right motors each with a control request

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX; // motor

public class TankDriveSubsystem {
    private final DutyCycleOut dutyCycleRequest = new DutyCycleOut(0.0);

    private final TalonFX motorLeft;
    private final TalonFX motorRight;

    public TankDriveSubsystem() {
        motorLeft = new TalonFX(1);
        motorRight = new TalonFX(2);

        CurrentLimitsConfigs clcDrive = new CurrentLimitsConfigs()
            .withStatorCurrentLimitEnable(true)
            .withStatorCurrentLimit(65)
            .withSupplyCurrentLimitEnable(true)
            .withSupplyCurrentLimit(30);
        
        motorLeft.getConfigurator().apply(clcDrive);
        motorRight.getConfigurator().apply(clcDrive);
    }

    public void drive(double leftMotorValue, double rightMotorValue) {
        motorLeft.setControl(dutyCycleRequest.withOutput(leftMotorValue));
        motorRight.setControl(dutyCycleRequest.withOutput(rightMotorValue));
    }
}
