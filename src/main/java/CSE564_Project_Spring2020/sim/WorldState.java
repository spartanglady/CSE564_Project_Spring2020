package CSE564_Project_Spring2020.sim;

/**
 * The type World state.
 */
public class WorldState {
	private final Degree currentDegrees;

	/**
	 * Instantiates a new World state.
	 */
	public WorldState() {
		currentDegrees = new Degree();
	}

	/**
	 * Degree changed.
	 *
	 * @param d_deg the d deg
	 */
	public void degreeChanged(double d_deg) {
		currentDegrees.add(d_deg);
	}

	/**
	 * Gets current degrees.
	 *
	 * @return the current degrees
	 */
	public Degree getCurrentDegrees() {
		return currentDegrees;
	}
}
