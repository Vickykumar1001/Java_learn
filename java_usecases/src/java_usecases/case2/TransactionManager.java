package java_usecases.case2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionManager {
	public static void main(String[] args) {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction("TXN001", "RTGS", 300000.0, 1678886400000L));
		transactions.add(new Transaction("TXN002", "RTGS", 300000.0, 1678886400001L));
		transactions.add(new Transaction("TXN003", "RTGS", 300000.0, 1678886400002L));
		transactions.add(new Transaction("TXN004", "IMPS", 10000.0, 1678886400000L));
		transactions.add(new Transaction("TXN005", "IMPS", 15000.0, 1678886400000L));
		transactions.add(new Transaction("TXN006", "IMPS", 10000.0, 1678886400001L));
		transactions.add(new Transaction("TXN007", "NEFT", 50000.0, 100L));
		transactions.add(new Transaction("TXN008", "NEFT", 75000.0, 100L));
		transactions.add(new Transaction("TXN009", "IMPS", 10000.0, 101L));
		transactions.add(new Transaction("TXN010", "RTGS", 250000.0, 100L));
		transactions.add(new Transaction("TXN011", "RTGS", 250000.0, 101L));

		System.out.println("Unsorted Transactions:");
		for (Transaction t : transactions) {
			System.out.println(t);
		}

		Collections.sort(transactions, new TransactionPriorityComparator());

		System.out.println("\nSorted Transactions:");
		for (Transaction t : transactions) {
			System.out.println(t);
		}
	}
}
