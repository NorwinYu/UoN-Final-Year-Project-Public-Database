package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.AlignToTarget;
import frc.robot.commands.ButtonPress;

public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());

  // Driver Joystick
  public XboxController DriveJoystick = new XboxController(RobotMap.DriveController);
  public static JoystickButton AlignToTargetButton;

  // Button Board
  public static Joystick ButtonBoard;
  public static JoystickButton HatchFloor;
  public static JoystickButton HatchOne;
  public static JoystickButton HatchTwo;
  public static JoystickButton HatchThree;
  public static JoystickButton CargoFloor;
  public static JoystickButton CargoOne;
  public static JoystickButton CargoTwo;
  public static JoystickButton CargoThree;
  public static JoystickButton Park;
  public static JoystickButton IntakeIn;
  public static JoystickButton IntakeOut;


  public OI() {
    // Driver controller
    //DriveJoystick;
    AlignToTargetButton = new JoystickButton(DriveJoystick, RobotMap.AlignToTarget);
    AlignToTargetButton.whileHeld(new AlignToTarget(DriveJoystick));

    // Button Board
    ButtonBoard = new Joystick(RobotMap.ButtonBoard);
    HatchFloor = new JoystickButton(ButtonBoard, RobotMap.HatchFloor);
    HatchOne = new JoystickButton(ButtonBoard, RobotMap.HatchOne);
    HatchTwo = new JoystickButton(ButtonBoard, RobotMap.HatchTwo);
    HatchThree = new JoystickButton(ButtonBoard, RobotMap.HatchThree);
    CargoFloor = new JoystickButton(ButtonBoard, RobotMap.CargoFloor);
    CargoOne = new JoystickButton(ButtonBoard, RobotMap.CargoOne);
    CargoTwo = new JoystickButton(ButtonBoard, RobotMap.CargoTwo);
    CargoThree = new JoystickButton(ButtonBoard, RobotMap.CargoThree);
    Park = new JoystickButton(ButtonBoard, RobotMap.Park);
    IntakeIn = new JoystickButton(ButtonBoard, RobotMap.IntakeIn);
    IntakeOut = new JoystickButton(ButtonBoard, RobotMap.IntakeOut);
  }  
}