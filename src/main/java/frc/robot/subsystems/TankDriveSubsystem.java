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
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class TankDriveSubsystem extends SubsystemBase {
    private final TalonFX motorLeft;
    private final TalonFX motorRight;

    private final PositionVoltage positionRequestLeft;
    private final PositionVoltage positionRequestRight;

    public TankDriveSubsystem() {
        motorLeft = new TalonFX(0);
        motorRight = new TalonFX(1);

        Slot0Configs slot0 = new Slot0Configs();
        slot0.kP = Constants.TankDrive.PIDValues.kP;
        slot0.kI = Constants.TankDrive.PIDValues.kI;
        slot0.kD = Constants.TankDrive.PIDValues.kD;
        motorLeft.getConfigurator().apply(slot0);

        Slot0Configs slot1 = new Slot0Configs();
        slot1.kP = Constants.TankDrive.PIDValues.kP;
        slot1.kI = Constants.TankDrive.PIDValues.kI;
        slot1.kD = Constants.TankDrive.PIDValues.kD;
        motorRight.getConfigurator().apply(slot1);

        positionRequestLeft = new PositionVoltage(0).withSlot(0);
        positionRequestRight = new PositionVoltage(0).withSlot(1);

        CurrentLimitsConfigs clcTankDrive = new CurrentLimitsConfigs().withStatorCurrentLimitEnable(true).withStatorCurrentLimit(Constants.TankDrive.CurrentLimits.STATOR_CURRENT_LIMIT_AMPS).withSupplyCurrentLimitEnable(true).withSupplyCurrentLimit(Constants.TankDrive.CurrentLimits.SUPPLY_CURRENT_LIMIT_AMPS);
        TalonFXConfigurator MasterConfiguraterTankDriveLeft = motorLeft.getConfigurator();
        TalonFXConfigurator MasterConfiguraterTankDriveRight = motorRight.getConfigurator();
        MasterConfiguraterTankDriveLeft.apply(clcTankDrive);
        MasterConfiguraterTankDriveRight.apply(clcTankDrive);
    }

}
