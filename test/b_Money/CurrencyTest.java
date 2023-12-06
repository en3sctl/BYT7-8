package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;

	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("DKK", DKK.getName());
		assertEquals("SEK", SEK.getName());
	}

	@Test
	public void testGetRate() {
		assertEquals(Double.valueOf(0.15), SEK.getRate());
		assertEquals(Double.valueOf(1.5), EUR.getRate());
	}

	@Test
	public void testSetRate() {
		SEK.setRate(0.25);
		assertEquals(Double.valueOf(0.25), SEK.getRate());
		SEK.setRate(0.15);
		assertEquals(Double.valueOf(0.15), SEK.getRate());
	}

	@Test
	public void testGlobalValue() {
		assertEquals(Integer.valueOf((int) (120 * 0.15)), SEK.universalValue(120));
		assertEquals(Integer.valueOf((int) (120 * 1.5)), EUR.universalValue(120));
	}

	@Test
	public void testValueInThisCurrency() {
		assertEquals(Integer.valueOf(1000), SEK.valueInThisCurrency(100, EUR));
		assertEquals(Integer.valueOf(750), DKK.valueInThisCurrency(100, EUR));
	}

}