package CSE564_Project_Spring2020.sim.helpers;

import CSE564_Project_Spring2020.sim.ControllerType;
import CSE564_Project_Spring2020.sim.DelaySettings;
import CSE564_Project_Spring2020.sim.ExperimentWorldData;
import CSE564_Project_Spring2020.sim.ExperimentWorldEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Experiment {
    private ControllerType controllerType;
    private List<TestExperimentWorldEvent> experimentWorldEvents = new ArrayList<>();
    private DelaySettings delaySettings;
    List<ExperimentWorldData> expectedWorldData = new ArrayList<>();

    private int init_time = 1;
    private double init_roll = 0.0;
    private double init_pitch = 0.0;
    private double init_yaw = 0.0;

    public Experiment(ControllerType controllerType) {
        this.controllerType = controllerType;
    }

    public Experiment addEvent(TestExperimentWorldEvent experimentWorldEvent) {
        experimentWorldEvents.add(experimentWorldEvent);
        return this;
    }

    public Experiment setDelay(DelaySettings delay) {
        delaySettings =  delay;
        return this;
    }

    public List<TestExperimentWorldEvent> getEvent() {
        return experimentWorldEvents;
    }

    public long experimentDuration() {
        long duration = 0;
        sortEvents();
        int lastEvent = experimentWorldEvents.size()-1;
        return duration = experimentWorldEvents.get(lastEvent).startTime + experimentWorldEvents.get(lastEvent).duration;
    }

    public DelaySettings getDelay() {
        return delaySettings;
    }

    public ControllerType getControllerType() {
        return controllerType;
    }

    private void sortEvents() {
        Comparator<ExperimentWorldEvent> compareByStartTime = (ExperimentWorldEvent e1, ExperimentWorldEvent e2) ->
                                                                e1.startTime.compareTo(e2.startTime);
        Collections.sort(experimentWorldEvents, compareByStartTime);
    }

}
