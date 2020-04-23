package CSE564_Project_Spring2020.sim.helpers;

import CSE564_Project_Spring2020.sim.*;

import java.util.List;

public class StabilityControlSystem {
    private Simulator simulator;
    private Experiment experiment;
    private ExperimentWorldDataListener testWorldListener = new ExperimentWorldDataListener();
    private ExperimentGyroDataListener testGyroListener = new ExperimentGyroDataListener();
    private ExperimentActuatorDataListener testActuatorListener = new ExperimentActuatorDataListener();
    private List<ExperimentWorldData> worldTable;
    private List<ExperimentGyroData> gyroTable;
    private List<ExperimentActuatorData> actuatorTable;

    public StabilityControlSystem(Experiment experiment) {
        this.experiment = experiment;
        this.testWorldListener.setGyroDataListener(testGyroListener);
        this.testWorldListener.setActuatorDataListener(testActuatorListener);
    }

    public StabilityControlSystem init() {
        simulator = new Simulator(experiment.experimentDuration());
        simulator.setGyroDelay(experiment.getDelay().gyroDelay);
        simulator.setActuatorDelay(RotationAxis.ROLL, experiment.getDelay().rollActuatorDelay);
        simulator.setActuatorDelay(RotationAxis.PITCH, experiment.getDelay().pitchActuatorDelay);
        simulator.setActuatorDelay(RotationAxis.YAW, experiment.getDelay().yawActuatorDelay);
        simulator.setWorldDataListener(testWorldListener);
        simulator.setGyroscopeDataListener(testGyroListener);
        simulator.setActuatorDataListener(testActuatorListener);

        experiment.getEvent().stream().forEach(experimentWorldEvent -> {
            simulator.getWorldEventManager().addEvent(experimentWorldEvent.startTime,
                    experimentWorldEvent.duration,
                    experimentWorldEvent.d_roll,
                    experimentWorldEvent.d_pitch,
                    experimentWorldEvent.d_yaw);
        });
        return this;
    }

    public StabilityControlSystem signalControllers() {
        ControllerType controllerType = experiment.getControllerType();
        ControllerFactory.CreateController(controllerType).ifPresent((Controller c) -> simulator.setRollController(c));
        ControllerFactory.CreateController(controllerType).ifPresent((Controller c) -> simulator.setPitchController(c));
        ControllerFactory.CreateController(controllerType).ifPresent((Controller c) -> simulator.setYawController(c));
        return this;
    }

    public StabilityControlSystem stabilize() throws InterruptedException{
        simulator.start();
        simulator.join();
        return this;
    }

    public StabilityControlSystem getGeneratedData() {
        testWorldListener.dataChanged(new DataChangeEvent(DataType.WorldTime, Long.toString(experiment.experimentDuration())));

        worldTable = testWorldListener.getTable();
        worldTable.remove(worldTable.size() - 1);

        gyroTable = testGyroListener.getTable();
        gyroTable.remove(gyroTable.size() - 1);

        actuatorTable = testActuatorListener.getTable();
        actuatorTable.remove(actuatorTable.size() - 1);
        return this;
    }

    public List<ExperimentWorldData> getWorldTable() {
        return worldTable;
    }

    public List<ExperimentGyroData> getGyroTable() {
        return gyroTable;
    }

    public List<ExperimentActuatorData> getActuatorTable() {
        return actuatorTable;
    }
}
