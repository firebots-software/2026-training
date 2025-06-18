package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

public class ArmSubsystem extends SubsystemBase {

    private final TalonFX motor;
    private final double rotationsPerRadian = 10.0;
    private final PositionVoltage positionRequest;

    private double targetRotation;

    public ArmSubsystem(int motorId) {
        motor = new TalonFX(motorId);

        Slot0Configs slot0 = new Slot0Configs();
        slot0.kP = 2.0;
        slot0.kI = 0.0;
        slot0.kD = 0.1;
        motor.getConfigurator().apply(slot0);

        positionRequest = new PositionVoltage(0).withSlot(0);
    }

    public void setRotation(double rotation) {
        targetRotation = rotation;
        motor.setControl(positionRequest.withPosition(rotation * rotationsPerRadian));
    }

    public double getRotation() {
        return motor.getRotorPosition().getValue().magnitude() / rotationsPerRadian;
    }

    public boolean atRotation(double thresh) {
        return Math.abs(getRotation() - targetRotation) <= thresh;
    }

    public void stop() {
        setRotation(getRotation());
    }
}