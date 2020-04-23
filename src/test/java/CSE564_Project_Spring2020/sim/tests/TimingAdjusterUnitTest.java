package CSE564_Project_Spring2020.sim.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import CSE564_Project_Spring2020.sim.ClockedComponent;
import CSE564_Project_Spring2020.sim.TimingAdjuster;
import org.junit.jupiter.api.Test;

class TimingAdjusterUnitTest {

	@Test
	void testSetRate() {
		TimingAdjuster cut = new TimingAdjuster();
		
		cut.setRate(1);
		cut.setRate(Integer.MAX_VALUE);

		assertThrows(AssertionError.class, () -> { cut.setRate(0); });
		assertThrows(AssertionError.class, () -> { cut.setRate(-1); });
		assertThrows(AssertionError.class, () -> { cut.setRate(-2); });
	}
	
	@Test
	void testTick() {
		TimingAdjuster cut = new TimingAdjuster();
		
		assertThrows(AssertionError.class, () -> { cut.setAdjustedComponent(null); });
		
		ClockedComponent mockAdjustedComponent = mock(ClockedComponent.class);
		
		assertThrows(AssertionError.class, () -> { cut.tick(); });
		cut.setRate(2);
		assertThrows(AssertionError.class, () -> { cut.tick(); });
		cut.setAdjustedComponent(mockAdjustedComponent);
		
		cut.tick();
		verify(mockAdjustedComponent, times(0)).tick();
		cut.tick();
		verify(mockAdjustedComponent, times(1)).tick();
		
		cut.tick();
		verify(mockAdjustedComponent, times(1)).tick();
		cut.tick();
		verify(mockAdjustedComponent, times(2)).tick();
		
		cut.setRate(3);
		
		cut.tick();
		verify(mockAdjustedComponent, times(2)).tick();
		cut.tick();
		verify(mockAdjustedComponent, times(2)).tick();
		cut.tick();
		verify(mockAdjustedComponent, times(3)).tick();
	}

}
