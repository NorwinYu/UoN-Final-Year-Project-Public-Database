/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
// import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class CargoMechanism implements CargoMechanismInterface {

	private static final double STAGING_WHEEL_PERCENT_VOLTAGE = 0.7;
	private static final double INTAKE_BAR_PERCENT_VOLTAGE = 0.5;
	private static final double LAUNCHER_LOW_PERCENT_VOLTAGE = 0.4;
	private static final double LAUNCHER_HIGH_PERCENT_VOLTAGE = 0.8;
	private static final double LAUNCHER_REVERSE_PERCENT_VOLTAGE = -0.4;

	private TalonSRX stagingWheel = new TalonSRX(4);
	private TalonSRX launcher = new TalonSRX(6);
	VictorSPX intakeBar = new VictorSPX(2);
	DoubleSolenoid hood = new DoubleSolenoid(0, 7, 6);
	DoubleSolenoid arm = new DoubleSolenoid(0, 4, 5);

	CargoMechanismModes state = CargoMechanismModes.idle;

	@Override
	public void periodic() {
		
		switch (state) {

			case idle:
				stagingWheelOff();
				launcherOff();
				intakeBarOff();
				intakeArmIn();
				hoodUp();
				break;
			case floorIntakeBarOut:
				stagingWheelOn();
				launcherOff();
				intakeBarOn();
				intakeArmOut();
				hoodUp();
				break;
			case floorIntakeBarIn:
				stagingWheelOn();
				launcherOff();
				intakeBarOn();
				intakeArmIn();
				hoodUp();
				break;
			case midIntake:
				stagingWheelOn();
				launcherOff();
				intakeBarReverse();
				intakeArmOut();
				hoodUp();
				break;
			case hoodIntake:
				stagingWheelOff();
				launcherReverse();
				intakeBarOff();
				intakeArmIn();
				hoodUp();
				break;
			case shootHigh:
				stagingWheelOn();
				launcherOnHigh();
				intakeBarOff();
				intakeArmIn();
				hoodUp();
				break;
			case shootLow:
				stagingWheelOn();
				launcherOnLow();
				intakeBarOn();
				intakeArmIn();
				hoodDown();
				break;
			case flush:
				stagingWheelReverse();
				launcherReverse();
				intakeBarReverse();
				intakeArmIn();
				hoodUp();
				break;
		}
	}
	
	@Override
	public void idle() {
		state = CargoMechanismModes.idle;
    }
    
	@Override
	public void floorIntakeBarOut() {
		state = CargoMechanismModes.floorIntakeBarOut;
    }
    
	@Override
	public void floorIntakeBarIn() {
		state = CargoMechanismModes.floorIntakeBarIn;
    }
    
	@Override
	public void midIntake() {
		state = CargoMechanismModes.midIntake;
    }
    
	@Override
	public void hoodIntake() {
		state = CargoMechanismModes.hoodIntake;
    }
    
	@Override
	public void shootHigh() {
		state = CargoMechanismModes.shootHigh;
    }
    
	@Override
	public void shootLow() {
		state = CargoMechanismModes.shootLow;
	}
	
	@Override
	public void flush() {
		state = CargoMechanismModes.flush;
	}

	private void intakeArmIn() {
		arm.set(Value.kReverse);
	}

	private void intakeArmOut() {
		arm.set(Value.kForward);
	}

	private void hoodDown() {
		hood.set(Value.kReverse);
	}

	private void hoodUp() {
		hood.set(Value.kForward);
	}

	private void intakeBarOn() {
		intakeBar.set(ControlMode.PercentOutput, INTAKE_BAR_PERCENT_VOLTAGE);
	}

	private void intakeBarOff() {
		intakeBar.set(ControlMode.PercentOutput, 0);
	}

	private void intakeBarReverse() {
		intakeBar.set(ControlMode.PercentOutput, -INTAKE_BAR_PERCENT_VOLTAGE);
	}

	private void stagingWheelOn() {
		stagingWheel.set(ControlMode.PercentOutput, -STAGING_WHEEL_PERCENT_VOLTAGE);
	}

	private void stagingWheelOff() {
		stagingWheel.set(ControlMode.PercentOutput, 0);
	}

	private void stagingWheelReverse() {
		stagingWheel.set(ControlMode.PercentOutput, STAGING_WHEEL_PERCENT_VOLTAGE);
	}

	private void launcherOnHigh() {
		launcher.set(ControlMode.PercentOutput, LAUNCHER_HIGH_PERCENT_VOLTAGE);
	}

	private void launcherOnLow() {
		launcher.set(ControlMode.PercentOutput, LAUNCHER_LOW_PERCENT_VOLTAGE);
	}

	private void launcherOff() {
		launcher.set(ControlMode.PercentOutput, 0);
	}

	private void launcherReverse() {
		launcher.set(ControlMode.PercentOutput, LAUNCHER_REVERSE_PERCENT_VOLTAGE);
	}

}
