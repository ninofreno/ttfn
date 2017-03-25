package ninofreno.ttfn.transaction;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class TransactionDto implements Comparable<TransactionDto> {

    @JsonProperty("amount")
    private final double amount;

    @JsonProperty("time")
    private final long time;

    @JsonCreator
    public TransactionDto(@JsonProperty("amount") double amount, @JsonProperty("time") long time) {

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
