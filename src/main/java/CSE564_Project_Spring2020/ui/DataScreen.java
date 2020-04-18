package CSE564_Project_Spring2020.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import CSE564_Project_Spring2020.sim.DataType;

public class DataScreen {
	private JFrame mainScreen;
	private String title;
	
	public DataScreen(JFrame _mainScreen, String _title) {
		mainScreen = _mainScreen;
		title = _title;
	}
	
	public JDialog buildDialog() {
		JDialog screen = new JDialog(mainScreen, title);
		screen.setSize(300, 400);
		
		JPanel screenPanel = new JPanel(new GridBagLayout());
		screenPanel.add(buildWorldDataPanel(), gridLocation(0, 0));
		screenPanel.add(buildGyroscopePanel(), gridLocation(0, 1));
		screenPanel.add(buildActuatorPanel(), gridLocation(0, 2));
		
		screen.add(screenPanel);
		
		return screen;
	}
	
	private JPanel buildWorldDataPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		panel.add(new JLabel("World State"), gridLocation(0, 0));
		panel.add(makeDataDisplayField("Current Time", "ms", DataType.WorldTime), gridLocation(0, 1));
		panel.add(makeDataDisplayField("Roll", "deg", DataType.WorldRoll), gridLocation(0, 2));
		panel.add(makeDataDisplayField("Pitch", "deg", DataType.WorldPitch), gridLocation(0, 3));
		panel.add(makeDataDisplayField("Yaw", "deg", DataType.WorldYaw), gridLocation(0, 4));

		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		return panel;
	}
	
	private JPanel buildGyroscopePanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		panel.add(new JLabel("Gyroscope Readings"), gridLocation(0, 0));
		panel.add(makeDataDisplayField("Roll", "deg", DataType.GyroRoll), gridLocation(0, 1));
		panel.add(makeDataDisplayField("Pitch", "deg", DataType.GyroPitch), gridLocation(0, 2));
		panel.add(makeDataDisplayField("Yaw", "deg", DataType.GyroYaw), gridLocation(0, 3));
		
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		return panel;
	}
	
	private JPanel buildActuatorPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		panel.add(new JLabel("Actuator Movements"), gridLocation(0, 0));
		panel.add(makeDataDisplayField("Last Change in Roll", "deg", DataType.ActuatorRoll), gridLocation(0, 1));
		panel.add(makeDataDisplayField("Last Change in Pitch", "deg", DataType.ActuatorPitch), gridLocation(0, 2));
		panel.add(makeDataDisplayField("Last Change in Yaw", "deg", DataType.ActuatorYaw), gridLocation(0, 3));
		
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		return panel;
	}
	
    private JPanel makeDataDisplayField(final String name, final String units, final DataType type) {
    	JPanel displayField = new JPanel();
    	
    	JLabel fieldName = new JLabel(String.format("%s: ", name));
    	JLabel data = new JLabel();
    	JLabel unitsLabel = new JLabel(units);
    	
    	if (type == DataType.WorldTime) {
        	DataScreenController.worldDataListener.registerTimeLabel(data);
    	}
    	else if (type == DataType.WorldRoll) {
    		DataScreenController.worldDataListener.registerRollLabel(data);
    	}
    	else if (type == DataType.WorldPitch) {
    		DataScreenController.worldDataListener.registerPitchLabel(data);
    	}
    	else if (type == DataType.WorldYaw) {
    		DataScreenController.worldDataListener.registerYawLabel(data);
    	}
    	else if (type == DataType.GyroRoll) {
    		DataScreenController.gyroDataListener.registerRollLabel(data);
    	}    	
    	else if (type == DataType.GyroPitch) {
    		DataScreenController.gyroDataListener.registerPitchLabel(data);
    	}
    	else if (type == DataType.GyroYaw) {
    		DataScreenController.gyroDataListener.registerYawLabel(data);
    	}
    	else if (type == DataType.ActuatorRoll) {
    		DataScreenController.actuatorDataListener.registerRollLabel(data);
    	}
    	else if (type == DataType.ActuatorPitch) {
    		DataScreenController.actuatorDataListener.registerPitchLabel(data);
    	}
    	else if (type == DataType.ActuatorYaw) {
    		DataScreenController.actuatorDataListener.registerYawLabel(data);
    	}
    	
    	displayField.add(fieldName);
    	displayField.add(data);
    	displayField.add(unitsLabel);
    	
    	return displayField;
    }
	
    private GridBagConstraints gridLocation(int x, int y)
    {
    	GridBagConstraints newConstraint = new GridBagConstraints();
    	newConstraint.anchor = GridBagConstraints.PAGE_START;
    	newConstraint.gridx = x;
    	newConstraint.gridy = y;
    	
    	return newConstraint;
    }
}
