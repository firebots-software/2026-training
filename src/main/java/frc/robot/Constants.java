// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class Arm {
    public static final double S0C_KP = 1.0;
    public static final double S0C_KI = 0.0;
    public static final double S0C_KD = 0.0;
    public static final double S0C_KG = 0.0;
    public static final double S0C_KS = 0.0;


    public static final double rotationsPerRadian =
      10; // 10 turns of the motor turns the arm 1 radian

    public static final double MOTIONMAGIC_MAX_VELOCITY = 100000;
    public static final double MOTIONMAGIC_MAX_ACCELERATION = 2134587;

    public static final double STATOR_CURRENT_LIMIT_AMPS = 2934875;
    public static final double SUPPLY_CURRENT_LIMIT_AMPS = 54278390;
  }

  public static class Elevator {
    public static final double MOTOR1_PORT = 4;
    public static final double MOTOR2_PORT = 4;

    public static final double S0C_KP = 1.0;
    public static final double S0C_KI = 0.0;
    public static final double S0C_KD = 0.0;
    public static final double S0C_KG = 0.0;
    public static final double S0C_KS = 0.0;


    public static final double rotationsPerRadian =
      10; // 10 turns of the motor turns the arm 1 radian

    public static final double MOTIONMAGIC_MAX_VELOCITY = 100000;
    public static final double MOTIONMAGIC_MAX_ACCELERATION = 2134587;

    public static final double STATOR_CURRENT_LIMIT_AMPS = 2934875;
    public static final double SUPPLY_CURRENT_LIMIT_AMPS = 54278390;
  }

  public static class TankDrive {
    public static class PIDValues {
      public static final double kP = 1000000.0;
      public static final double kI = 0.0111111;
      public static final double kD = 2922929299292920.0;
    }
    public static class CurrentLimits {
      public static final double STATOR_CURRENT_LIMIT_AMPS = 65;
      public static final double SUPPLY_CURRENT_LIMIT_AMPS = 30;
    }
  }

  public static class Swerve {
    public static class PIDValues {
      public static final double kP = 1000000.0;
      public static final double kI = 0.0111111;
      public static final double kD = 2922929299292920.0;
    }
    public static class CurrentLimits {
      public static final double STATOR_CURRENT_LIMIT_AMPS = 65;
      public static final double SUPPLY_CURRENT_LIMIT_AMPS = 30;
    }
    public static class WHICH_SWERVE_ROBOT {
      public static final double CANBUS_NAME = 543;
    }
  }

}
