package ninofreno.ttfn.transaction;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.base.Preconditions;

public class Cache {

    private static final Logger LOGGER = LoggerFactory.getLogger(Cache.class);

    private static final long SLEEP_MILLIS = 1000;

    private final long duration;
    private final ConcurrentNavigableMap<Long, StatsDto> timestamps2stats;

    @Autowired
    private ObjectWriter jacksonWriter;

    public Cache(long duration) {

        this.duration = duration;
        timestamps2stats = new ConcurrentSkipListMap<>();

        new CacheCleaner().start();
    }

    public void update(TransactionDto transaction) {

        long time = transaction.getTime();
        try {
            Preconditions.checkArgument(time < System.currentTimeMillis(),
                    "Invalid transaction (timestamp refers to the future):",
                    jacksonWriter.writeValueAsString(transaction));
            if (!this.isObsolete(transaction)) {
                StatsDto stats = StatsDto.fromTransaction(transaction);
                timestamps2stats.merge(time, stats, (stats1, stats2) -> StatsDto.reduce(stats1, stats2));
                LOGGER.debug("Ingested transaction: {}", jacksonWriter.writeValueAsString(transaction));
            } else {
                LOGGER.warn("Ignoring obsolete transaction: {}", jacksonWriter.writeValueAsString(transaction));
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("Exception thrown!", e);
        }
    }

    private boolean isObsolete(TransactionDto transaction) {
        return !this.isFresh(transaction.getTime());
    }

    private boolean isFresh(long timestamp) {
        return System.currentTimeMillis() - timestamp <= duration;
    }

    private boolean isStale() {

        return !(timestamps2stats.isEmpty() || this.isFresh(timestamps2stats.firstKey()));
    }

    private void prune() {

        timestamps2stats.pollFirstEntry();
    }

    public StatsDto getStatistics() {

        return timestamps2stats.values().stream().reduce(StatsDto::reduce).orElse(StatsDto.emptyStats());
    }

    private class CacheCleaner {

        public void start() {
            Executors.newSingleThreadExecutor().submit(() -> {
                try {

                    while (true) {

                        while (Cache.this.isStale()) {
                            Cache.this.prune();
                        }

                        Thread.sleep(SLEEP_MILLIS);
                    }

                } catch (InterruptedException e) {
                    LOGGER.error("Thread interrupted!", e);
                }
            });
        }
    }

}
