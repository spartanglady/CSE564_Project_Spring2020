package CSE564_Project_Spring2020.sim;

import java.util.Optional;

/**
 * The type World.
 */
public class World {
	private final WorldState rollState;
	private final WorldState pitchState;
	private final WorldState yawState;
	private Optional<DataListener> worldStateListener;

	/**
	 * Instantiates a new World.
	 */
	public World() {
		rollState = new WorldState();
		pitchState = new WorldState();
		yawState = new WorldState();
		
		worldStateListener = Optional.empty();
	}

	/**
	 * Roll changed.
	 *
	 * @param d_deg the d deg
	 */
	public void rollChanged(double d_deg) {
		rollState.degreeChanged(d_deg);

		worldStateListener.ifPresent(
			(DataListener l) -> {
				final String currentRoll = rollState.getCurrentDegrees().toString();
				final DataChangeEvent e = new DataChangeEvent(DataType.WorldRoll, currentRoll);
				l.dataChanged(e);
			}
		);
	}

	/**
	 * Pitch changed.
	 *
	 * @param d_deg the d deg
	 */
	public void pitchChanged(double d_deg) {
		pitchState.degreeChanged(d_deg);

		worldStateListener.ifPresent(
			(DataListener l) -> {
				final String currentPitch = pitchState.getCurrentDegrees().toString();
				final DataChangeEvent e = new DataChangeEvent(DataType.WorldPitch, currentPitch);
				l.dataChanged(e);
			}
		);
	}
	
	public void yawChanged(double d_deg) {
		yawState.degreeChanged(d_deg);
		
		worldStateListener.ifPresent(
			(DataListener l) -> {
				final String currentYaw = yawState.getCurrentDegrees().toString();
				final DataChangeEvent e = new DataChangeEvent(DataType.WorldYaw, currentYaw);
				l.dataChanged(e);
			}
		);
	}

	/**
	 * Gets current roll.
	 *
	 * @return the current roll
	 */
	public Degree getCurrentRoll() {
		return rollState.getCurrentDegrees();
	}

	/**
	 * Gets current pitch.
	 *
	 * @return the current pitch
	 */
	public Degree getCurrentPitch() {
		return pitchState.getCurrentDegrees();
	}

	/**
	 * Gets current yaw.
	 *
	 * @return the current yaw
	 */
	public Degree getCurrentYaw() {
		return yawState.getCurrentDegrees();
	}

	/**
	 * Sets state listener.
	 *
	 * @param l the l
	 */
	public void setStateListener(DataListener l) {
		assert(l != null);
		worldStateListener = Optional.of(l);
	}
}
