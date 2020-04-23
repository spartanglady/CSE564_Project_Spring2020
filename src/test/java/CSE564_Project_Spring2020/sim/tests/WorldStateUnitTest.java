package CSE564_Project_Spring2020.sim.tests;

import static CSE564_Project_Spring2020.sim.DegreeAssertions.assertEquals;

import CSE564_Project_Spring2020.sim.WorldState;
import org.junit.jupiter.api.Test;

class WorldStateUnitTest {

	@Test
	void smokeTest() {
		WorldState cut = new WorldState();
		
		assertEquals(0.0d, cut.getCurrentDegrees());
		
		cut.degreeChanged(4.1d);
		
		assertEquals(4.1d, cut.getCurrentDegrees());
		
		cut.degreeChanged(-2.0d);
		
		assertEquals(2.1d, cut.getCurrentDegrees());
		
		cut.degreeChanged(361d);
		
		assertEquals(3.1d, cut.getCurrentDegrees());
		
		cut.degreeChanged(-4.0d);
		
		assertEquals(359.1d, cut.getCurrentDegrees());
	}

}
