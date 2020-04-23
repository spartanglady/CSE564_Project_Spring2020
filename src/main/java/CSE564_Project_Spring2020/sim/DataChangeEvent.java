package CSE564_Project_Spring2020.sim;

/**
 * The type Data change event.
 */
public class DataChangeEvent {
	private final DataType type;
	private final String value;

	/**
	 * Instantiates a new Data change event.
	 *
	 * @param _type  the type
	 * @param _value the value
	 */
	public DataChangeEvent(DataType _type, String _value) {
		type = _type;
		value = _value;
	}

	/**
	 * Gets type.
	 *
	 * @return the type
	 */
	public DataType getType() {
		return type;
	}

	/**
	 * Gets value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}
