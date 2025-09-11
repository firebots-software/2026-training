// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.TorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.CANrange;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;
import dev.doglog.DogLog;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;




public class ElevatorSubsystem extends SubsystemBase {
  private static ElevatorSubsystem instance;

  private TalonFX motor1;
  private TalonFX motor2;
  public TalonFX master;

  private LinearFilter elevatorFilter;
  private double currentHeightToF;
  private boolean elevatorZeroed;

  private MotionMagicConfigs mmc;
  private float currentLevel;
  private float targetHeight;
  private CANrange distance; // Time of Flight (ToF) sensor

  private float tolerance;

  private final MotionMagicVoltage controlRequest = new MotionMagicVoltage(0);
  private final TorqueCurrentFOC torqueRequest = new TorqueCurrentFOC(0);
  private final VelocityVoltage velocityRequest = new VelocityVoltage(0);
  /** Creates a new ExampleSubsystem. */
  public ElevatorSubsystem() {}

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public boolean isAtPosition() {
    return Math.abs(motor1.getPosition().getValueAsDouble() - targetHeight) < tolerance;
  }

  public void setPosition(float position) {
    targetHeight = position;
    motor1.setControl(controlRequest.withPosition(position));
    motor2.setControl(motor1.getAppliedControl());
  }
}