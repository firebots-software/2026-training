// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem2 extends SubsystemBase {
    private final TalonFX motor;
    private double targetPosition;
    private final MotionMagicVoltage positionRequest;

    public ArmSubsystem2() {
        motor = new TalonFX(1);

        Slot0Configs s0cArm = new Slot0Configs().withKP(Constants.Arm.S0C_KP).withKI(Constants.Arm.S0C_KI).withKD(Constants.Arm.S0C_KD).withKS(Constants.Arm.S0C_KS).withKG(Constants.Arm.S0C_KG);
        MotionMagicConfigs mmc = new MotionMagicConfigs().withMotionMagicCruiseVelocity(Constants.Arm.MOTIONMAGIC_MAX_VELOCITY).withMotionMagicAcceleration(Constants.Arm.MOTIONMAGIC_MAX_ACCELERATION);
        CurrentLimitsConfigs clc = new CurrentLimitsConfigs().withStatorCurrentLimit(Constants.Arm.STATOR_CURRENT_LIMIT_AMPS).withStatorCurrentLimitEnable(true).withSupplyCurrentLimit(Constants.Arm.SUPPLY_CURRENT_LIMIT_AMPS).withSupplyCurrentLimitEnable(true);
        MotorOutputConfigs moc = new MotorOutputConfigs().withNeutralMode(NeutralModeValue.Brake).withInverted(InvertedValue.Clockwise_Positive);

        TalonFXConfigurator configuration = motor.getConfigurator();
        configuration.apply(s0cArm);
        configuration.apply(mmc);
        configuration.apply(clc);
        configuration.apply(moc);

        positionRequest = new MotionMagicVoltage(0).withSlot(0);
    }

    public void setPosition(double positionRadians) {
        targetPosition = positionRadians;
        motor.setControl(positionRequest.withPosition(targetPosition * Constants.Arm.rotationsPerRadian));
    }

    public double getPosition() {
        return motor.getRotorPosition().getValueAsDouble() / Constants.Arm.rotationsPerRadian;
    }

    public void zeroEncoder() {
        motor.setPosition(0.0);
    }

    public boolean isAtTargetAngle(double toleranceRadians) {
        return Math.abs(getPosition()-targetPosition) <= toleranceRadians;
    }

    public void stop() {
        setPosition(getPosition());
    }
}