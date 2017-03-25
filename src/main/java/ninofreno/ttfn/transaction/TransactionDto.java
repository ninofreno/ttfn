package ninofreno.ttfn.transaction;

public class TransactionDto implements Comparable<TransactionDto> {

    private final double amount;
    private final long time;

    public TransactionDto(double amount, long time) {

        this.amount = amount;
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public long getTime() {
        return time;
    }

    @Override
    public int compareTo(TransactionDto otherTransaction) {

        return Long.compare(this.time, otherTransaction.time);
    }

}
