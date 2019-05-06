package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ArcadeDrive extends Command {
  public ArcadeDrive() {
    requires(Robot.DriveBase);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.DriveBase.ArcadeDriveMethod(Robot.Oi.DriveJoystick);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.DriveBase.StopAll();
  }

  @Override
  protected void interrupted() {
    end();
  }
}