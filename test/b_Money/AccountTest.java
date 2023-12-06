package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount, testAccount2;

	// i also added another testAccount2 for testing
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));
		testAccount2 = new Account("Hans", DKK);
		testAccount2.deposit(new Money(100000, DKK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	// testing addTimedPayment(), removeTimedPayment() and timedPaymentExists()
	// methods from class Account
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("1", Integer.valueOf(12), Integer.valueOf(1), new Money(10000, SEK), SweBank,
				"Alice");
		assertTrue(testAccount.timedPaymentExists("1"));

		testAccount.removeTimedPayment("1");
		assertFalse(testAccount.timedPaymentExists("1"));
	}

	// testing TimedPayment class method tick() inside class Account
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("1", Integer.valueOf(1), Integer.valueOf(0), new Money(1000000, SEK), SweBank,
				"Alice");
		assertTrue(testAccount.timedPaymentExists("1"));

		testAccount.tick();
		testAccount.tick();
		testAccount.tick();

		assertEquals(Integer.valueOf(8000000), testAccount.getBalance().getAmount());
	}

	// testing deposit() and withdraw() methods from class Account
	// i also add deposit to this method for test deposit also
	@Test
	public void testDepositWithdraw() {
		testAccount2.withdraw(new Money(8000, SEK));
		assertEquals(Integer.valueOf(94000), testAccount2.getBalance().getAmount());
		testAccount2.deposit(new Money(6000, DKK));
		assertEquals(Integer.valueOf(100000), testAccount2.getBalance().getAmount());
	}

	// testing getBalance() method from class Account
	@Test
	public void testGetBalance() {
		assertEquals(Integer.valueOf(100000), testAccount2.getBalance().getAmount());
		assertEquals("DKK", testAccount2.getBalance().getCurrency().getName());
	}
}