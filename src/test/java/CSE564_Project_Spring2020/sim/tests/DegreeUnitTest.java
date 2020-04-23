package CSE564_Project_Spring2020.sim.tests;

import static CSE564_Project_Spring2020.sim.DegreeAssertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import CSE564_Project_Spring2020.sim.Degree;
import org.junit.jupiter.api.Test;

class DegreeUnitTest {

	@Test
	void smokeTest() {
		Degree cut = new Degree();
		
		assertEquals(0.0, cut);
		
		cut = new Degree(2.0);
		
		assertEquals(2.0, cut);
		
		cut = new Degree(360.0);
		
		assertEquals(0.0, cut);
		
		cut = new Degree(361.0);
		
		assertEquals(1.0, cut);
		
		cut = new Degree(362.0);
		
		assertEquals(2.0, cut);

		cut = new Degree(719.0);
		
		assertEquals(359.0, cut);
		
		cut = new Degree(720.0);
		
		assertEquals(0.0, cut);
		
		cut = new Degree(721.0);
		
		assertEquals(1.0, cut);
		
		cut = new Degree(-1.0);
		
		assertEquals(359.0, cut);
		
		cut = new Degree(-2.0);
		
		assertEquals(358.0, cut);
		
		cut = new Degree(-360.0);
		
		assertEquals(0.0, cut);
		
		cut = new Degree(-361.0);
		
		assertEquals(359.0, cut);
	}
	
	@Test
	void precisionTest() {
		Degree cut = new Degree(0.01d);
		
		assertEquals(0.01d, cut);
		
		cut = new Degree(360.1);
		
		assertEquals(0.1d, cut);
		
		cut = new Degree(361.12);
		
		assertEquals(1.12d, cut);
		
		cut = new Degree(-721.003d);
		
		assertEquals(358.997d, cut);
	}
	
	@Test
	void addTest() {
		Degree cut = new Degree();
		
		assertEquals(0.0d, cut);
		
		cut.add(new Degree(5.0d));
		
		assertEquals(5.0d, cut);
		
		cut.add(354.0d);
		
		assertEquals(359.0d, cut);
		
		cut.add(1.0d);
		
		assertEquals(0.0d, cut);
	}
	
	@Test
	void addNegativeNumbersTest() {
		Degree cut = new Degree();
		
		assertEquals(0.0d, cut);
		
		cut.add(new Degree(-5.0d));
		
		assertEquals(355.0d, cut);
		
		cut.add(-5.0d);
		
		assertEquals(350.0d, cut);
		
		cut.add(-350.0d);
		
		assertEquals(0.0d, cut);
	}

	@Test
	void plusTest() {
		Degree cut = new Degree();
		
		assertEquals(0.0d, cut);
		
		assertEquals(5.0d, cut.plus(new Degree(5.0d)));
		
		assertEquals(359.0d, cut.plus(359));
		
		assertEquals(0.0d, cut.plus(360));
	}
	
	@Test
	void plusNegativeNumbersTest() {
		Degree cut = new Degree();
		
		assertEquals(0.0d, cut);
		
		assertEquals(355.0d, cut.plus(new Degree(-5.0d)));
		
		assertEquals(350.0d, cut.plus(-10d));
		
		assertEquals(0.0d, cut.plus(-360d));
	}
	
	@Test
	void testPreconditions() {
		assertThrows(AssertionError.class, () -> new Degree(Double.POSITIVE_INFINITY));
		assertThrows(AssertionError.class, () -> new Degree(Double.NEGATIVE_INFINITY));
		assertThrows(AssertionError.class, () -> new Degree(Double.NaN));
	}
}
