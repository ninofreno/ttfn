package ninofreno.ttfn.transaction;

public class StatsDto {

    private final double min;
    private final double max;
    private final double sum;
    private final long count;

    private StatsDto(Builder builder) {

        this.min = builder.min;
        this.max = builder.max;
        this.sum = builder.sum;
        this.count = builder.count;
    }

    private StatsDto(double amount) {
        min = max = sum = amount;
        count = 1;
    }

    double getMin() {
        return min;
    }

    double getMax() {
        return max;
    }

    double getSum() {
        return sum;
    }

    double getAvg() {
        return count == 0 ? 0 : sum / count;
    }

    long getCount() {
        return count;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static StatsDto emptyStats() {
        return new Builder().build();
    }

    public static StatsDto fromTransaction(TransactionDto transaction) {
        return new StatsDto(transaction.getAmount());
    }

    public static StatsDto reduce(StatsDto stats, StatsDto otherStats) {
        return new Builder().withMin(Math.min(stats.min, otherStats.min)).withMax(Math.max(stats.max, otherStats.max))
                .withCount(stats.count + otherStats.count).build();
    }

    public static class Builder {

        private double min;
        private double max;
        private double sum;
        private long count;

        public Builder withMin(double min) {
            this.min = min;
            return this;
        }

        public Builder withMax(double max) {
            this.max = max;
            return this;
        }

        public Builder withSum(double sum) {
            this.sum = sum;
            return this;
        }

        public Builder withCount(long count) {
            this.count = count;
            return this;
        }

        public StatsDto build() {
            return new StatsDto(this);
        }
    }

}
