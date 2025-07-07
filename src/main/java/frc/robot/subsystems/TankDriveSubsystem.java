package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.DutyCycleOut;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TankDriveSubsystem extends SubsystemBase {

  private final TalonFX leftDriveMotor;
  private final TalonFX rightDriveMotor;

  private final DutyCycleOut tankDriveControl = new DutyCycleOut(0.0);

  public TankDriveSubsystem() {
    leftDriveMotor = new TalonFX(1);
    rightDriveMotor = new TalonFX(2);

    CurrentLimitsConfigs currentLimitConfig = new CurrentLimitsConfigs()
      .withStatorCurrentLimitEnable(true)
      .withStatorCurrentLimit(65)
      .withSupplyCurrentLimitEnable(true)
      .withSupplyCurrentLimit(30);

    leftDriveMotor.getConfigurator().apply(currentLimitConfig);
    rightDriveMotor.getConfigurator().apply(currentLimitConfig);
  }

  public void tankDrive(double leftPower, double rightPower) {
    leftDriveMotor.setControl(tankDriveControl.withOutput(leftPower));
    rightDriveMotor.setControl(tankDriveControl.withOutput(rightPower));
  }

  public void resetEncoders() {
    leftDriveMotor.setPosition(0.0);
    rightDriveMotor.setPosition(0.0);
  }

  public double getLeftEncoder() {
    return leftDriveMotor.getPosition().getValueAsDouble();
  }

  public double getRightEncoder() {
    return rightDriveMotor.getPosition().getValueAsDouble();
  }
}
