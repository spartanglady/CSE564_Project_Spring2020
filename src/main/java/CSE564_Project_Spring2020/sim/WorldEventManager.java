package CSE564_Project_Spring2020.sim;

import java.util.List;
import java.util.Optional;

/**
 * The type World event manager.
 */
public class WorldEventManager implements ClockedComponent {
	private Optional<World> world;
	private final AxisEventManager rollEventManager;
	private final AxisEventManager pitchEventManager;
	private final AxisEventManager yawEventManager;

	/**
	 * Instantiates a new World event manager.
	 */
	public WorldEventManager() {
		world = Optional.empty();
		rollEventManager = new AxisEventManager();
		pitchEventManager = new AxisEventManager();
		yawEventManager = new AxisEventManager();
	}

	/**
	 * Sets world.
	 *
	 * @param _world the world
	 */
	public void setWorld(World _world) {
		assert(_world != null);
		world = Optional.of(_world);
	}
	
	@Override
	public void tick() {
		assert(world.isPresent());
		
		rollEventManager.tick();
		pitchEventManager.tick();
		yawEventManager.tick();
		
		Double roll = aggregateEventData(rollEventManager.getActiveEvents());
		Double pitch = aggregateEventData(pitchEventManager.getActiveEvents());
		Double yaw = aggregateEventData(yawEventManager.getActiveEvents());
		
		if (roll != 0.0d) {
			world.get().rollChanged(roll);
		}
		
		if (pitch != 0.0d) {
			world.get().pitchChanged(pitch);
		}
		
		if (yaw != 0.0d) {
			world.get().yawChanged(yaw);
		}
	}

	/**
	 * Add event.
	 *
	 * @param startTime the start time
	 * @param duration  the duration
	 * @param d_roll    the d roll
	 * @param d_pitch   the d pitch
	 * @param d_yaw     the d yaw
	 * @throws AssertionError the assertion error
	 */
	public void addEvent(
		int startTime,
		int duration,
		double d_roll,
		double d_pitch,
		double d_yaw
	) throws AssertionError {
		rollEventManager.addEvent(startTime, duration, d_roll);
		pitchEventManager.addEvent(startTime, duration, d_pitch);
		yawEventManager.addEvent(startTime, duration, d_yaw);
	}
	
	private Double aggregateEventData(List<AxisEvent> events) {
		Optional<Double> aggregate = events.stream()
				.map(AxisEvent::getDeltaDegrees)
				.reduce(Double::sum);
		return aggregate.orElseGet(() -> 0.0d);
	}
}
