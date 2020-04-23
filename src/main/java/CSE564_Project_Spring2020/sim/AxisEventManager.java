package CSE564_Project_Spring2020.sim;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Axis event manager.
 */
public class AxisEventManager implements ClockedComponent {
	private final Queue<AxisEvent> inactive;
	private List<AxisEvent> active;
	private int currentTime;

	/**
	 * Instantiates a new Axis event manager.
	 */
	public AxisEventManager() {
		inactive = new PriorityQueue<>(Comparator.comparingInt((AxisEvent lhs) -> lhs.startTime));
		active = new LinkedList<>();
		currentTime = 0;
	}

	/**
	 * Gets active events.
	 *
	 * @return the active events
	 */
	public List<AxisEvent> getActiveEvents() {
		return active;
	}

	/**
	 * Add event.
	 *
	 * @param time     the time
	 * @param duration the duration
	 * @param d_deg    the d deg
	 */
	public void addEvent(int time, int duration, double d_deg) {
		inactive.add(new AxisEvent(time, duration, d_deg));
	}
	
	@Override
	public void tick() {
		++currentTime;
		active = active.stream()
					.filter((AxisEvent e) -> currentTime - (e.startTime + e.duration) < 0)
					.collect(Collectors.toCollection(LinkedList::new));
		
		while (!inactive.isEmpty() && inactive.peek().startTime <= currentTime) {
			active.add(inactive.poll());
		}
	}
}
