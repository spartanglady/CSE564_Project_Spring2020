package CSE564_Project_Spring2020.sim;

/**
 * The type Axis event.
 */
public class AxisEvent {
	/**
	 * The Start time.
	 */
	public final int startTime;
	/**
	 * The Duration.
	 */
	public final int duration;
	private double d_deg;

	/**
	 * Instantiates a new Axis event.
	 */
	public AxisEvent() {
		this(0, 0, 0.0d);
	}

	/**
	 * Instantiates a new Axis event.
	 *
	 * @param _startTime the start time
	 * @param _duration  the duration
	 * @param _d_deg     the d deg
	 */
	public AxisEvent(int _startTime, int _duration, double _d_deg) {
		startTime = _startTime;
		duration = _duration;
		setDeltaDegrees(_d_deg);
	}

	/**
	 * Sets delta degrees.
	 *
	 * @param _d_deg the d deg
	 */
	public void setDeltaDegrees(double _d_deg) {
		assert(!Double.isInfinite(_d_deg));
		assert(!Double.isNaN(_d_deg));
		d_deg = _d_deg;
	}

	/**
	 * Gets delta degrees.
	 *
	 * @return the delta degrees
	 */
	public double getDeltaDegrees() {
		return d_deg;
	}
}
