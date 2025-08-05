// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  private final TalonFX motor;
  private double targetRadians;
  private final MotionMagicVoltage motorRequestMotionMagic;
  private final PositionVoltage positionRequest;

  public ArmSubsystem() {
    positionRequest = new PositionVoltage(0).withSlot(0);

    motor = new TalonFX(1);
    // TalonFXConfigurator masterConfiguratorArmMotor = motor.getConfigurator();
    CurrentLimitsConfigs CurrentLimits =
        new CurrentLimitsConfigs()
            .withStatorCurrentLimitEnable(true)
            .withStatorCurrentLimit(65)
            .withSupplyCurrentLimitEnable(true)
            .withSupplyCurrentLimit(30);

    // masterConfiguratorArmMotor.apply(CurrentLimits);
    // makes the neutral mode brake
    MotorOutputConfigs neutralMode =
        new MotorOutputConfigs()
            .withNeutralMode(NeutralModeValue.Brake)
            .withInverted(InvertedValue.Clockwise_Positive);

    Slot0Configs slot0 = new Slot0Configs();
    slot0.kS = Constants.ArmConstants.kS; // Add 0.25 V output to overcome static friction
    slot0.kV = Constants.ArmConstants.kV; // A velocity target of 1 rps results in 0.12 V output
    slot0.kA = Constants.ArmConstants.kA; // An acceleration of 1 rps/s requires 0.01 V output
    slot0.kP = Constants.ArmConstants.kP; // A position error of 2.5 rotations results in 12 V output
    slot0.kI = Constants.ArmConstants.kI; // no output for integrated error
    slot0.kD = Constants.ArmConstants.kD; // A velocity error of 1 rps results in 0.1 V output

    var talonFXConfigs = new TalonFXConfiguration();

    var motionMagicConfigs = talonFXConfigs.MotionMagic;
    motionMagicConfigs.MotionMagicCruiseVelocity =
        Constants.MotionMagicConstants.MOTIONMAGIC_MAX_VELOCITY; // Target cruise velocity of 80 rps
    motionMagicConfigs.MotionMagicAcceleration =
        Constants.MotionMagicConstants
            .MOTIONMAGIC_MAX_ACCELERATION; // Target acceleration of 160 rps/s (0.5 seconds)

    motor.getConfigurator().apply(CurrentLimits);
    motor.getConfigurator().apply(slot0);
    motor.getConfigurator().apply(neutralMode);

    // create a Motion Magic request, voltage output
    motorRequestMotionMagic = new MotionMagicVoltage(0);

    targetRadians = getAngle();
  }

  public boolean setAngle(double armRadians) {
    if (0 <= armRadians && armRadians <= 180) {
      this.targetRadians = armRadians;
      //                                                    number of rotations
      motor.setControl(
          motorRequestMotionMagic.withPosition(
              Constants.ArmConstants.rotationsPerRadian * armRadians));
      return true;
    } // else
    return false;
  }

  public double getAngle() {
    return motor.getRotorPosition().getValueAsDouble() / Constants.ArmConstants.rotationsPerRadian;
  }

  public double getTargetRadians() {
    return this.targetRadians;
  }

  public void zeroEncoder() {
    motor.setPosition(0.0);
  }

  public boolean isAtTargetAngle(double toleranceRadians) {
    return Math.abs(getAngle() - this.targetRadians) <= toleranceRadians;
  }

  public void stop() {
    setAngle(getAngle());
  }

  @Override
  public void periodic() {
    motor.setControl(
        positionRequest.withPosition(targetRadians * Constants.ArmConstants.rotationsPerRadian));
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
