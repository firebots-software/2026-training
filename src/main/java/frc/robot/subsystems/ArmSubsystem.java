// Homework for Week 2
package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
  // Info variables
  private double targetDegrees;
  private static final double motorRotationsPerArmDegree =
      10
          * (2 * Math.PI
              / 360); // Multiply 10 motor rotations by how many radians are in a degree (10 motor
  // rotations = 1 arm radian)

  // Motor variables
  private final TalonFX motor;
  private double initialRotations;

  // Motion control variables
  private final PositionVoltage positionRequest;

  public ArmSubsystem() {
    // The arm has one motor that turns it.

    // Initializing motor
    // An initial targetDegrees of 0 means the arm will always try to go to rest (horizontal) at the
    // beginning
    this.targetDegrees = 0;
    this.motor = new TalonFX(0, "MyCanbus");
    // Stores the encoder reading at the start of the program. Assuming arm is horizontal when
    // initialized.
    this.initialRotations = motor.getRotorPosition().getValueAsDouble();

    // PID Configurations
    Slot0Configs slot0 = new Slot0Configs();
    slot0.kP = 2.0;
    slot0.kI = 0.0;
    slot0.kD = 0.1;
    motor.getConfigurator().apply(slot0);

    positionRequest = new PositionVoltage(0).withSlot(0);
  }

  public void setTargetDegrees(double targetDegrees) {
    // Set the target degrees of the arm subsystem, making sure it is between 0 and 90 degrees
    this.targetDegrees = Math.min(Math.max(targetDegrees, 0), 90);
    // Move arm to the target position
    motor.setControl(positionRequest.withPosition(this.targetDegrees * motorRotationsPerArmDegree));
  }

  public double getTargetDegrees() {
    return this.targetDegrees;
  }

  public double getCurrentMotorRotations() {
    // Return the current position of the arm, in rotations, with the initial arm position at the
    // start of execution being 0
    return motor.getRotorPosition().getValueAsDouble() - this.initialRotations;
  }

  public double getCurrentArmDegrees() {
    return this.getCurrentMotorRotations() / motorRotationsPerArmDegree;
  }

  public void zeroEncoder() {
    motor.setPosition(0.0);
    this.initialRotations = 0;
  }

  public boolean isAtTargetDegrees(double tolerance) {
    return Math.abs(this.getTargetDegrees() - this.getCurrentArmDegrees()) <= tolerance;
  }
}
