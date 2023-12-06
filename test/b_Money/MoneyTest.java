package b_Money;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(Integer.valueOf(10000), SEK100.getAmount());
		assertEquals(Integer.valueOf(1000), EUR10.getAmount());
		assertEquals(Integer.valueOf(20000), SEK200.getAmount());
	}

	@Test
	public void testGetCurrency() {
		assertEquals("SEK", SEK100.getCurrency().getName());
		assertEquals("EUR", EUR10.getCurrency().getName());
		assertEquals("SEK", SEK200.getCurrency().getName());
	}

	@Test
	public void testToString() {
		assertEquals("100.00 SEK", SEK100.toString());
		assertEquals("0.00 SEK", SEK0.toString());
		assertEquals("20.00 EUR", EUR20.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals(Integer.valueOf((int) (10000 * 0.15)), SEK100.universalValue());
		assertEquals(Integer.valueOf((int) (2000 * 1.5)), EUR20.universalValue());
		assertEquals(Integer.valueOf(0), SEK0.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		assertTrue(SEK0.equals(EUR0));
		assertTrue(SEK200.equals(EUR20));
		assertFalse(SEK100.equals(EUR20));
	}

	@Test
	public void testAdd() {
		assertEquals(Integer.valueOf(40000), SEK200.add(EUR20).getAmount());
		assertEquals(Integer.valueOf(1000), EUR0.add(EUR10).getAmount());
	}

	@Test
	public void testSub() {
		assertEquals(Integer.valueOf(0), SEK200.sub(EUR20).getAmount());
		assertEquals(Integer.valueOf(-1000), EUR0.sub(EUR10).getAmount());
	}

	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
		assertFalse(EUR20.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals(Integer.valueOf(-10000), SEK100.negate().getAmount());
		assertEquals(Integer.valueOf(-2000), EUR20.negate().getAmount());
		assertEquals(Integer.valueOf(10000), SEKn100.negate().getAmount());
	}

	@Test
	public void testCompareTo() {
		assertEquals(0, SEK100.compareTo(EUR10));
		assertEquals(1, SEK200.compareTo(EUR0));
		assertEquals(-1, EUR10.compareTo(SEK200));
	}
}