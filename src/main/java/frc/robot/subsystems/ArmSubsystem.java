package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;

public class ArmSubsystem extends SubsystemBase {
    
    private static final double kRotationsPerRadian = 10.0; 
    private final TalonFX motor;
    private double targetAngle;

    private final PositionVoltage positionRequest;

    public ArmSubsystem() {
        motor = new TalonFX(2);
        Slot0Configs slot0 = new Slot0Configs();
        slot0.kP = 2.0;
        slot0.kI = 0.0;
        slot0.kD = 0.1;
        motor.getConfigurator().apply(slot0);
        positionRequest = new PositionVoltage(0).withSlot(0);
    }


    public void setAngle(double angleRadians) {
        targetAngle = angleRadians;
        motor.setControl(positionRequest.withPosition(angleRadians * kRotationsPerRadian));
    }

    public double getAngle() {
        return motor.getRotorPosition().getValueAsDouble() / kRotationsPerRadian;
    }

    public void zeroEncoder() {
        motor.setPosition(0.0);
    }

    public boolean isAtTargetAngle(double toleranceRadians) {
        return Math.abs(getAngle() - targetAngle) <= toleranceRadians;
    }

    public void stop() {
        setAngle(getAngle());
    }
}
