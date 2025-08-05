// Homework for Week 8

// Used Elevator Subsystems as a reference.


// Key Changes from that subsystem to this: 
// Movement Unit – Meters → Radians
// targetHeight → targetAngleRad
// Essentially adjusted Elevator Subsystem code to match the movement unit of radians(instead of meters) and get the target and normal angle(targetAngleRad/getAngle) instead of height.



package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.controls.MotionMagicConfigs;
import com.ctre.phoenix6.controls.MotionMagicVoltage;



public class ArmSubsystem extends SubsystemBase {
    // 5 full motor rotations = 0.5 radians as given
    private static final double kRotationsPerRadian = 10.0; 
   // Because 5 rotations / 0.5 rad = 10 rotations/radian


    private final TalonFX armMotor;
    private double targetAngleRad;
    private final MotionMagicVoltage motionMagicRequest;
    private final TalonFXConfigurator masterConfiguratorArm;
    private final MotionMagicConfigs motionMagicConfigsArm;


    public ArmSubsystem() {
        // As in the elevator reference code, the number inside the parentheses is the motor ID
        armMotor = new TalonFX(1);


        // PID config (same as ElevatorSubsystem)
        Slot0Configs slot0 = 
            new Slot0Configs()
                .withKP(Constants.Arm.kP)
                .withKI(Constants.Arm.kI)
                .withKD(Constants.Arm.kD)
                .withKV(Constants.Arm.kV) // feedforward application
                .withKA(Constants.Arm.kA); // feedforward application
        masterConfiguratorArm.apply(slot0);


        motionMagicRequest = new MotionMagicVoltage(0).withSlot(0);

        //apply MotionMagicConfigs to master motor
        motionMagicConfigsArm = new MotionMagicConfigs();
        motionMagicConfigsArm.MotionMagicCruiseVelocity = Constants.Arm.MOTIONMAGIC_MAX_VELOCITY;
        motionMagicConfigsArm.MotionMagicAcceleration = Constants.Arm.MOTIONMAGIC_MAX_ACCELERATION;
        masterConfiguratorArm.apply(motionMagicConfigsArm);

        TalonFXConfigurator masterConfiguratorArm = armMotor.getConfigurator();
        CurrentLimitsConfigs clcArm = 
            new CurrentLimitsConfigs()
                .withStatorCurrentLimitEnable(true)
                .withStatorCurrentLimit(Constants.Arm.STATOR_CURRENT_LIMIT_AMPS)
                .withSupplyCurrentLimitEnable(true)
                .withSupplyCurrentLimit(Constants.Arm.SUPPLY_CURRENT_LIMIT_AMPS);

        masterConfiguratorArm.apply(clcArm); //apply current limits to master motor
        
        //Set neutral mode to brake
        MotorOutputConfigs moc =
            new MotorOutputConfigs()
                .withNeutralMode(NeutralModeValue.Brake)
                .withInverted(InvertedValue.Clockwise_Positive);
        
        masterConfiguratorArm.apply(moc);
        
    }


    // Sets the arm to a target angle in radians
    public void setAngle(double angleRad) {
        targetAngleRad = angleRad;
        armMotor.setControl(motionMagicRequest.withPosition(angleRad * kRotationsPerRadian));
    }


    // Gets the current angle of the arm in radians
    public double getAngle() {
        return armMotor.getRotorPosition().getValueAsDouble() / kRotationsPerRadian;
    }


    // Reset the encoder to zero 
    public void zeroEncoder() {
        armMotor.setPosition(0.0);
    }


    public boolean isAtTargetAngle(double toleranceRadians) {
        return Math.abs(getAngle() - targetAngleRad) <= toleranceRadians;
    }


    // Stops the arm by holding the angle it’s currently in
    public void stop() {
        setAngle(getAngle());
    }
}