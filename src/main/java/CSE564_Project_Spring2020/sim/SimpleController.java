package CSE564_Project_Spring2020.sim;

/**
 * The type Simple controller.
 */
@SuppressWarnings("DuplicatedCode")
public class SimpleController implements Controller {
	private Gyroscope gyro;
	private Actuator actuator;
	private RotationAxis axis;

	/**
	 * Instantiates a new Simple controller.
	 */
	public SimpleController() {
		gyro = null;
		actuator = null;
		axis = null;
	}
	
	@Override
	public void tick() {
		assert(gyro != null);
		assert(actuator != null);
		assert(axis != null);
		
		Degree deg = new Degree();
		
		if (axis == RotationAxis.ROLL) {
			deg = gyro.getRoll();
		}
		else if (axis == RotationAxis.PITCH) {
			deg = gyro.getPitch();
		}
		else if (axis == RotationAxis.YAW) {
			deg = gyro.getYaw();
		}
		
		if (!deg.isZero()) {
			actuator.rotate(-1 * deg.getValue());
		}
		else {
			actuator.rotate(0.0d);
		}
	}

	@Override
	public void setGyroscope(Gyroscope _gyro) {
		assert(_gyro != null);
		gyro = _gyro;
	}

	@Override
	public void setActuator(RotationAxis _axis, Actuator _actuator) {
		assert(_actuator != null);
		assert(_axis != null);
		actuator = _actuator;
		axis = _axis;
	}
}
