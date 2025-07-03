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
    private final TalonFX rightMotor;
    private final TalonFX leftMotor;

    // dutyCycleOut bc i think it'll be easier to control which side you want to turn.
    // like if you want left, then make the left speed % less than the right speed
    // also pretty sure i read somwhere that for tank drive, using this is easier for joystick control (?)
    private final DutyCycleOut dutyRequest = new DutyCycleOut(0);

    public TankDriveSubsystem() {
        rightMotor = new TalonFX(1);
        leftMotor = new TalonFX(2);

        TalonFXConfigurator rightConfigurator = rightMotor.getConfigurator();
        TalonFXConfigurator leftConfigurator = leftMotor.getConfigurator();


        CurrentLimitsConfigs clc = 
            new CurrentLimitsConfigs()
                .withStatorCurrentLimitEnable(true)
                .withStatorCurrentLimit(65)
                .withSupplyCurrentLimitEnable(true)
                .withSupplyCurrentLimit(30);
        
        rightConfigurator.apply(clc);
        leftConfigurator.apply(clc);
    }

    // turn left: drive(60, 30) - my thinking
    // easier to put in speed in whole # than deci
    // also less error of putting invalid speed (ex.2.0 vs 200)
    // no one stupid enough to put 200 speed -_-
    public void drive(double rightSpeed, double leftSpeed) {
        rightMotor.setControl(dutyRequest.withOutput(rightSpeed * 0.01));
        leftMotor.setControl(dutyRequest.withOutput(leftSpeed * 0.01)); 
    }

    public void stop() {
        drive(0,0);
    }
}
