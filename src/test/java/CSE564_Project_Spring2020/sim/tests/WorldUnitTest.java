package CSE564_Project_Spring2020.sim.tests;

import static CSE564_Project_Spring2020.sim.DegreeAssertions.assertEquals;

import CSE564_Project_Spring2020.sim.World;
import org.junit.jupiter.api.Test;

class WorldUnitTest {
	
	@Test
	void smokeTest() {
		World cut = new World();
		
		assertEquals(0.0d, cut.getCurrentRoll());
		assertEquals(0.0d, cut.getCurrentPitch());
		assertEquals(0.0d, cut.getCurrentYaw());
		
		cut.rollChanged(3.0d);
		assertEquals(3.0d, cut.getCurrentRoll());
		assertEquals(0.0d, cut.getCurrentPitch());
		assertEquals(0.0d, cut.getCurrentYaw());
		
		cut.pitchChanged(-1.0d);
		assertEquals(3.0d, cut.getCurrentRoll());
		assertEquals(359.0d, cut.getCurrentPitch());
		assertEquals(0.0d, cut.getCurrentYaw());
		
		cut.yawChanged(365);
		assertEquals(3.0d, cut.getCurrentRoll());
		assertEquals(359.0d, cut.getCurrentPitch());
		assertEquals(5.0d, cut.getCurrentYaw());
	}

}
