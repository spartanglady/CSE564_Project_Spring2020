package CSE564_Project_Spring2020.ui;

import javax.swing.JLabel;

import CSE564_Project_Spring2020.sim.DataChangeEvent;
import CSE564_Project_Spring2020.sim.DataListener;
import CSE564_Project_Spring2020.sim.DataType;

/**
 * The type Data screen controller.
 */
public class DataScreenController {

	/**
	 * The type World data listener.
	 */
	static class WorldDataListener implements DataListener {
		private JLabel timeLabel, rollLabel, pitchLabel, yawLabel;
		
		@Override
		public void dataChanged(DataChangeEvent e) {
			final DataType type = e.getType();

			if (type == DataType.WorldTime) {
				timeLabel.setText(e.getValue());
			}
			else if (type == DataType.WorldRoll) {
				rollLabel.setText(e.getValue());
			}
			else if (type == DataType.WorldPitch) {
				pitchLabel.setText(e.getValue());
			}
			else if (type == DataType.WorldYaw) {
				yawLabel.setText(e.getValue());
			}
		}

		/**
		 * Register time label.
		 *
		 * @param _timeLabel the time label
		 */
		public void registerTimeLabel(JLabel _timeLabel) {
			timeLabel = _timeLabel;
		}

		/**
		 * Register roll label.
		 *
		 * @param _rollLabel the roll label
		 */
		public void registerRollLabel(JLabel _rollLabel) {
			rollLabel = _rollLabel;
		}

		/**
		 * Register pitch label.
		 *
		 * @param _pitchLabel the pitch label
		 */
		public void registerPitchLabel(JLabel _pitchLabel) {
			pitchLabel = _pitchLabel;
		}

		/**
		 * Register yaw label.
		 *
		 * @param _yawLabel the yaw label
		 */
		public void registerYawLabel(JLabel _yawLabel) {
			yawLabel = _yawLabel;
		}
	}

	/**
	 * The constant worldDataListener.
	 */
	public static final WorldDataListener worldDataListener = new WorldDataListener();

	/**
	 * The type Gyro data listener.
	 */
	static class GyroDataListener implements DataListener {
		private JLabel rollLabel, pitchLabel, yawLabel;

		@Override
		public void dataChanged(DataChangeEvent e) {
			final DataType type = e.getType();

			if (type == DataType.GyroRoll) {
				rollLabel.setText(e.getValue());
			}
			else if (type == DataType.GyroPitch) {
				pitchLabel.setText(e.getValue());
			}
			else if (type == DataType.GyroYaw) {
				yawLabel.setText(e.getValue());
			}
		}

		/**
		 * Register roll label.
		 *
		 * @param _rollLabel the roll label
		 */
		public void registerRollLabel(JLabel _rollLabel) {
			rollLabel = _rollLabel;
		}

		/**
		 * Register pitch label.
		 *
		 * @param _pitchLabel the pitch label
		 */
		public void registerPitchLabel(JLabel _pitchLabel) {
			pitchLabel = _pitchLabel;
		}

		/**
		 * Register yaw label.
		 *
		 * @param _yawLabel the yaw label
		 */
		public void registerYawLabel(JLabel _yawLabel) {
			yawLabel = _yawLabel;
		}
	}

	/**
	 * The constant gyroDataListener.
	 */
	public static final GyroDataListener gyroDataListener = new GyroDataListener();

	/**
	 * The type Actuator data listener.
	 */
	static class ActuatorDataListener implements DataListener {
		private JLabel rollLabel, pitchLabel, yawLabel;

		@Override
		public void dataChanged(DataChangeEvent e) {
			final DataType type = e.getType();

			if (type == DataType.ActuatorRoll) {
				rollLabel.setText(e.getValue());
			}
			else if (type == DataType.ActuatorPitch) {
				pitchLabel.setText(e.getValue());
			}
			else if (type == DataType.ActuatorYaw) {
				yawLabel.setText(e.getValue());
			}
		}

		/**
		 * Register roll label.
		 *
		 * @param _rollLabel the roll label
		 */
		public void registerRollLabel(JLabel _rollLabel) {
			rollLabel = _rollLabel;
		}

		/**
		 * Register pitch label.
		 *
		 * @param _pitchLabel the pitch label
		 */
		public void registerPitchLabel(JLabel _pitchLabel) {
			pitchLabel = _pitchLabel;
		}

		/**
		 * Register yaw label.
		 *
		 * @param _yawLabel the yaw label
		 */
		public void registerYawLabel(JLabel _yawLabel) {
			yawLabel = _yawLabel;
		}
	}

	/**
	 * The constant actuatorDataListener.
	 */
	public static final ActuatorDataListener actuatorDataListener = new ActuatorDataListener();
}
