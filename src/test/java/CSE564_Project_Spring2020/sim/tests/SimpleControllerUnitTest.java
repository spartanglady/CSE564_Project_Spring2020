package CSE564_Project_Spring2020.sim.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;

import CSE564_Project_Spring2020.sim.*;
import org.junit.jupiter.api.Test;

class SimpleControllerUnitTest {
	
	@Test
	void smokeTest() {
		SimpleController cut = new SimpleController();
		
		Gyroscope mockGyroscope = mock(Gyroscope.class);
		Actuator mockRollActuator = mock(Actuator.class);
		Actuator mockPitchActuator = mock(Actuator.class);
		Actuator mockYawActuator = mock(Actuator.class);
		
		cut.setGyroscope(mockGyroscope);
		
		when(mockGyroscope.getRoll()).thenReturn(new Degree());
		when(mockGyroscope.getPitch()).thenReturn(new Degree());
		when(mockGyroscope.getYaw()).thenReturn(new Degree());
		
		cut.setActuator(RotationAxis.ROLL, mockRollActuator);
		
		cut.tick();
		
		verify(mockRollActuator, times(1)).rotate(Double.valueOf(0.0d));
		verify(mockPitchActuator, never()).rotate(any());
		verify(mockYawActuator, never()).rotate(any());
		
		when(mockGyroscope.getRoll()).thenReturn(new Degree(20d));
		when(mockGyroscope.getPitch()).thenReturn(new Degree(140d));
		when(mockGyroscope.getYaw()).thenReturn(new Degree(260d));
		
		cut.tick();
		
		verify(mockRollActuator, times(1)).rotate(Double.valueOf(-20d));
		verify(mockPitchActuator, never()).rotate(any());
		verify(mockYawActuator, never()).rotate(any());

		
		when(mockGyroscope.getRoll()).thenReturn(new Degree());
		
		verify(mockRollActuator, times(2)).rotate(any());
		verify(mockPitchActuator, never()).rotate(any());
		verify(mockYawActuator, never()).rotate(any());
		
		cut.setActuator(RotationAxis.PITCH, mockPitchActuator);
		
		cut.tick();
		
		verify(mockRollActuator, times(2)).rotate(any());
		verify(mockPitchActuator, times(1)).rotate(Double.valueOf(-140d));
		verify(mockYawActuator, never()).rotate(any());

		cut.setActuator(RotationAxis.YAW, mockYawActuator);
		
		cut.tick();

		verify(mockRollActuator, times(2)).rotate(any());
		verify(mockPitchActuator, times(1)).rotate(any());
		verify(mockYawActuator, times(1)).rotate(Double.valueOf(-260d));
		verify(mockYawActuator, times(1)).rotate(any());
	}
		
	@Test
	void testPreconditions() {
		SimpleController cut = new SimpleController();

		assertThrows(AssertionError.class, () -> cut.setGyroscope(null));
		assertThrows(AssertionError.class, () -> cut.setActuator(RotationAxis.ROLL, null));
		assertThrows(AssertionError.class, () -> cut.setActuator(null, mock(Actuator.class)));
	}

}
