package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;

	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	// testing getName() method from class Bank
	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
	}

	// testing getCurrency() method from class Bank
	@Test
	public void testGetCurrency() {
		assertEquals("SEK", SweBank.getCurrency().getName());
		assertEquals("DKK", DanskeBank.getCurrency().getName());
	}

	// testing openAccount() method from class Bank
	@Test(expected = AccountExistsException.class)
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		SweBank.openAccount("testAcc1");
		// AccountExistsException expected to be thrown as account with id "Ulrika"
		// already exists
		SweBank.openAccount("Ulrika");
	}

	// testing deposit() method from class Bank
	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		assertEquals(Integer.valueOf(0), SweBank.getBalance("Ulrika"));
		SweBank.deposit("Ulrika", new Money(1000000, SEK));
		assertEquals(Integer.valueOf(1000000), SweBank.getBalance("Ulrika"));
	}

	// testing withdraw() method from class Bank
	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(1000000, SEK));
		assertEquals(Integer.valueOf(1000000), SweBank.getBalance("Ulrika"));
		SweBank.withdraw("Ulrika", new Money(1000000, SEK));
		assertEquals(Integer.valueOf(0), SweBank.getBalance("Ulrika"));
	}

	// testing getBalance() method from class Bank
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals(Integer.valueOf(0), SweBank.getBalance("Ulrika"));
		assertEquals(Integer.valueOf(0), Nordea.getBalance("Bob"));
		Nordea.deposit("Bob", new Money(20000, SEK));
		assertEquals(Integer.valueOf(20000), Nordea.getBalance("Bob"));
	}

	// testing transfer() method from class Bank
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		assertEquals(Integer.valueOf(0), SweBank.getBalance("Ulrika"));
		assertEquals(Integer.valueOf(0), Nordea.getBalance("Bob"));

		SweBank.deposit("Ulrika", new Money(1000000, SEK));
		SweBank.transfer("Ulrika", Nordea, "Bob", new Money(500000, SEK));
		assertEquals(Integer.valueOf(500000), SweBank.getBalance("Ulrika"));
		assertEquals(Integer.valueOf(500000), Nordea.getBalance("Bob"));

		SweBank.transfer("Ulrika", "Bob", new Money(250000, SEK));
		assertEquals(Integer.valueOf(250000), SweBank.getBalance("Ulrika"));
		assertEquals(Integer.valueOf(250000), SweBank.getBalance("Bob"));
	}

	// testing how timed payment works in class Bank
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		DanskeBank.deposit("Gertrud", new Money(2500000, DKK));
		assertEquals(Integer.valueOf(2500000), DanskeBank.getBalance("Gertrud"));
		DanskeBank.addTimedPayment("Gertrud", "1", Integer.valueOf(3), Integer.valueOf(1), new Money(500000, DKK),
				Nordea, "Bob");

		int count = 0;
		while (count < 8) {
			count++;
			DanskeBank.tick();
		}

		assertEquals(Integer.valueOf(1500000), DanskeBank.getBalance("Gertrud"));
		assertEquals(Integer.valueOf(1333332), Nordea.getBalance("Bob"));
	}
}