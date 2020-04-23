package CSE564_Project_Spring2020.sim.helpers;

import CSE564_Project_Spring2020.sim.ExperimentWorldEvent;

public class TestExperimentWorldEvent extends ExperimentWorldEvent implements Comparable<ExperimentWorldEvent> {

    public TestExperimentWorldEvent(int _startTime, int _duration, double _d_roll, double _d_pitch, double _d_yaw) {
        super(_startTime, _duration, _d_roll, _d_pitch, _d_yaw);
    }

    @Override
    public int compareTo(ExperimentWorldEvent o) {
        return startTime.compareTo(o.startTime);
    }
}
