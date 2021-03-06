package application.TrainController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.shape.Circle;



public class TrainControllerCtrl implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private TrainControllerSingleton mySin = TrainControllerSingleton.getInstance();
	private AnimationTimer updateAnimation;

	// NOTE: This is where you link to elements in your FXML file
	// Example:(fx:id="counter")
	// WARNING: Your fx:id and variable name Must Match!
	// Links to FXML elements
	@FXML
	private Label counter, CTCSpeed, CTCAuthority;
	@FXML
	private TextField speed, power, ki, kp, temp;
	@FXML 
	private Label actualSpeed, actualPower, currentTemp, driveStatus, MBOSpeed, MBOAuthority;
	@FXML
	private Button confirmSpeed, confirmPower, confirmKi, confirmKp, confirmTemp;
	@FXML
	private ToggleButton emergencyBrake, serviceBrake, lights, rightDoor, leftDoor;
	@FXML
	private CheckBox manual = new CheckBox(), automatic;
	@FXML
	private ComboBox<Integer> listTrainID;
	@FXML
	private Circle engineStatus, brakeStatus, signalStatus;

	Train train;

	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions

	@FXML
	public void Temperature() {
		String inputTemp = temp.getText();
		//String.format("%.2f", inputTemp);
		if(!inputTemp.isEmpty()) {	
			double temperature = Double.parseDouble(inputTemp);
			if(temperature < 0) {
				throw new IllegalArgumentException("Cannot have negative temperature.");
			}
			if(train != null) train.setTemperature(temperature);
		}
	}
	
	void UpdateTemprature() {
		Double temp = train.getTemperature();
		String temperature = String.format("%.2f",temp);
		currentTemp.setText(temperature + "�C");
	}

	@FXML
	public void Speed() {
		String inputSpeed = speed.getText();
		if(!inputSpeed.isEmpty()) {			
			double ctrlSpeed = Double.parseDouble(inputSpeed);
			if(ctrlSpeed < 0) {
				throw new IllegalArgumentException("Cannot have negative speed.");
			}
			if(train != null) train.setSpeed(ctrlSpeed);
		}
	}
	
	void UpdateSpeed() {
		Double speed = train.getActualSpeed();
		Double speedMPH = speed * 0.621371;
		String ctrlSpeed = String.format("%.2f", speedMPH);
		actualSpeed.setText(ctrlSpeed + "mph");
	}

	private void restartSpeed() {
		if(train != null) train.setSpeed(0);
	}

	//need to calculate this 
	void Power() {
		Double inputPower = train.getPower();
		if(inputPower >= 120000) {
			//String power = String.format("%.2f", inputPower);
			actualPower.setText("120,000Kwatts");
		}else {
			String power = String.format("%.2f", inputPower);
			actualPower.setText(power + "Kwatts");
		}
	}

	@FXML
	public void serviceBrake() {
		if(serviceBrake.isSelected()) {
			serviceBrake.setText("Slowing down Train.");
			restartSpeed();
		}else {
			serviceBrake.setText("Brake is off.");
		}
		if(train != null) train.toggleServiceBrake();
	}
	
	@FXML
	public void emergencyBrake() {
		if(emergencyBrake.isSelected()) {
			if(train != null) train.setSpeed(0);
			emergencyBrake.setText("EMERGENCY STOP!!");
			emergencyBrake.setStyle("-fx-background-color:red");
			restartSpeed();
			//emergencyBrake.setStyle("-fx-text-fill: black");
		} else {
			emergencyBrake.setStyle("-fx-background-color:grey");
			emergencyBrake.setText("EMERGENCY BRAKE");
		}	
		if(train != null) train.toggleEmergencyBrake();
	}

	@FXML
	public void ManualMode() {
			manual.setSelected(true);
			automatic.setSelected(false);
			driveStatus.setText("Manual Mode");
			if(train != null) train.setDriveMode(true);
	}

	@FXML
	public void AutomaticMode() {
			automatic.setSelected(true);
			manual.setSelected(false);
			driveStatus.setText("Automatic Mode");
			if(train != null) train.setDriveMode(false);
	}
	
	@FXML
	public void Ki() {
		String inputKi = ki.getText();
		if(train != null) train.setKI(Double.parseDouble(inputKi));
	}

	@FXML
	public void Kp() {
		String inputKp = kp.getText();
		if(train != null) train.setKP(Double.parseDouble(inputKp));
	}

	/**
	 *true = On 
	 *false = off 
	 */
	@FXML
	public void Lights() {
		if(lights.isSelected()) {
			if(train != null) train.setLights(true);
			lights.setText("On");
		}else {
			if(train != null) train.setLights(false);
			lights.setText("Off");
		}
	}

	@FXML
	public void rightDoor() {
		if(rightDoor.isSelected()) {
			if(train != null) train.setRightDoor(true);
			rightDoor.setText("Open");
		}else {
			if(train != null) train.setRightDoor(false);
			rightDoor.setText("Closed");
		}
	}

	/**
	 * true = open
	 * false = closed
	 */
	
	@FXML
	public void leftDoor() {
		if(leftDoor.isSelected()) {
			if(train != null) train.setLeftDoor(true);
			leftDoor.setText("Open");
		}else {
			if(train != null) train.setLeftDoor(false);
			leftDoor.setText("Closed");
		}
	}

	public void engineStatus() {
		if(!train.getEngineStatus()) {
			engineStatus.setFill(javafx.scene.paint.Color.RED);
			restartSpeed();
		}else {
			engineStatus.setFill(javafx.scene.paint.Color.GREEN);
		}
	}

	public void brakeStatus() {
		if(!train.getBrakeStatus()) {
			brakeStatus.setFill(javafx.scene.paint.Color.RED);
			restartSpeed();
		}else {
			brakeStatus.setFill(javafx.scene.paint.Color.GREEN);
		}
	}

	public void signalStatus() {
		if(!train.getSignalStatus()) {
			signalStatus.setFill(javafx.scene.paint.Color.RED);
			restartSpeed();
		} else {
			signalStatus.setFill(javafx.scene.paint.Color.GREEN);
		}
	}	

	/**
	 * this sets the speed/authority from MBO and possibly CTC office
	 * @param inputMBOSpeed
	 * @param inputMBOAuthority
	 */
	public void StationInput(double inputMBOSpeed, int inputMBOAuthority, double inputCTCSpeed, int inputCTCAuthority) {	
		MBOSpeed.setText(Double.toString(inputMBOSpeed));
		MBOAuthority.setText(Integer.toString(inputMBOAuthority));
		CTCSpeed.setText(Double.toString(inputCTCSpeed));
		CTCAuthority.setText(Integer.toString(inputMBOAuthority));
	}
	
	public void setTrain(int trainID) {
		 Train train = mySin.getTrain(trainID);
		 if(train != null) {
			 this.train = train;
		 }
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listTrainID.valueProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				setTrain(newValue);
				setKvalues();
			}
	    });

		// Starts the automatic update (NO TOUCHY!!)
		updateAnimation = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				update();
			}
		};
		updateAnimation.start();
		trainCtrl = this;
	}

	// NOTE: This is where you get new information from your singleton
	// You can read/change fx elements linked above
	// WARNING: This assumes your singleton is updating its information
	private void update() {
		if(train == null) return;
		//currentTrainID = mySin.getTrainID();
		double inputMBOSpeed = train.getMBOSpeed();
		int inputMBOAuthority = train.getMBOAuthority();
		double inputCTCSpeed = train.getCTCSpeed();
		int inputCTCAuthority = train.getCTCAuthority();
		StationInput(inputMBOSpeed, inputMBOAuthority, inputCTCSpeed, inputCTCAuthority);
		Power();
		UpdateSpeed();
		UpdateTemprature();
		engineStatus();
		brakeStatus();
		signalStatus();
	}

	static TrainControllerCtrl trainCtrl;

	static void addTrainS(int trainID) {
		trainCtrl.addTrain(trainID);
	}

	static void removeTrainS(int trainID) {
		trainCtrl.removeTrain(trainID);
	}

	void addTrain(int trainID) {
		ObservableList<Integer> list = listTrainID.getItems();
		if(!list.contains(trainID)) list.add(trainID);
	}

	private void setKvalues() {
		ki.setText(Double.toString(train.getKI()));
		kp.setText(Double.toString(train.getKP()));
	}

	void removeTrain(int trainID) {
		ObservableList<Integer> list = listTrainID.getItems();
		if(list.contains(trainID)) list.remove(trainID);
	}
}