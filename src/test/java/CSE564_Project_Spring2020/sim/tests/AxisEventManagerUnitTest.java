package CSE564_Project_Spring2020.sim.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import CSE564_Project_Spring2020.sim.AxisEvent;
import CSE564_Project_Spring2020.sim.AxisEventManager;
import org.junit.jupiter.api.Test;

class AxisEventManagerUnitTest {

	@Test
	void smokeTest() {
		AxisEventManager cut = new AxisEventManager();
		
		assertTrue(cut.getActiveEvents().isEmpty());
		
		cut.addEvent(1, 2, 2.0d);
		
		assertTrue(cut.getActiveEvents().isEmpty());

		cut.tick();
		
		List<AxisEvent> activeEvents = cut.getActiveEvents();
		
		assertEquals(1, activeEvents.size());
		
		AxisEvent firstEvent = activeEvents.get(0);
		
		assertEquals(1, firstEvent.startTime);
		assertEquals(2, firstEvent.duration);
		assertEquals(2.0d, firstEvent.getDeltaDegrees());
		
		cut.tick();

		assertEquals(1, activeEvents.size());

		AxisEvent secondEvent = activeEvents.get(0);
		
		assertEquals(1, secondEvent.startTime);
		assertEquals(2, secondEvent.duration);
		assertEquals(2.0d, secondEvent.getDeltaDegrees());
		
		cut.tick();
		
		assertTrue(cut.getActiveEvents().isEmpty());
	}
	
	@Test
	public void multipleEventsTest() {
		AxisEventManager cut = new AxisEventManager();

		assertTrue(cut.getActiveEvents().isEmpty());
		
		cut.addEvent(1, 1, 11.0d);
		cut.addEvent(2, 1, 21.0d);
		cut.addEvent(2, 2, 22.0d);
		cut.addEvent(3, 1, 31.0d);
		
		cut.tick();
		
		List<AxisEvent> activeEvents = cut.getActiveEvents();
		
		assertEquals(1, activeEvents.size());

		assertEquals(1, activeEvents.get(0).startTime);
		assertEquals(1, activeEvents.get(0).duration);
		assertEquals(11.0d, activeEvents.get(0).getDeltaDegrees());
		
		cut.tick();
		
		activeEvents = cut.getActiveEvents();
		activeEvents.sort((AxisEvent lhs, AxisEvent rhs) -> {
			return Double.compare(lhs.getDeltaDegrees(), rhs.getDeltaDegrees());
		});
		
		assertEquals(2, activeEvents.size());
		
		
		assertEquals(2, activeEvents.get(0).startTime);
		assertEquals(1, activeEvents.get(0).duration);
		assertEquals(21.0d, activeEvents.get(0).getDeltaDegrees());
		
		assertEquals(2, activeEvents.get(1).startTime);
		assertEquals(2, activeEvents.get(1).duration);
		assertEquals(22.0d, activeEvents.get(1).getDeltaDegrees());
		
		cut.tick();
		
		activeEvents = cut.getActiveEvents();
		activeEvents.sort((AxisEvent lhs, AxisEvent rhs) -> {
			return Double.compare(lhs.getDeltaDegrees(), rhs.getDeltaDegrees());
		});
		
		assertEquals(2, activeEvents.size());
		
		assertEquals(2, activeEvents.get(0).startTime);
		assertEquals(2, activeEvents.get(0).duration);
		assertEquals(22.0d, activeEvents.get(0).getDeltaDegrees());
		
		assertEquals(3, activeEvents.get(1).startTime);
		assertEquals(1, activeEvents.get(1).duration);
		assertEquals(31.0d, activeEvents.get(1).getDeltaDegrees());
		
		cut.tick();

		assertTrue(cut.getActiveEvents().isEmpty());
	}
	
	@Test
	public void testPreconditions() {
		AxisEventManager cut = new AxisEventManager();

		assertThrows(AssertionError.class, () -> cut.addEvent(0, 0, Double.POSITIVE_INFINITY));
		assertThrows(AssertionError.class, () -> cut.addEvent(0, 0, Double.NEGATIVE_INFINITY));
		assertThrows(AssertionError.class, () -> cut.addEvent(0, 0, Double.NaN));
	}

}
