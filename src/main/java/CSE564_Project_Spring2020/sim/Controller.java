package CSE564_Project_Spring2020.sim;

/**
 * The interface Controller.
 */
public interface Controller extends ClockedComponent {
	/**
	 * Sets gyroscope.
	 *
	 * @param gyro the gyro
	 */
	void setGyroscope(Gyroscope gyro);

	/**
	 * Sets actuator.
	 *
	 * @param axis     the axis
	 * @param actuator the actuator
	 */
	void setActuator(RotationAxis axis, Actuator actuator);
}
