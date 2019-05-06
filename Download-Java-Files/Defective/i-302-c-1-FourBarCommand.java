
package frc.robot.commands;

public class FourBarCommand extends CommandBase {
  boolean buttonReleased, fourBarStart, floorGather, hatchLvlTwo, rocketLvlTwo, rocketLvlOne, climb, ballLvlOne,
      ballLvlTwo;
  int leftBarEncoder, rightBarEncoder;
  int startPoint = 5, floorGatherPoint = 6, hatchLvlTwoPoint = 7, rocketLvlTwoPoint = 8, rocketLvlOnePoint = 9,
      climbPoint = 10, ballLvlOnePoint = 11, ballLvlTwoPoint = 12, wristDownSetPoint = 70;

  public FourBarCommand() {
    // Use requires() here to declare subsystem dependencies
    requires(fourBar);
    requires(wrist);
    leftBarEncoder = fourBar.getLeftBarEncoder();
    rightBarEncoder = fourBar.getRightBarEncoder();

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    // send pid to correct motors
    fourBar.setBothFourBarMotor(0, 0);
    fourBar.resetEncoders();
    buttonReleased = true;
    fourBarStart = false;

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    // Eight positions: starting/lvl 1 hatch, floor gather, hatch lvl 2, rocket lvl
    // 2, rocket lvl 1, climb position, lvl 1 ball, lvl 2 ball

    // starting position button (getBarStartingButton)
    if (oi.getBarStartingButton() && buttonReleased) {
      fourBarStart = !fourBarStart;
      buttonReleased = !oi.getBarStartingButton();
    }

    if (!oi.getBarStartingButton()) {
      buttonReleased = true;
    }

    if (fourBarStart) {
      fourBar.setStartPoint(startPoint);
      fourBar.setBothFourBarMotor(0.5, 0.5);
    }

    // floor gather button
    if (oi.getFloorGatherButton() && buttonReleased) {
      floorGather = !floorGather;
      buttonReleased = !oi.getFloorGatherButton();
    }

    if (!oi.getFloorGatherButton()) {
      buttonReleased = true;
    }

    // hatch level 2 button- also gather + cargo ship level
    if (hatchLvlTwo) {
      fourBar.setHatchLvlTwoPoint(hatchLvlTwoPoint);
      fourBar.setBothFourBarMotor(0.5, 0.5);
    }
    
    //floor gather functionality
    if (floorGather) {
      fourBar.setFloorGatherPoint(floorGatherPoint);
      fourBar.setBothFourBarMotor(0.5, 0.5);
      wrist.setWristDownSetpoint(wristDownSetPoint);
      wrist.setBothWristMotor(0.5, 0.5);
    }

    // hatch level 2 button- also gather + cargo ship level
    if (oi.getHatchLvlTwoButton() && buttonReleased) {
      hatchLvlTwo = !hatchLvlTwo;
      buttonReleased = !oi.getHatchLvlTwoButton();
    }

    if (!oi.getHatchLvlTwoButton()) {
      buttonReleased = true;
    }

    // rocket level two button
    if (rocketLvlTwo) {
      fourBar.setRocketLvlTwoPoint(rocketLvlTwoPoint);
      fourBar.setBothFourBarMotor(0.5, 0.5);
    }

    if (hatchLvlTwo) {
      fourBar.setHatchLvlTwoPoint(hatchLvlTwoPoint);
      fourBar.setBothFourBarMotor(0.5, 0.5);
    }

    // rocket level 2 button
    if (oi.getRocketLvlTwoButton() && buttonReleased) {
      rocketLvlTwo = !rocketLvlTwo;
      buttonReleased = !oi.getRocketLvlTwoButton();
    }

    if (!oi.getRocketLvlTwoButton()) {
      buttonReleased = true;
    }

    // rocket level one button
    if (rocketLvlOne) {
      fourBar.setRocketLvlOnePoint(rocketLvlOnePoint);
      fourBar.setBothFourBarMotor(0.5, 0.5);
    }

    if (rocketLvlTwo) {
      fourBar.setRocketLvlTwoPoint(rocketLvlTwoPoint);
      fourBar.setBothFourBarMotor(0.5, 0.5);
    }

    // rocket level 1 button
    if (oi.getRocketLvlOneButton() && buttonReleased) {
      rocketLvlOne = !rocketLvlOne;
      buttonReleased = !oi.getRocketLvlOneButton();
    }

    if (!oi.getRocketLvlOneButton()) {
      buttonReleased = true;
    }

    // fourbar + wrist climbing position button
    if (climb) {
      fourBar.setClimbPoint(climbPoint);
      fourBar.setBothFourBarMotor(0.5, 0.5);
    }

    if (rocketLvlOne) {
      fourBar.setRocketLvlOnePoint(rocketLvlOnePoint);
      fourBar.setBothFourBarMotor(0.5, 0.5);
    }

    // fourbar climb position button
    if (oi.getBarClimbPositionButton() && buttonReleased) {
      climb = !climb;
      buttonReleased = !oi.getBarClimbPositionButton();
    }

    if (!oi.getBarClimbPositionButton()) {
      buttonReleased = true;
    }

    // ball level one button
    if (ballLvlOne) {
      fourBar.setBallLvlOnePoint(ballLvlOnePoint);
      fourBar.setBothFourBarMotor(0.5, 0.5);
    }

    if (oi.getBallLvlOneButton() && buttonReleased) {
      ballLvlOne = !ballLvlOne;
      buttonReleased = !oi.getBallLvlOneButton();
      
    if (climb) {
      fourBar.setClimbPoint(climbPoint);
      fourBar.setBothFourBarMotor(0.5, 0.5);
    }

    // ball level 1 button
    if (oi.getBallLvlOneButton() && buttonReleased) {
      ballLvlOne = !ballLvlOne;
      buttonReleased = !oi.getBallLvlOneButton();

    }

    if (!oi.getBallLvlOneButton()) {
      buttonReleased = true;
    }

    // ball level two button
    if (ballLvlTwo) {
      fourBar.setBallLvlTwoPoint(ballLvlTwoPoint);
      fourBar.setBothFourBarMotor(0.5, 0.5);
    }

    if (ballLvlOne) {
      fourBar.setBallLvlOnePoint(ballLvlOnePoint);
      fourBar.setBothFourBarMotor(0.5, 0.5);
      wrist.setWristDownSetpoint(wristDownSetPoint);
      wrist.setBothWristMotor(0.5, 0.5);
    }

    // ball level 2 button
    if (oi.getBallLvlTwoButton() && buttonReleased) {
      ballLvlTwo = !ballLvlTwo;
      buttonReleased = !oi.getBallLvlTwoButton();
    }

    if (!oi.getBallLvlTwoButton()) {
      buttonReleased = true;
    }

    if (ballLvlTwo) {
      fourBar.setBallLvlTwoPoint(ballLvlTwoPoint);
      fourBar.setBothFourBarMotor(0.5, 0.5);
      wrist.setWristDownSetpoint(wristDownSetPoint);
      wrist.setBothWristMotor(0.5, 0.5);
    }
  }
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
