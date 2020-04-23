package CSE564_Project_Spring2020.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import CSE564_Project_Spring2020.sim.ControllerFactory;
import CSE564_Project_Spring2020.sim.ControllerType;
import CSE564_Project_Spring2020.sim.RotationAxis;
import CSE564_Project_Spring2020.sim.Simulator;

/**
 * The type Main screen controller.
 */
public class MainScreenController {

	/**
	 * The type Main screen state listener.
	 */
	static class MainScreenStateListener implements WindowStateListener {
		private Optional<Simulator> sim;
		private List<Thread> threads;

		/**
		 * Instantiates a new Main screen state listener.
		 */
		public MainScreenStateListener() {
			sim = Optional.empty();
			threads = new LinkedList<>();
		}

		@Override
		public void windowStateChanged(WindowEvent e) {
			if (e.getNewState() == WindowEvent.WINDOW_CLOSED) {
				sim.ifPresent(Thread::interrupt);
				threads.stream()
					.filter(Thread::isAlive)
					.forEach(Thread::interrupt);
			}
		}

		/**
		 * Register simulator.
		 *
		 * @param _sim the sim
		 */
		public void registerSimulator(Simulator _sim) {
			assert(_sim != null);
			sim = Optional.of(_sim);
		}

		/**
		 * Add thread.
		 *
		 * @param _thread the thread
		 */
		public void addThread(Thread _thread) {
			threads.add(_thread);
			
			if (threads.size() > 1000) {
				threads = threads.stream()
							.filter(Thread::isAlive)
							.collect(Collectors.toCollection(LinkedList::new));
			}
		}
	}

	/**
	 * The constant mainScreenStateListener.
	 */
	public static final MainScreenStateListener mainScreenStateListener = new MainScreenStateListener();

	/**
	 * The type Simulation starter.
	 */
	static abstract class SimulationStarter implements ActionListener {
		/**
		 * The Sim.
		 */
		protected Optional<Simulator> sim;
		/**
		 * The Controller picker.
		 */
		protected Optional<JComboBox<ControllerType>> controllerPicker;
		private Optional<JTextField> gyroDelayField, rollActuatorDelayField, pitchActuatorDelayField, yawActuatorDelayField;
		private Optional<JFrame> mainScreen;
		private boolean controllerIsSet;
		private final boolean delaysAreSet;

		/**
		 * Instantiates a new Simulation starter.
		 */
		public SimulationStarter() {
			sim = Optional.empty();
			controllerPicker = Optional.empty();
			gyroDelayField = Optional.empty();
			rollActuatorDelayField = Optional.empty();
			pitchActuatorDelayField = Optional.empty();
			yawActuatorDelayField = Optional.empty();

			controllerIsSet = false;
			delaysAreSet = false;
		}

		/**
		 * Sets controller.
		 */
		protected void setController() {
			if (controllerIsSet) {
				return;
			}
			final ControllerType controllerType = getControllerType();
        	sim.ifPresent(
    			(Simulator s) -> ControllerFactory.CreateController(controllerType)
									.ifPresent(s::setRollController)
			);
        	sim.ifPresent(
    			(Simulator s) -> ControllerFactory.CreateController(controllerType)
    								.ifPresent(s::setPitchController)
			);
        	sim.ifPresent(
    			(Simulator s) -> ControllerFactory.CreateController(controllerType)
    								.ifPresent(s::setYawController)
			);
	        controllerIsSet = true;
		}

		/**
		 * Sets simulation delays.
		 *
		 * @return the simulation delays
		 */
		protected boolean setSimulationDelays() {
			if (delaysAreSet) {
				return false;
			}
			
			boolean[] success = new boolean[] { true };
			
			sim.ifPresent((Simulator s) -> {
				gyroDelayField.ifPresent((JTextField f) -> {
					Integer value = parseDelayField(f.getText());
					if (value != null) {
						s.setGyroDelay(value);
					}
					else {
						success[0] = false;
					}
				});
				
				rollActuatorDelayField.ifPresent((JTextField f) -> {
					Integer value = parseDelayField(f.getText());
					if (value != null) {
						s.setActuatorDelay(RotationAxis.ROLL, value);
					}
					else {
						success[0] = false;
					}
				});
				
				pitchActuatorDelayField.ifPresent((JTextField f) -> {
					Integer value = parseDelayField(f.getText());
					if (value != null) {
						s.setActuatorDelay(RotationAxis.PITCH, value);
					}
					else {
						success[0] = false;
					}
				});
				
				yawActuatorDelayField.ifPresent((JTextField f) -> {
					Integer value = parseDelayField(f.getText());
					if (value != null) {
						s.setActuatorDelay(RotationAxis.YAW, value);
					}
					else {
						success[0] = false;
					}
				});
			});
			
			if (success[0]) {
				gyroDelayField.ifPresent(gyroField -> gyroField.setEnabled(false));
				rollActuatorDelayField.ifPresent(rollField -> rollField.setEnabled(false));
				pitchActuatorDelayField.ifPresent(pitchField -> pitchField.setEnabled(false));
				yawActuatorDelayField.ifPresent(yawField -> yawField.setEnabled(false));
			}
			
			return !success[0];
		}
		
		private Integer parseDelayField(String fieldText) {
			try {
				Integer value = Integer.parseUnsignedInt(fieldText);

				if (value.compareTo(1) >= 0) {
					return value;
				}
			}
			catch (NumberFormatException e) {
				e.printStackTrace(System.err);
			}
			
			JOptionPane.showMessageDialog(
				mainScreen.get(),
				String.format("Invalid positive integer: %s.\nPlease enter a valid value greater than 1", fieldText),
				"Number Error!",
				JOptionPane.ERROR_MESSAGE
			);
			
			return null;
		}

		/**
		 * Gets controller type.
		 *
		 * @return the controller type
		 */
		public ControllerType getControllerType() {
			if (controllerPicker.isPresent()) {
				int index = controllerPicker.get().getSelectedIndex();
				
				if (index > -1) {
					return (ControllerType) controllerPicker.get().getSelectedItem();
				}
			}
			return ControllerType.None;
		}

		/**
		 * Register controller picker.
		 *
		 * @param _controllerPicker the controller picker
		 */
		public void registerControllerPicker(JComboBox<ControllerType> _controllerPicker) {
			assert(_controllerPicker != null);
			
			controllerPicker = Optional.of(_controllerPicker);
		}

		/**
		 * Register simulator.
		 *
		 * @param _sim the sim
		 */
		public void registerSimulator(Simulator _sim) {
			assert(_sim != null);

			sim = Optional.of(_sim);
		}

		/**
		 * Register gyro delay field.
		 *
		 * @param _gyroDelayField the gyro delay field
		 */
		public void registerGyroDelayField(JTextField _gyroDelayField) {
			assert(_gyroDelayField != null);
			
			gyroDelayField = Optional.of(_gyroDelayField);
		}

		/**
		 * Register actuator delay field.
		 *
		 * @param axis               the axis
		 * @param actuatorDelayField the actuator delay field
		 */
		public void registerActuatorDelayField(RotationAxis axis, JTextField actuatorDelayField) {
			assert(actuatorDelayField != null);
			
			if (axis == RotationAxis.ROLL) {
				rollActuatorDelayField = Optional.of(actuatorDelayField);
			}
			else if (axis == RotationAxis.PITCH) {
				pitchActuatorDelayField = Optional.of(actuatorDelayField);
			}
			else if (axis == RotationAxis.YAW) {
				yawActuatorDelayField = Optional.of(actuatorDelayField);
			}
		}

		/**
		 * Register main screen.
		 *
		 * @param _mainScreen the main screen
		 */
		public void registerMainScreen(JFrame _mainScreen) {
			assert(_mainScreen != null);
			
			mainScreen = Optional.of(_mainScreen);
		}
	}

	/**
	 * The type Start stop button listener.
	 */
	static class StartStopButtonListener extends SimulationStarter {
		private Optional<JButton> startStopButton;
		private Optional<JButton> singleStepButton;
		private Optional<JButton> multiStepButton;
		private Optional<JSpinner> stepPicker;

		/**
		 * Instantiates a new Start stop button listener.
		 */
		public StartStopButtonListener() {
			super();
			startStopButton = Optional.empty();
			singleStepButton = Optional.empty();
			multiStepButton = Optional.empty();
			stepPicker = Optional.empty();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getID() == ActionEvent.ACTION_PERFORMED) {
				startStopButton.ifPresent((JButton ssb) -> {
					if(ssb.getText().contentEquals("Start")) {
				        if (setSimulationDelays()) {
				        	return;
				        }
				        setController();
						controllerPicker.ifPresent(picker -> picker.setEnabled(false));
					}
					
					if (ssb.getText().contentEquals("Start") || ssb.getText().contentEquals("Resume")) {
						sim.ifPresent(Simulator::unpause);
						ssb.setText("Pause");
						
						singleStepButton.ifPresent(stb -> stb.setEnabled(false));
						multiStepButton.ifPresent(msb -> msb.setEnabled(false));
						stepPicker.ifPresent(picker -> picker.setEnabled(false));
					}
					else if (ssb.getText().contentEquals("Pause")) {
						sim.ifPresent(Simulator::pause);
						ssb.setText("Resume");
						
						singleStepButton.ifPresent(stb -> stb.setEnabled(true));
						multiStepButton.ifPresent(msb -> msb.setEnabled(true));
						stepPicker.ifPresent(picker -> picker.setEnabled(true));
					}
				});
			}
		}

		/**
		 * Register start stop button.
		 *
		 * @param _startStopButton the start stop button
		 */
		public void registerStartStopButton(JButton _startStopButton) {
			assert(_startStopButton != null);

			startStopButton = Optional.of(_startStopButton);
		}

		/**
		 * Register single step button.
		 *
		 * @param _singleStepButton the single step button
		 */
		public void registerSingleStepButton(JButton _singleStepButton) {
			assert(_singleStepButton != null);

			singleStepButton = Optional.of(_singleStepButton);
		}

		/**
		 * Register multi step button.
		 *
		 * @param _multiStepButton the multi step button
		 */
		public void registerMultiStepButton(JButton _multiStepButton) {
			assert(_multiStepButton != null);
			
			multiStepButton = Optional.of(_multiStepButton);
		}

		/**
		 * Register step picker.
		 *
		 * @param _stepPicker the step picker
		 */
		public void registerStepPicker(JSpinner _stepPicker) {
			assert(_stepPicker != null);
			
			stepPicker = Optional.of(_stepPicker);
		}
	}

	/**
	 * The constant startStopButtonListener.
	 */
	public static final StartStopButtonListener startStopButtonListener = new StartStopButtonListener();

	/**
	 * The type Single step button listener.
	 */
	static class SingleStepButtonListener extends SimulationStarter {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getID() == ActionEvent.ACTION_PERFORMED) {
				if (setSimulationDelays()) {
					return;
				}
				setController();
				sim.ifPresent((Simulator s) -> {
					Thread t = new Thread(s::tick);
					t.start();
					mainScreenStateListener.addThread(t);
				});
				controllerPicker.ifPresent(picker -> picker.setEnabled(false));
			}
		}
	}

	/**
	 * The constant singleStepButtonListener.
	 */
	public static final SingleStepButtonListener singleStepButtonListener = new SingleStepButtonListener();

	/**
	 * The type Multi step button listener.
	 */
	static class MultiStepButtonListener extends SimulationStarter {
		private Optional<JSpinner> stepPicker;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getID() == ActionEvent.ACTION_PERFORMED) {
				if (setSimulationDelays()) {
					return;
				}
				setController();
				sim.ifPresent((Simulator simulator) -> stepPicker.ifPresent((JSpinner stepSpinner) -> {
					final int numSteps = (Integer) stepSpinner.getValue();
					Thread t = new Thread(() -> {
						for (int i = 0; i < numSteps; ++i) {
							simulator.tick();
						}
					});
					t.start();
					mainScreenStateListener.addThread(t);
				}));
				controllerPicker.ifPresent(picker -> picker.setEnabled(false));
			}
		}

		/**
		 * Register step picker.
		 *
		 * @param _stepPicker the step picker
		 */
		public void registerStepPicker(JSpinner _stepPicker) {
			assert(_stepPicker != null);
			
			stepPicker = Optional.of(_stepPicker);
		}
	}

	/**
	 * The constant multiStepButtonListener.
	 */
	public static final MultiStepButtonListener multiStepButtonListener = new MultiStepButtonListener();

	/**
	 * The type Add event button listener.
	 */
	static class AddEventButtonListener implements ActionListener {
		private JFrame mainScreen;
		private final JTextField[] accelerationFields;
		private JTextField durationField;
		private JTextField timeField;
		
		private MainScreenModel model;

		/**
		 * Instantiates a new Add event button listener.
		 */
		AddEventButtonListener() {
			mainScreen = null;
			accelerationFields = new JTextField[3];
			durationField = null;
			timeField = null;
			
			model = null;
		}
		
		@Override
		public void actionPerformed(ActionEvent ev) {
			if (ev.getID() == ActionEvent.ACTION_PERFORMED) {
				ArrayList<Optional<Double>> accelerationValues = processAccelerationFields();
				Optional<Integer> durationValue = processUnsignedIntegerField(durationField);
				Optional<Integer> timeValue = processUnsignedIntegerField(timeField);
				
				if (accelerationValues != null
						&& durationValue.isPresent()
						&& timeValue.isPresent()) {
					setAccelerationValues(accelerationValues);
					setStartTime(timeValue.get());
					setDuration(durationValue.get());
					model.accelerationEventData.sendToEventManager();
				}
			}
		}
		
		private ArrayList<Optional<Double>> processAccelerationFields() {
			ArrayList<Optional<Double>> fieldValues = new ArrayList<>();
			for (JTextField accelerationField : accelerationFields) {
				if (accelerationField != null) {
					String fieldText = accelerationField.getText();

					if (!fieldText.isEmpty()) {
						Double value = parseDoubleField(fieldText);

						if (value == null) {
							return null;
						}

						fieldValues.add(Optional.of(value));
					} else {
						fieldValues.add(Optional.empty());
					}
				}
			}
			return fieldValues;
		}
		
		private void setAccelerationValues(ArrayList<Optional<Double>> accelerationValues) {
			for (int i = 0; i < 3; ++i) {
				Double value = accelerationValues.get(i).orElse(0.0d);

				RotationAxis type = RotationAxis.values()[i];

				if (type == RotationAxis.ROLL) {
					model.accelerationEventData.rollAcceleration = value;
				}
				else if (type == RotationAxis.PITCH) {
					model.accelerationEventData.pitchAcceleration = value;
				}
				else if (type == RotationAxis.YAW) {
					model.accelerationEventData.yawAcceleration = value;
				}
				System.out.println(String.format("%s Acceleration entered: %f", type.toString(), value));
			}
		}
		
		private void setStartTime(int startTime) {
			model.accelerationEventData.startTime = startTime;
			System.out.println(String.format("Start time entered: %d", startTime));
		}
		
		private void setDuration(int duration) {
			model.accelerationEventData.duration = duration;
			System.out.println(String.format("Duration entered: %d", duration));
		}
		
		private Double parseDoubleField(String fieldText) {
			try {

				return Double.parseDouble(fieldText);
			}
			catch (NumberFormatException e) {
				e.printStackTrace(System.err);

				JOptionPane.showMessageDialog(
					mainScreen,
					String.format("Invalid number: %s", fieldText),
					"Number Error!",
					JOptionPane.ERROR_MESSAGE
				);
				
				return null;
			}
		}
		
		private Optional<Integer> processUnsignedIntegerField(JTextField integerField) {
			Integer i = parseUnsignedIntegerField(integerField.getText());
			
			return Optional.ofNullable(i);
		}
		
		private Integer parseUnsignedIntegerField(String fieldText) {
			try {

				return Integer.parseUnsignedInt(fieldText);
			}
			catch (NumberFormatException e) {
				e.printStackTrace(System.err);

				JOptionPane.showMessageDialog(
					mainScreen,
					String.format("Invalid positive integer: %s", fieldText),
					"Number Error!",
					JOptionPane.ERROR_MESSAGE
				);
				
				return null;
			}
		}

		/**
		 * Register acceleration field.
		 *
		 * @param accelerationField the acceleration field
		 * @param axis              the axis
		 */
		public void registerAccelerationField(JTextField accelerationField, RotationAxis axis) {
			this.accelerationFields[axis.ordinal()] = accelerationField;

		}

		/**
		 * Register main screen.
		 *
		 * @param mainScreen the main screen
		 */
		public void registerMainScreen(JFrame mainScreen) {
			this.mainScreen = mainScreen;

		}

		/**
		 * Register main screen model.
		 *
		 * @param model the model
		 */
		public void registerMainScreenModel(MainScreenModel model) {
			this.model = model;

		}

		/**
		 * Register duration field.
		 *
		 * @param durationField the duration field
		 */
		public void registerDurationField(JTextField durationField) {
			this.durationField = durationField;

		}

		/**
		 * Register time field.
		 *
		 * @param timeField the time field
		 */
		public void registerTimeField(JTextField timeField) {
			this.timeField = timeField;

		}
	}

	/**
	 * The constant addEventButtonListener.
	 */
	public static final AddEventButtonListener addEventButtonListener = new AddEventButtonListener();

	/**
	 * The type Open world data screen listener.
	 */
	static class OpenWorldDataScreenListener implements ActionListener {
		private Optional<JDialog> worldDataScreen;

		/**
		 * Instantiates a new Open world data screen listener.
		 */
		public OpenWorldDataScreenListener() {
			worldDataScreen = Optional.empty();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getID() == ActionEvent.ACTION_PERFORMED) {
				worldDataScreen.ifPresent(dataScreen -> dataScreen.setVisible(true));
			}
		}

		/**
		 * Register world data screen.
		 *
		 * @param _worldDataScreen the world data screen
		 */
		public void registerWorldDataScreen(JDialog _worldDataScreen) {
			assert(_worldDataScreen != null);
			worldDataScreen = Optional.of(_worldDataScreen);
		}
		
	}

	/**
	 * The constant openWorldDataScreenListener.
	 */
	public static final OpenWorldDataScreenListener openWorldDataScreenListener = new OpenWorldDataScreenListener();
	
}
