// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  static Joystick joy1;
  static Joystick joy2;

  Spark rightFront;
  Spark rightBack;
  Spark leftFront;
  Spark leftBack;

  MotorControllerGroup right;
  MotorControllerGroup left;

  Compressor compressor;
  DoubleSolenoid grabber;
  CANSparkMax armUpDown;
  CANSparkMax armInOut;
  AHRS gyro;

  static double rj;
  static double lj;
  static boolean a;
  static double lj2;
  static double rt;
  static double lt;

  double speed;
  double turn;
  double turnRight;
  double turnLeft;
  
  private static void controls(){
    lj = joy1.getRawAxis(1);
    rj = joy1.getRawAxis(4);
    a = joy2.getRawButton(1);
    lj2 = joy2.getRawAxis(1);
    rt = joy2.getRawAxis(3);
    lt = joy2.getRawAxis(2);
  }
  @Override
  public void robotInit() {
    joy1 = new Joystick(0);
    joy2 = new Joystick(1);

    rightFront = new Spark(1);
    rightBack = new Spark(3);
    leftFront = new Spark(2);
    leftBack = new Spark(4);

    right = new MotorControllerGroup(rightFront, rightBack);
    left = new MotorControllerGroup(leftFront, leftBack);

    compressor = new Compressor(3, PneumaticsModuleType.CTREPCM); 
    compressor.enableDigital();

    grabber = new DoubleSolenoid(3, PneumaticsModuleType.CTREPCM, 1, 2);
    armUpDown = new CANSparkMax(1, MotorType.kBrushed);
    armInOut = new CANSparkMax(2, MotorType.kBrushless);

  }

  @Override
  public void robotPeriodic() {
    controls();
  }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    

  }

  @Override
  public void teleopPeriodic() {
    
    speed = lj;
    if(speed < .075 && speed > -.075){
      speed = 0;
    }
    turn = rj;
    if(turn < .075 && turn > -.075){
      turn = 0;
    }

    turnRight = speed + turn;
    turnLeft = speed - turn;

    right.set(turnRight);
    left.set(-turnLeft);

    //grabber
    if(a){
      grabber.set(Value.kForward);
    }else{
      grabber.set(Value.kReverse);
    }

    //arm
    armUpDown.set((lj2) * .5);
    armInOut.set(rt - lt);

  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
