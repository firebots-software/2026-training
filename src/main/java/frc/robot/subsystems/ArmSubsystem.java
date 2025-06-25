// Homework for Week 2
package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;

public class ArmSubsystem {
    private final TalonFX motor;
    private final PositionVoltage positionRequest;
    /* i dont really know what TalonFX is
    i just saw it on the elevatorsubsystem code
    */
    // i also dont know what a PID is but i saw it on the elevator subsystem example
    public ArmSubsystem() {
        motor = new TalonFX(1);
        Slot0Configs slot0 = new Slot0Configs();
        slot0.kP = 2.0;
        slot0.kI = 0.0;
        slot0.kD = 0.1;
        motor.getConfigurator().apply(slot0);
        positionRequest = new PositionVoltage(0).withSlot(0);
    }
    public double getMovementRadians(){
        return motor.getRotorPosition().getValue()/10;
        //this seems pretty similar to getHeight in the elevator subsystem code except in radians
    }
    public double armAngle(){
        /* i dont really know how to code this but i know i want to use an imu
        sensor to find the pitch yaw or roll the arm is currently at (depending
        on which axis it spins on). maybe a future use for this coule be if we
        wanted to pick up game pieces using this arm, knowing the angle of the arm
        would make it easier to align it with the game piece?
         */
    }
    public boolean isHolding(){
        /* i dont know how to code this either but to check if the arm is holding or
        grabbing something maybe we could use a beam break sensor?
         */
    }
}