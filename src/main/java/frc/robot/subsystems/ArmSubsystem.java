// Homework for Week 2
package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
  // Rotations per meter refers to how many full rotations of the motor correlate to one meter up by
  // the elevator
  private static final double rotationsPerArmRadian = 10.0;

  // TalonFX is the name of the motor controllers we use (essentially the motor in code)
  private final TalonFX motor;
  private double targetTurnAmount;
  private final PositionVoltage positionRequest;

  public ArmSubsystem() {
    // The number within the parentheses refers to the ID we assign to the motor (so we can
    // differentiate between motors)
    motor = new TalonFX(1);

    // If you are unfamiliar with PID control, don't worry about the next 5 lines of code
    // At an extremely basic level, these numbers & applied configuration give us some values that
    // help us control the mechanism
    // You can copy these numbers and lines to your arm code
    Slot0Configs slot0 = new Slot0Configs();
    slot0.kP = 2.0;
    slot0.kI = 0.0;
    slot0.kD = 0.1;
    motor.getConfigurator().apply(slot0);

    // We will get into what this does but for now, just know that this is an object that is reused
    // later in setTurnAmount to set the position of the motor
    positionRequest = new PositionVoltage(0).withSlot(0);
  }

  // turnAmount is in radians
  public void setTargetTurnAmount(double turnAmount) {
    targetTurnAmount = turnAmount;
    motor.setControl(positionRequest.withPosition(turnAmount * rotationsPerArmRadian));
  }

  public double getTurnAmount() {
    return motor.getRotorPosition().getValueAsDouble() / rotationsPerArmRadian;
  }

  public double getTargetTurnAmount() {
    return targetTurnAmount;
  }

  // Sets the encoder to read 0
  public void zeroEncoder() {
    motor.setPosition(0.0);
  }

  public boolean isAtTargetTurnAmount(double toleranceMeters) {
    return Math.abs(getTurnAmount() - targetTurnAmount) <= toleranceMeters;
  }

  public void stop() {
    setTargetTurnAmount(getTurnAmount());
  }
}
