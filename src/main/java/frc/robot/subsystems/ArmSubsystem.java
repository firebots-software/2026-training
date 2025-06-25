// Homework for Week 2
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.Slot0Configs;
import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.controls.PositionVoltage;

public class ArmSubsystem extends SubsystemBase {
    private static final double kRotationsPerRadian = 10.0;

    private final TalonFX armMotor;
    private final PositionVoltage positionRequest;
    private double targetAngle;


    public ArmSubsystem() {
        armMotor = new TalonFX(0);

        Slot0Configs s0cArm = new Slot1Configs().withKP(2.0).withKI(0.0).withKD(0.1);
        armMotor.getConfigurator().apply(s0cArm);

        positionRequest = new PositionVoltage(0).withSlot(0);
    }

    public void setAngle(double angleRadians) {
        targetAngle = angleRadians;
        armMotor.setControl(positionRequest.withPosition(angleRadians * kRotationsPerRadian));
    }

    public double getAngle() {
        return armMotor.getRotorPosition().getValueAsDouble() / kRotationsPerRadian;
    }

    public void stop() {
        setAngle(getAngle());
    }

    public boolean isAtAngle(double tolerance) {
        return (Math.abs(getAngle() - targetAngle)) <= tolerance;
    }

    public void zeroEncoder() {
        armMotor.setPosition(0.0);
    }
}