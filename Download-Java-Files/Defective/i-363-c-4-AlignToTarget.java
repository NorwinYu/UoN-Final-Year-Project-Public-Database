/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class AlignToTarget extends Command {

  XboxController _driveJoystick;

  double _kP = -0.1;
  double _minCommand = 0.15;

  public AlignToTarget(XboxController controller) {
    requires(Robot.DriveBase);
    
    _driveJoystick = controller;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //add a clause to turn on/off the limelight LEDs
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);

    double headingError = Math.abs(tx) <= 3 ? 0 : tx/25;
    double steeringAdjust = 0.0;
    
    steeringAdjust = headingError;    

    if(steeringAdjust != 0 && Math.abs(steeringAdjust) < _minCommand) {
      steeringAdjust = steeringAdjust < 0 ? -_minCommand : _minCommand;
    }

    //System.out.println("steeringAdjust: " + steeringAdjust);

    Robot.DifferentalDrive.arcadeDrive(-_driveJoystick.getY(Hand.kLeft), steeringAdjust);
  }

// Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
