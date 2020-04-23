package CSE564_Project_Spring2020.sim;

public class ExperimentWorldEvent {
	public Integer startTime;
	public Integer duration;
	public double d_roll, d_pitch, d_yaw;
	
	public ExperimentWorldEvent(int _startTime, int _duration, double _d_roll, double _d_pitch, double _d_yaw) {
		startTime = _startTime;
		duration = _duration;
		d_roll = _d_roll;
		d_pitch = _d_pitch;
		d_yaw = _d_yaw;
	}
}
