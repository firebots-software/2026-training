package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.PositionVoltage;

public class ArmSubsystem extends SubsystemBase {
  // 10 rotations of the arm motor = 1 radian of movement
  // or about 0.1745 degrees per rotation
  private static final int rotationsPerRadian = 10;
  private static final double rotationsPerDegree = 0.1745;

  private final TalonFX motor;
  // Set both so that both radians and degrees can be set
  private double targetRadians;
  private double targetDegrees;
  private final PositionVoltage positionRequest;

  public ArmSubsystem() {
    motor = new TalonFX(1);

    positionRequest = new PositionVoltage(0).withSlot(0);
  }

  // Allow choice between setting radians or degrees
  public void setAngle(double angle, boolean radians) {
    if (radians) {
      targetRadians = angle;
      motor.setControl(positionRequest.withPosition(angle * rotationsPerRadian));
    } else {
      targetDegrees = angle;
      motor.setControl(positionRequest.withPosition(angle * rotationsPerDegree));
    }
  }

  public double getAngle(boolean radians) {
    if (radians) {
      return motor.getRotorPosition().getValueAsDouble() / rotationsPerRadian;
    } else {
      return motor.getRotorPosition().getValueAsDouble() / rotationsPerDegree;
    }
  }

  public void stop() {
    setAngle(getAngle(true), true);
  }
}
