// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
  private static final double rotationsPerRadian =
      10; // 10 turns of the motor turns the arm 1 radian
  private final TalonFX motor;
  private double targetRadians;

  private final PositionVoltage positionRequest;

  public ArmSubsystem() {
    targetRadians = 3.14 / 2d;

    motor = new TalonFX(1);
    Slot0Configs slot0 = new Slot0Configs();
    slot0.kP = 1.0;
    slot0.kI = 0;
    slot0.kD = 0;
    motor.getConfigurator().apply(slot0);

    positionRequest = new PositionVoltage(0).withSlot(0);
  }

  public void setAngle(double armRadians) {
    targetRadians = armRadians;
  }

  public double getAngle() {
    return motor.getRotorPosition().getValueAsDouble() / rotationsPerRadian;
  }

  public void zeroEncoder() {
    motor.setPosition(0.0);
  }

  public boolean isAtTargetAngle(double toleranceRadians) {
    return Math.abs(getAngle() - targetRadians) <= toleranceRadians;
  }

  public void stop() {
    setAngle(getAngle());
  }

  @Override
  public void periodic() {
    motor.setControl(positionRequest.withPosition(targetRadians * rotationsPerRadian));
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
