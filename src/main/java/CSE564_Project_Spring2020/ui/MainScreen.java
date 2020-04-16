package CSE564_Project_Spring2020.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainScreen {
	private MainScreenModel model;
	
    public static void main(String[] args) {
        new MainScreen().buildFrame().setVisible(true);
    }
    
    public MainScreen()
    {
    	model = new MainScreenModel();
    }

    public JFrame buildFrame() {
        JFrame newFrame = new JFrame("Environment Control");
        
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        newFrame.add(buildControlPanel());
        
        newFrame.setSize(400, 400);
        
        MainScreenController.addEventButtonListener.registerMainScreen(newFrame);
        MainScreenController.addEventButtonListener.registerMainScreenModel(model);

        return newFrame;
    }
    
    public JPanel buildControlPanel() {
    	JPanel newPanel = new JPanel(new GridBagLayout());
    	
    	newPanel.add(makeAddEventButton(), gridLocation(0, 0));
    	newPanel.add(makeAccelerationField(MainScreenController.AccelerationType.ROLL), gridLocation(0, 1));
    	newPanel.add(makeAccelerationField(MainScreenController.AccelerationType.PITCH), gridLocation(0, 2));
    	newPanel.add(makeAccelerationField(MainScreenController.AccelerationType.YAW), gridLocation(0, 3));
    	newPanel.add(makeDurationField(), gridLocation(0, 4));
    	newPanel.add(makeTimeField(), gridLocation(0, 5));
    	
    	return newPanel;
    }
    
    public GridBagConstraints gridLocation(int x, int y)
    {
    	GridBagConstraints newConstraint = new GridBagConstraints();
    	newConstraint.anchor = GridBagConstraints.PAGE_START;
    	newConstraint.gridx = x;
    	newConstraint.gridy = y;
    	
    	return newConstraint;
    }
    
    public JButton makeAddEventButton() {
    	JButton btn = new JButton("Add Event");
    	
    	btn.addActionListener(MainScreenController.addEventButtonListener);
    	
    	return btn;
    }
    
    public JPanel makeAccelerationField(MainScreenController.AccelerationType type) {
    	JPanel accelerationPanel = new JPanel();
    	
    	JLabel accelerationLabel = new JLabel(String.format("%s Acceleration: ", type.getText()));
    	JTextField accelerationField = new JTextField(10);
    	JLabel unitsLabel = new JLabel("deg/s");
    	
    	MainScreenController.addEventButtonListener.registerAccelerationField(accelerationField, type);
    	
    	accelerationPanel.add(accelerationLabel);
    	accelerationPanel.add(accelerationField);
    	accelerationPanel.add(unitsLabel);
    	
    	return accelerationPanel;
    }
    
    public JPanel makeDurationField() {
    	JPanel durationPanel = new JPanel();
    	
    	JLabel durationLabel = new JLabel("Duration: ");
    	JTextField durationField = new JTextField(10);
    	JLabel msLabel =  new JLabel("ms");
    	
    	MainScreenController.addEventButtonListener.registerDurationField(durationField);
    	
    	durationPanel.add(durationLabel);
    	durationPanel.add(durationField);
    	durationPanel.add(msLabel);
    	
    	return durationPanel;
    }
    
    public JPanel makeTimeField() {
    	JPanel timePanel = new JPanel();
    	
    	JLabel timeLabel = new JLabel("Time: ");
    	JTextField timeField = new JTextField(10);
    	JLabel msLabel = new JLabel("ms");
    	
    	MainScreenController.addEventButtonListener.registerTimeField(timeField);
    	
    	timePanel.add(timeLabel);
    	timePanel.add(timeField);
    	timePanel.add(msLabel);
    	
    	return timePanel;
    }
}
