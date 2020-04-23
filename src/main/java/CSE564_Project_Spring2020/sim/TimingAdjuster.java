package CSE564_Project_Spring2020.sim;

import java.util.Optional;

/**
 * The type Timing adjuster.
 */
public class TimingAdjuster {
	private int refreshRate, ticks;
	private Optional<ClockedComponent> adjustedComponent;

	/**
	 * Instantiates a new Timing adjuster.
	 */
	public TimingAdjuster() {
		adjustedComponent = Optional.empty();
		refreshRate = 0;
		ticks = 0;
	}

	/**
	 * Sets rate.
	 *
	 * @param rate the rate
	 */
	public void setRate(int rate) {
		assert(rate > 0);
		refreshRate = rate;
	}

	/**
	 * Sets adjusted component.
	 *
	 * @param component the component
	 */
	public void setAdjustedComponent(ClockedComponent component) {
		assert(component != null);
		adjustedComponent = Optional.of(component);
	}

	/**
	 * Tick.
	 */
	public void tick() {
		assert(refreshRate > 0);
		assert(adjustedComponent.isPresent());
		++ticks;
		
		if (ticks == refreshRate) {
			adjustedComponent.get().tick();
			ticks = 0;
		}
	}
}
