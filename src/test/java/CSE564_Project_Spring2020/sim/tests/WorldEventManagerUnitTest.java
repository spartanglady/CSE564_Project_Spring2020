package CSE564_Project_Spring2020.sim.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import CSE564_Project_Spring2020.sim.World;
import CSE564_Project_Spring2020.sim.WorldEventManager;
import org.junit.jupiter.api.Test;

class WorldEventManagerUnitTest {

	@Test
	void smokeTest() {
		WorldEventManager cut = new WorldEventManager();
		
		assertThrows(AssertionError.class, () -> { cut.tick(); });
		
		World mockWorld = mock(World.class);
		
		cut.setWorld(mockWorld);
		
		cut.tick();
		
		verify(mockWorld, never()).rollChanged(anyDouble());
		verify(mockWorld, never()).pitchChanged(anyDouble());
		verify(mockWorld, never()).yawChanged(anyDouble());
		
		cut.addEvent(2, 2, 1.0, 2.0, 3.0);
		
		cut.tick();
		
		verify(mockWorld, times(1)).rollChanged(1.0d);
		verify(mockWorld, times(1)).pitchChanged(2.0d);
		verify(mockWorld, times(1)).yawChanged(3.0d);
		
		cut.tick();
		
		verify(mockWorld, times(2)).rollChanged(1.0d);
		verify(mockWorld, times(2)).pitchChanged(2.0d);
		verify(mockWorld, times(2)).yawChanged(3.0d);
		
		cut.tick();
		
		verify(mockWorld, times(2)).rollChanged(anyDouble());
		verify(mockWorld, times(2)).pitchChanged(anyDouble());
		verify(mockWorld, times(2)).yawChanged(anyDouble());
	}
	
	@Test
	public void testPreconditions() {
		WorldEventManager cut = new WorldEventManager();
		cut.addEvent(2, 2, 1.0, 2.0, 3.0);
		assertThrows(AssertionError.class, () -> cut.addEvent(2, 2, Double.POSITIVE_INFINITY, 2.0, 3.0));
		assertThrows(AssertionError.class, () -> cut.addEvent(2, 2, Double.NEGATIVE_INFINITY, 2.0, 3.0));
		assertThrows(AssertionError.class, () -> cut.addEvent(2, 2, Double.NaN, 2.0, 3.0));
		assertThrows(AssertionError.class, () -> cut.addEvent(2, 2, 1.0d, Double.POSITIVE_INFINITY, 3.0));
		assertThrows(AssertionError.class, () -> cut.addEvent(2, 2, 1.0d, Double.NEGATIVE_INFINITY, 3.0));
		assertThrows(AssertionError.class, () -> cut.addEvent(2, 2, 1.0d, Double.NaN, 3.0));
		assertThrows(AssertionError.class, () -> cut.addEvent(2, 2, 1.0d, 2.0d, Double.POSITIVE_INFINITY));
		assertThrows(AssertionError.class, () -> cut.addEvent(2, 2, 1.0d, 2.0d, Double.NEGATIVE_INFINITY));
		assertThrows(AssertionError.class, () -> cut.addEvent(2, 2, 1.0d, 2.0d, Double.NaN));
	}

}
