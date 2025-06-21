// Homework for Week 2
// Used Elevator Subsystems as a reference.


// Key Changes from that subsystem to this: 
// Movement Unit – Meters → Radians
// targetHeight → targetAngleRad
// Essentially adjusted Elevator Subsystem code to match the movement unit of radians(instead of meters) and get the target and normal angle(targetAngleRad/getAngle) instead of height.



package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;


public class ArmSubsystem extends SubsystemBase {
    // 5 full motor rotations = 0.5 radians as given
    private static final double kRotationsPerRadian = 10.0; 
   // Because 5 rotations / 0.5 rad = 10 rotations/radian


    private final TalonFX motor;
    private double targetAngleRad;
    private final PositionVoltage positionRequest;


    public ArmSubsystem() {
        // As in the elevator reference code, the number inside the parentheses is the motor ID
        motor = new TalonFX(1);


        // PID config (same as ElevatorSubsystem)
        Slot0Configs slot0 = new Slot0Configs();
        slot0.kP = 2.0;
        slot0.kI = 0.0;
        slot0.kD = 0.1;
        motor.getConfigurator().apply(slot0);


        positionRequest = new PositionVoltage(0).withSlot(0);
    }


    // Sets the arm to a target angle in radians
    public void setAngle(double angleRad) {
        targetAngleRad = angleRad;
        motor.setControl(positionRequest.withPosition(angleRad * kRotationsPerRadian));
    }


    // Gets the current angle of the arm in radians
    public double getAngle() {
        return motor.getRotorPosition().getValueAsDouble() / kRotationsPerRadian;
    }


    // Reset the encoder to zero 
    public void zeroEncoder() {
        motor.setPosition(0.0);
    }


    public boolean isAtTargetAngle(double toleranceRadians) {
        return Math.abs(getAngle() - targetAngleRad) <= toleranceRadians;
    }


    // Stops the arm by holding the angle it’s currently in
    public void stop() {
        setAngle(getAngle());
    }
}