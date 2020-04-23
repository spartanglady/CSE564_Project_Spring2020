package CSE564_Project_Spring2020.sim.tests;

import static CSE564_Project_Spring2020.sim.DegreeAssertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import CSE564_Project_Spring2020.sim.Degree;
import CSE564_Project_Spring2020.sim.DegreeAssertions;
import CSE564_Project_Spring2020.sim.Gyroscope;
import CSE564_Project_Spring2020.sim.World;
import org.junit.jupiter.api.Test;

class GyroscopeUnitTest {

	@Test
	void smokeTest() {
		Gyroscope cut = new Gyroscope();
		
		assertThrows(AssertionError.class, () -> { cut.tick(); });
		
		DegreeAssertions.assertEquals(new Degree(), cut.getRoll());
		assertEquals(new Degree(), cut.getPitch());
		assertEquals(new Degree(), cut.getYaw());
		
		World mockWorld = mock(World.class);

		when(mockWorld.getCurrentRoll()).thenReturn(new Degree(2.0));
		when(mockWorld.getCurrentPitch()).thenReturn(new Degree(3.0));
		when(mockWorld.getCurrentYaw()).thenReturn(new Degree(1.0));
		
		cut.setWorld(mockWorld);
		
		assertEquals(new Degree(), cut.getRoll());
		assertEquals(new Degree(), cut.getPitch());
		assertEquals(new Degree(), cut.getYaw());
		
		cut.tick();
		
		assertEquals(2.0d, cut.getRoll());
		assertEquals(3.0d, cut.getPitch());
		assertEquals(1.0d, cut.getYaw());
		
		when(mockWorld.getCurrentRoll()).thenReturn(new Degree(4.0));
		when(mockWorld.getCurrentPitch()).thenReturn(new Degree(5.0));
		when(mockWorld.getCurrentYaw()).thenReturn(new Degree(6.0));
		
		assertEquals(2.0d, cut.getRoll());
		assertEquals(3.0d, cut.getPitch());
		assertEquals(1.0d, cut.getYaw());
		
		cut.tick();

		assertEquals(4.0d, cut.getRoll());
		assertEquals(5.0d, cut.getPitch());
		assertEquals(6.0d, cut.getYaw());
	}

}
