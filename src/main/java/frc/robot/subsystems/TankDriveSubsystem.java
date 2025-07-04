// Instructions:
// 1. create a branch off of this branch and title it firstname-lastname-week-4
// 2. you are responsible for instantiating the two motors for this subsystem
// 3. you are responsible for applying stator and supply current limits
//    for each of the motors (recommended 65 Amp stator current limits & 30 Amp limit for supply)
// 4. use the appropriate control mode for running the Tank Drive (and leave a comment for why you
// are using it)
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
  // I'm using duty cycle out because when someone drives with a joystick, depending on how far they
  // push the joy stick, a percentage of the max voltage in turn the max speed will be used on the
  // robot.
  final DutyCycleOut dutyCycleRequest = new DutyCycleOut(0.0);

  public TankDriveSubsystem() {
    leftMotor = new TalonFX(1);
    rightMotor = new TalonFX(2);

    TalonFXConfigurator masterConfiguratorLeftMotor = leftMotor.getConfigurator();
    TalonFXConfigurator masterConfiguratorRightMotor = rightMotor.getConfigurator();
    CurrentLimitsConfigs CurrentLimits =
        new CurrentLimitsConfigs()
            .withStatorCurrentLimitEnable(true)
            .withStatorCurrentLimit(65)
            .withSupplyCurrentLimitEnable(true)
            .withSupplyCurrentLimit(30);

    masterConfiguratorLeftMotor.apply(CurrentLimits);
    masterConfiguratorRightMotor.apply(CurrentLimits);
  }

  public void zeroEncoders() {
    leftMotor.setPosition(0.0);
    rightMotor.setPosition(0.0);
  }

  public void driveLeftMotorAmount(double driveAmount) {
    leftMotor.setControl(dutyCycleRequest.withOutput(driveAmount));
  }

  public void driveRightMotorAmount(double driveAmount) {
    rightMotor.setControl(dutyCycleRequest.withOutput(driveAmount));
  }

  public void driveBothMotors(double leftDriveAmount, double rightDriveAmount) {
    leftMotor.setControl(dutyCycleRequest.withOutput(leftDriveAmount));
    rightMotor.setControl(dutyCycleRequest.withOutput(rightDriveAmount));
  }
}
