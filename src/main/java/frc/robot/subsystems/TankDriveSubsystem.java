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
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TankDriveSubsystem extends SubsystemBase {
    private final TalonFX leftMotor;
    private final TalonFX rightMotor;
    public final VoltageOut voltageRequest;

    public TankDriveSubsystem() {

        leftMotor = new TalonFX(Constants.TankDriveConstants.leftMotorID);
        rightMotor = new TalonFX(Constants.TankDriveConstants.rightMotorID);

        voltageRequest = new VoltageOut(0);
        TalonFXConfigurator leftDriveMotorConfigurator = leftMotor.getConfigurator();
        TalonFXConfigurator rightDriveMotorConfigurator = rightMotor.getConfigurator();

        CurrentLimitsConfigs clcDrive = new CurrentLimitsConfigs()
                                        .withStatorCurrentLimitEnable(true)
                                        .withStatorCurrentLimit(Constants.STATOR_CURRENT_LIMIT_AMPS)
                                        .withSupplyCurrentLimitEnable(true)
                                        .withSupplyCurrentLimit(Constants.SUPPLY_CURRENT_LIMIT_AMPS);
        leftDriveMotorConfigurator.apply(clcDrive);
        rightDriveMotorConfigurator.apply(clcDrive);

    }
    
    public void setMotorVoltage(double leftVoltage, double rightVoltage){
        leftMotor.setControl(voltageRequest.withOutput(leftVoltage));
        rightMotor.setControl(voltageRequest.withOutput(rightVoltage));
    }

}
