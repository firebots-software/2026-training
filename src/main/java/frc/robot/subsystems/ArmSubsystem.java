package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.PositionOut;

// This is heavily based on gh/firebots-software/ExampleElevatorSubsystem as linked in the assignment doc

public class ArmSubsystem extends SubsystemBase {
	// given that 5 rotations is .5 radians
	private static final double rotationsPerRadian = 10;
	
	private final TalonFX motor;
	private double targetAngle;
	
	// these would have to be initialized properly
	private double minAngle;
	private double maxAngle;
	
	public ArmSubsystem() {
		// necessary hardware: 1 motor
		motor = new TalonFX(1);
	}
	
	// move arm to the input angle
	public void setAngle (double targetInputRadians) {	
	
		// clamp the input value to valid movement only
		targetInputRadians = Math.max(minAngle, Math.min(maxAngle, targetInputRadians));
		
		targetAngle = targetInputRadians;
		
		motor.setControl(new PositionOut(targetInputRadians * rotationsPerRadian));
	}
	
	// can return either the raw encoder value or the angle in radians. If we know what option we will be using we can remove the option for a raw value and just return that.
	public double getCurrentAngle(boolean rawValue) {
		if (rawValue) {
			return motor.getRotorPosition().getValue();
		} else {
			return motor.getRotorPosition().getValue() / rotationsPerRadian;
		}
	}
	
	public double getCurrentAngle() {
		return getCurrentAngle(false);
	}
	
	public void zeroEncoder() {
		motor.getRotorPosition().set(0.0);
	}
	
	public boolean isAtTarget(double toleranceRadians) {
		return Math.abs(getCurrentAngle() - targetAngle) <= toleranceRadians;
	}
	
	public void stop() {
		setAngle(getCurrentAngle());
	}
}