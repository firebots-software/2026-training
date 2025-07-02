// Instructions:
// 1. create a branch off of this branch and title it firstname-lastname-week-4
// 2. you are responsible for instantiating the two motors for this subsystem
// 3. you are responsible for applying stator and supply current limits
//    for each of the motors (recommended 65 Amp stator current limits & 30 Amp limit for supply)
// 4. use the appropriate control mode for running the Tank Drive (and leave a comment for why you are using it)
// Hint: besides defining the motors in the constructor, the main method that your Tank Drive
// needs takes in input to drive the left and right motors each with a control request

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;

public class TankDriveSubsystem {
    private final TalonFX lMotor;
    private final TalonFX rMotor;

    public TankDriveSubsystem() {
        lMotor = new TalonFX(0);
        rMotor = new TalonFX(1);

        TalonFXConfigurator lMotorConfigurator = lMotor.getConfigurator();
        TalonFXConfigurator rMotorConfigurator = rMotor.getConfigurator();

        CurrentLimitsConfigs cl = new CurrentLimitsConfigs().withSupplyCurrentLimit(6).withStatorCurrentLimit(6); //etc

        lMotorConfigurator.apply(cl);
        rMotorConfigurator.apply(cl);
    }

    private void move(String input, float speed) {
        if (input.equals("clockwise")) {
            setLMotorSpeed(speed); //conveniently enough the talonfx class has a thing where you dont need a request (it does it for you)
        }
        if (input.equals("counterclockwise")) {
            setRMotorSpeed(speed);
        }
        if (input.equals("forward")) {
            setLMotorSpeed(speed);
            setRMotorSpeed(speed);
        }
    }

    public void turnLeft(float speed) {
        move("left", speed);
    }

    public void turnRight(float speed) {
        move("right", speed);
    }

    public void moveForward(float speed) {
        move("forward", speed);
    }

    public void moveBackward(float speed) {
        move("forward", -speed);
    }

    public void brake() {
        move("forward", 0);
    }

    public void setLMotorSpeed(float speed) {
        lMotor.set(speed);
    }

    public void setRMotorSpeed(float speed) {
        rMotor.set(speed);
    }
}
