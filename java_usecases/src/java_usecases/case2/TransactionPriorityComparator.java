package java_usecases.case2;

import java.util.Comparator;

class TransactionPriorityComparator implements Comparator<Transaction> {
	@Override
	public int compare(Transaction t1, Transaction t2) {
		if (getPriority(t2.getTransactionType()) - getPriority(t1.getTransactionType()) != 0) {
			return getPriority(t2.getTransactionType()) - getPriority(t1.getTransactionType());
		}

		int amountComparison = Double.compare(t2.getAmount(), t1.getAmount());
		if (amountComparison != 0) {
			return amountComparison;
		}

		return Long.compare(t1.getTimestamp(), t2.getTimestamp());
	}

	private int getPriority(String transactionType) {
		switch (transactionType) {
		case "RTGS":
			return 3;
		case "IMPS":
			return 2;
		case "NEFT":
			return 1;
		default:
			return 0;
		}
	}
}