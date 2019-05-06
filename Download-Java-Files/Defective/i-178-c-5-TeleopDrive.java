/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.teleopcommands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TeleopDrive extends Command {

  private final static double Y_THRESHOLD = 0.3;
  private final static double Z_THRESHOLD = 0.3;

  private final static double Y_NERF = 1;
  private final static double Z_NERF = 0.8;

  public TeleopDrive() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.driveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double y = Robot.oi.getDriveY();
    double z = Robot.oi.getDriveX();
		double yInput = Y_NERF * (Math.abs(y) < Y_THRESHOLD ? 0 : -y);
    double zInput = Z_NERF * (Math.abs(z) < Z_THRESHOLD ? 0 : -z);
    Robot.driveTrain.arcade(yInput, zInput);
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
