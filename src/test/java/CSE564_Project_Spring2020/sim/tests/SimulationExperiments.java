package CSE564_Project_Spring2020.sim.tests;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import CSE564_Project_Spring2020.sim.*;
import CSE564_Project_Spring2020.sim.helpers.Experiment;
import CSE564_Project_Spring2020.sim.helpers.StabilityControlSystem;
import CSE564_Project_Spring2020.sim.helpers.TestExperimentWorldEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimulationExperiments {
	/**
	 * Basic integration test. More involved tests should create
	 * CSV files from output and be analyzed manually.
	 * 
	 * @throws InterruptedException propagated from Thread.join
	 */
	TestExperimentWorldEvent firstEvent;
	TestExperimentWorldEvent secondEvent;
	DelaySettings delaySettings;

	List<ExperimentWorldData> expectedWorldData;
	List<ExperimentGyroData> expectedGyroData;
	List<ExperimentActuatorData> expectedActuatorData;


	@BeforeEach
	public void setup() {
		/**
		 *	Generating Event Test Data for the Experiment
		 */
		firstEvent = new TestExperimentWorldEvent(10, 10, 0.1, 0.2, 0.3);
		secondEvent = new TestExperimentWorldEvent(30, 10, 0.3, 0.1, 0.2);
		delaySettings = new DelaySettings(300, 300, 300, 300);

		/**
		 * Generating Expected Event Data
		 */
		expectedWorldData = generateData();
		expectedGyroData = generateGyroData();
		expectedActuatorData = generateActuatorData();
	}
	@Test
	void testBasicWorldEvents() throws InterruptedException {
		/**
		 * Initializing the Experiment
		 */
		Experiment userAction = new Experiment(ControllerType.None)
												.addEvent(firstEvent)
												.addEvent(secondEvent)
												.setDelay(delaySettings);

		/**
		 * Initializing the Stability Control System
		 */

		StabilityControlSystem controlSystem = new StabilityControlSystem(userAction);

		/**
		 * System Stabilizing the Event
		 */
		controlSystem.init()
					 .signalControllers()
				     .stabilize()
				     .getGeneratedData();

		/**
		 * Validating the Stabilized Event
		 */
		assertIterableEquals(expectedWorldData, controlSystem.getWorldTable());
		assertIterableEquals(expectedGyroData, controlSystem.getGyroTable());
		assertIterableEquals(expectedActuatorData, controlSystem.getActuatorTable());
	}

	@Test
	void testSimpleController() throws InterruptedException {
		/**
		 * Initializing the Experiment
		 */
		Experiment userAction = new Experiment(ControllerType.None)
				.addEvent(firstEvent)
				.addEvent(secondEvent)
				.setDelay(delaySettings);

		StabilityControlSystem controlSystem = new StabilityControlSystem(userAction);

		/**
		 * System Stabilizing the Event
		 */
		controlSystem.init()
				.signalControllers()
				.stabilize()
				.getGeneratedData();

		/**
		 * Validating the Stabilized Event
		 */
		assertIterableEquals(expectedWorldData, controlSystem.getWorldTable());
		assertIterableEquals(expectedGyroData, controlSystem.getGyroTable());
		assertIterableEquals(expectedActuatorData, controlSystem.getActuatorTable());
	}

	@Test
	void testIncrementalController() throws InterruptedException {
		/**
		 * Initializing the Experiment
		 */
		Experiment userAction = new Experiment(ControllerType.None)
				.addEvent(firstEvent)
				.addEvent(secondEvent)
				.setDelay(delaySettings);

		/**
		 * System Stabilizing the Event
		 */
		StabilityControlSystem controlSystem = new StabilityControlSystem(userAction);

		/**
		 * System Stabilizing the Event
		 */
		controlSystem.init()
				.signalControllers()
				.stabilize()
				.getGeneratedData();

		/**
		 * Validating the Stabilized Event
		 */
		assertIterableEquals(expectedWorldData, controlSystem.getWorldTable());
		assertIterableEquals(expectedGyroData, controlSystem.getGyroTable());
		assertIterableEquals(expectedActuatorData, controlSystem.getActuatorTable());
	}


















	private List<ExperimentWorldData> generateData() {
		return Arrays.asList(
				new ExperimentWorldData(1, 0.0, 0.0, 0.0),
				new ExperimentWorldData(2, 0.0, 0.0, 0.0),
				new ExperimentWorldData(3, 0.0, 0.0, 0.0),
				new ExperimentWorldData(4, 0.0, 0.0, 0.0),
				new ExperimentWorldData(5, 0.0, 0.0, 0.0),
				new ExperimentWorldData(6, 0.0, 0.0, 0.0),
				new ExperimentWorldData(7, 0.0, 0.0, 0.0),
				new ExperimentWorldData(8, 0.0, 0.0, 0.0),
				new ExperimentWorldData(9, 0.0, 0.0, 0.0),

				new ExperimentWorldData(10, 0.1, 0.2, 0.3),
				new ExperimentWorldData(11, 0.2, 0.4, 0.6),
				new ExperimentWorldData(12, 0.3, 0.6, 0.9),
				new ExperimentWorldData(13, 0.4, 0.8, 1.2),
				new ExperimentWorldData(14, 0.5, 1.0, 1.5),
				new ExperimentWorldData(15, 0.6, 1.2, 1.8),
				new ExperimentWorldData(16, 0.7, 1.4, 2.1),
				new ExperimentWorldData(17, 0.8, 1.6, 2.4),
				new ExperimentWorldData(18, 0.9, 1.8, 2.7),
				new ExperimentWorldData(19, 1.0, 2.0, 3.0),

				new ExperimentWorldData(20, 1.0, 2.0, 3.0),
				new ExperimentWorldData(21, 1.0, 2.0, 3.0),
				new ExperimentWorldData(22, 1.0, 2.0, 3.0),
				new ExperimentWorldData(23, 1.0, 2.0, 3.0),
				new ExperimentWorldData(24, 1.0, 2.0, 3.0),
				new ExperimentWorldData(25, 1.0, 2.0, 3.0),
				new ExperimentWorldData(26, 1.0, 2.0, 3.0),
				new ExperimentWorldData(27, 1.0, 2.0, 3.0),
				new ExperimentWorldData(28, 1.0, 2.0, 3.0),
				new ExperimentWorldData(29, 1.0, 2.0, 3.0),

				new ExperimentWorldData(30, 1.3, 2.1, 3.2),
				new ExperimentWorldData(31, 1.6, 2.2, 3.4),
				new ExperimentWorldData(32, 1.9, 2.3, 3.6),
				new ExperimentWorldData(33, 2.2, 2.4, 3.8),
				new ExperimentWorldData(34, 2.5, 2.5, 4.0),
				new ExperimentWorldData(35, 2.8, 2.6, 4.2),
				new ExperimentWorldData(36, 3.1, 2.7, 4.4),
				new ExperimentWorldData(37, 3.4, 2.8, 4.6),
				new ExperimentWorldData(38, 3.7, 2.9, 4.8),
				new ExperimentWorldData(39, 4.0, 3.0, 5.0),

				new ExperimentWorldData(40, 4.0, 3.0, 5.0)
		);
	}

	private List<ExperimentGyroData> generateGyroData() {
		List<ExperimentGyroData> expectedGyroData = new ArrayList<ExperimentGyroData>();
		for (int i = 1; i < 41; ++i) {
			expectedGyroData.add(new ExperimentGyroData(i, 0.0, 0.0, 0.0)); // Updates every 200ms; no data
		}
		return expectedGyroData;
	}

	private List<ExperimentActuatorData> generateActuatorData() {
		List<ExperimentActuatorData> expectedActuatorData = new ArrayList<ExperimentActuatorData>();
		for (int i = 1; i < 41; ++i) {
			expectedActuatorData.add(new ExperimentActuatorData(i, 0.0, 0.0, 0.0)); // Updates every 200ms; no data
		}
		return expectedActuatorData;
	}

}
