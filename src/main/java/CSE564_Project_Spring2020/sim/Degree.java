package CSE564_Project_Spring2020.sim;

/**
 * The type Degree.
 */
public class Degree {
	private double value;

	/**
	 * The Epsilon.
	 */
	static final double epsilon = 0.0001d;

	/**
	 * Instantiates a new Degree.
	 */
	public Degree() {
		value = 0.0d;
	}

	/**
	 * Instantiates a new Degree.
	 *
	 * @param _value the value
	 */
	public Degree(double _value) {
		assert(!Double.isInfinite(_value));
		assert(!Double.isNaN(_value));
		value = _value;
		normalize();
	}

	/**
	 * Plus degree.
	 *
	 * @param _value the value
	 * @return the degree
	 */
	public Degree plus(Degree _value) {
		return plus(_value.getValue());
	}

	/**
	 * Plus degree.
	 *
	 * @param _value the value
	 * @return the degree
	 */
	public Degree plus(double _value) {
		return new Degree(value + _value);
	}

	/**
	 * Add.
	 *
	 * @param _value the value
	 */
	public void add(Degree _value) {
		add(_value.getValue());
	}

	/**
	 * Add.
	 *
	 * @param _value the value
	 */
	public void add(double _value) {
		value += _value;
		normalize();
	}

	/**
	 * Gets value.
	 *
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
	
	private void normalize() {
		if (value >= 360) {
			value %= 360;
		}
		else if (value < 0.0d) {
			value %= 360;
			
			if (value < 0.0d) {
				value += 360;
			}
			else if (value == -0.0d) {
				value = 0.0d;
			}
		}
	}

	/**
	 * Is zero boolean.
	 *
	 * @return the boolean
	 */
	public boolean isZero() {
		return Math.abs(value - 0.0d) < epsilon;
	}

	/**
	 * Copy degree.
	 *
	 * @return the degree
	 */
	public Degree copy() {
		return new Degree(value);
	}
	
	@Override
	public boolean equals(Object rhs) {
		if (rhs.getClass() == Degree.class) {
			return Math.abs(((Degree)rhs).value - value) < epsilon;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("%.4f", value);
	}
}
