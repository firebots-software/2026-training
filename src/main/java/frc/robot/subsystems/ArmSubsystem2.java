// Homework for Week 2
package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem2 extends SubsystemBase {

  private static final double kRotationsPerRadian = 10.0; // 5 rotations (0.5 radians) * 2

  private final TalonFX motor;
  private double targetPos;
  private final PositionVoltage positionRequest;

  public ArmSubsystem2() {
    motor = new TalonFX(1);

    Slot0Configs slot0 = new Slot0Configs();
    slot0.kP = 2.0;
    slot0.kI = 0.0;
    slot0.kD = 0.1;
    motor.getConfigurator().apply(slot0);

    positionRequest = new PositionVoltage(0).withSlot(0);
  }

  public void setPos(double rad) {
    targetPos = rad;
    motor.setControl(positionRequest.withPosition(rad * kRotationsPerRadian));
  }

  public double getPos() {
    return motor.getRotorPosition().getValueAsDouble() / kRotationsPerRadian;
  }

  public void zeroEncoder() {
    motor.setPosition(0.0);
  }

  public boolean isAtTargetPos(double tolRad) {
    return Math.abs(getPos() - targetPos) <= tolRad;
  }

  public void stop() {
    setPos(getPos());
  }

  public double getTargetPos() {
    return targetPos;
  }
}
