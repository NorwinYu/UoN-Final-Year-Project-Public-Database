/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.HatchCommands.*;
import frc.robot.commands.HatchControls;

/**
 * Add your docs here.
 */
public class HatchManipulator extends Subsystem {
  private WPI_TalonSRX hatchIntake=RobotMap.hatchIntake;
  private WPI_TalonSRX hatchFlipper=RobotMap.hatchFlipper;
  public DigitalInput switchDown = RobotMap.flipperLimitSwitchDown;
  public DigitalInput switchUp = RobotMap.flipperLimitSwitchUp;
  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new HatchControls());
  }
  public boolean getLimitSwitchState(){
    return (switchDown.get()||switchUp.get());
    // if hatchFlipper limit switch 1 activated
    //   return true
    // else if hatchFlipper limit switch 2 activated
    //   return true
    // else
    //   return false
  }
  public void setFlipper(double power){
    hatchFlipper.set(ControlMode.PercentOutput,power);
  }
  public void flipUp(){
    setDefaultCommand(new FlipUp());
  }
  public void flipDown(){
    setDefaultCommand(new FlipDown());
  }
  public void setIntake(double power){
    hatchIntake.set(ControlMode.PercentOutput,power);
  }
  public void fingerRaise(){
//  Robot.pnuematic.fingerRaise();
  }
  public void fingerLower(){
//    Robot.pnuematic.fingerLower();
  }
  public void hatchExtend(){
//    Robot.pnuematic.hatchExtend();
  }
  public void hatchRetract(){
//    Robot.pnuematic.hatchRetract();
  }
}
