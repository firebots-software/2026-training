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
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TankDriveSubsystem extends SubsystemBase {
    // Instantiating the two motors of the Tank Drive Subsystem
    private final TalonFX leftMotor = new TalonFX(0);
    private final TalonFX rightMotor = new TalonFX(1);

    // Using VoltageOut instead of DutyCycle control request because allows for precise voltage control instead of percent of total available current, which is unstable
    private VoltageOut voltageRequestR, voltageRequestL;

    public TankDriveSubsystem() {
        // Initialize Voltage Out Requests
        voltageRequestR = new VoltageOut(0.0);
        voltageRequestL = new VoltageOut(0.0);

        // Apply Stator and Supply current limits to the motors
        TalonFXConfigurator rightMotorConfigurator = rightMotor.getConfigurator();
        TalonFXConfigurator leftMotorConfigurator = leftMotor.getConfigurator();
        
        CurrentLimitsConfigs currentLimitConfigs =
            new CurrentLimitsConfigs()
                .withStatorCurrentLimitEnable(true)
                .withStatorCurrentLimit(65)
                .withSupplyCurrentLimitEnable(true)
                .withSupplyCurrentLimit(30);
        
        rightMotorConfigurator.apply(currentLimitConfigs);
        leftMotorConfigurator.apply(currentLimitConfigs);
    }

    // Main method for controling the Tank Drivebase
    public void RunMotorsAtVoltages(double leftVoltage, double rightVoltage) {
        leftMotor.setControl(voltageRequestL.withOutput(leftVoltage));
        rightMotor.setControl(voltageRequestR.withOutput(rightVoltage));
    }

    // Stop all motors
    public void StopMotors() {
        leftMotor.stopMotor();
        rightMotor.stopMotor();
    }
}
