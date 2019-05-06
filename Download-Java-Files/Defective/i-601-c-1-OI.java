package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.ExtendCommand;
import frc.robot.commands.GrabCommand;
import frc.robot.commands.LiftAdjustmentCommand;
import frc.robot.commands.LiftCommand;
import frc.robot.commands.ReleaseCommand;

public class OI {
  // Makes the axis of the joystick exist

  public static enum Axis {X, Y, Z, LeftY, RightY, THROTTLE};

  // Makes the joystick and xbox controller exist

  private static Joystick driveStick = new Joystick(RobotMap.DRIVE_STICK);
  private static Joystick xBoxController = new Joystick(RobotMap.XBOX_CONTROLLER);

  public static double getJoystickAxis(int joystickID, Axis axis) {

      // Establishes the joystick and its axes

      Joystick joystick;

      double axisValue = 0;

      if (joystickID == RobotMap.DRIVE_STICK) {
          joystick = driveStick;
      } else if (joystickID == RobotMap.XBOX_CONTROLLER) {
          joystick = xBoxController;
      } else {
          System.out.println("Wrong id");
          return 0;
      }

      if (axis == Axis.X) {
          axisValue = joystick.getX();
      } else if (axis == Axis.Y) {
          axisValue = joystick.getY();
      } else if (axis == Axis.Z) {
          axisValue = joystick.getZ();
      } else if (axis == Axis.LeftY) {
          axisValue = joystick.getRawAxis(1); // 1 is supposed to be XBox id for left joystick
      } else if (axis == Axis.RightY) {
          axisValue = joystick.getRawAxis(5); // 5 is supposed to be XBox id for right joystick
      } else if (axis == Axis.THROTTLE) {
          axisValue = joystick.getThrottle();
      }

      // Creates dead zone

      if (Math.abs(axisValue) < .1) {
          axisValue = 0;
      }

      return axisValue;
  }
    

    public static JoystickButton AButton = new JoystickButton(xBoxController, 0);
    public static JoystickButton BButton = new JoystickButton(xBoxController, 1);
    public static JoystickButton XButton = new JoystickButton(xBoxController, 2);
    public static JoystickButton YButton = new JoystickButton(xBoxController, 3);

    public static JoystickButton leftBumper = new JoystickButton(xBoxController, 4);
    public static JoystickButton rightBumper = new JoystickButton(xBoxController, 5);

    public static JoystickButton backButton = new JoystickButton(xBoxController, 6);
    public static JoystickButton startButton = new JoystickButton(xBoxController, 7);

    public static JoystickButton pressLStick = new JoystickButton(xBoxController, 8);
    public static JoystickButton pressRStick = new JoystickButton(xBoxController, 9);

  public OI(){
   XButton.whileHeld(new ExtendCommand());
   rightBumper.whileHeld(new GrabCommand());
   leftBumper.whileHeld(new ReleaseCommand());
  }
}
