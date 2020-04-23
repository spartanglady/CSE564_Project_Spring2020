package CSE564_Project_Spring2020.ui;

import java.util.Optional;

import CSE564_Project_Spring2020.sim.WorldEventManager;

/**
 * The type Main screen model.
 */
public class MainScreenModel {
	/**
	 * The type Acceleration event data.
	 */
	static class AccelerationEventData {
		/**
		 * The Start time.
		 */
		public int startTime;
		/**
		 * The Duration.
		 */
		public int duration;
		/**
		 * The Roll acceleration.
		 */
		public double rollAcceleration;
		/**
		 * The Pitch acceleration.
		 */
		public double pitchAcceleration;
		/**
		 * The Yaw acceleration.
		 */
		public double yawAcceleration;
		
		private Optional<WorldEventManager> worldEventManager;

		/**
		 * Instantiates a new Acceleration event data.
		 */
		public AccelerationEventData() {
			startTime = 0;
			duration = 0;
			rollAcceleration = 0;
			yawAcceleration = 0;
			pitchAcceleration = 0;
			worldEventManager = Optional.empty();
		}

		/**
		 * Register world event manager.
		 *
		 * @param _worldEventManager the world event manager
		 */
		public void registerWorldEventManager(WorldEventManager _worldEventManager) {
			assert(_worldEventManager != null);
			worldEventManager = Optional.of(_worldEventManager);
		}

		/**
		 * Send to event manager.
		 */
		public void sendToEventManager() {
			worldEventManager.ifPresent(
				(WorldEventManager m) -> m.addEvent(
					startTime,
					duration,
					rollAcceleration,
					pitchAcceleration,
					yawAcceleration
				)
			);
		}
	}

	/**
	 * The Acceleration event data.
	 */
	public final AccelerationEventData accelerationEventData = new AccelerationEventData();
}
