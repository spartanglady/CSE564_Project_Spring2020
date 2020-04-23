package CSE564_Project_Spring2020.sim;

import java.util.Optional;

/**
 * The type Gyroscope.
 */
public class Gyroscope implements ClockedComponent {
	private Optional<Degree> prevRoll, prevPitch, prevYaw;
	private Optional<World> world;
	
	private Optional<DataListener> gyroscopeStateListener;

	/**
	 * Instantiates a new Gyroscope.
	 */
	public Gyroscope() {
		prevRoll = Optional.empty();
		prevPitch = Optional.empty();
		prevYaw = Optional.empty();
		world = Optional.empty();
		
		gyroscopeStateListener = Optional.empty();
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
		refresh();
	}

	/**
	 * Gets roll.
	 *
	 * @return the roll
	 */
	public Degree getRoll() {
		return prevRoll.orElse(new Degree());
	}

	/**
	 * Gets pitch.
	 *
	 * @return the pitch
	 */
	public Degree getPitch() {
		return prevPitch.orElse(new Degree());
	}

	/**
	 * Gets yaw.
	 *
	 * @return the yaw
	 */
	public Degree getYaw() {
		return prevYaw.orElse(new Degree());
	}

	/**
	 * Sets gyroscope state listener.
	 *
	 * @param l the l
	 */
	public void setGyroscopeStateListener(DataListener l) {
		assert(l != null);
		gyroscopeStateListener = Optional.of(l);
	}
	
	private void refresh() {
		Degree newRoll = world.get().getCurrentRoll();
		
		if (!prevRoll.isPresent() || !prevRoll.get().equals(newRoll)) {
			gyroscopeStateListener.ifPresent(
				(DataListener l) -> l.dataChanged(new DataChangeEvent(DataType.GyroRoll, newRoll.toString()))
			);
		}
		prevRoll = Optional.of(newRoll.copy());

		Degree newPitch = world.get().getCurrentPitch();
		
		if (!prevPitch.isPresent() || !prevPitch.get().equals(newPitch)) {
			gyroscopeStateListener.ifPresent(
				(DataListener l) -> l.dataChanged(new DataChangeEvent(DataType.GyroPitch, newPitch.toString()))
			);
		}
		prevPitch = Optional.of(newPitch.copy());
		
		Degree newYaw = world.get().getCurrentYaw();
		
		if (!prevYaw.isPresent() || !prevYaw.get().equals(newYaw)) {
			gyroscopeStateListener.ifPresent(
				(DataListener l) -> l.dataChanged(new DataChangeEvent(DataType.GyroYaw, newYaw.toString()))
			);
		}
		prevYaw = Optional.of(newYaw.copy());
	}
}
