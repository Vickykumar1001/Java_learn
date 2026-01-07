package java_usecases.case2;


class Transaction {
	private String transactionId;
	private String transactionType;
	private double amount;
	private long timestamp;

	public Transaction(String transactionId, String transactionType, double amount, long timestamp) {
		this.transactionId = transactionId;
		this.transactionType = transactionType;
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public double getAmount() {
		return amount;
	}

	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return "ID: " + transactionId + ", Type: " + transactionType + ", Amount: " + amount + ", Time: " + timestamp;
	}
}