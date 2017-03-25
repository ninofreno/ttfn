package ninofreno.ttfn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import ninofreno.ttfn.transaction.Cache;

@Configuration
@Import(ServletConfig.class)
public class StatsConfig {

    // private static final Logger LOGGER =
    // LoggerFactory.getLogger(StatsConfig.class);

    @Value("${transactions.cacheDuration}")
    private long duration;

    @Bean
    public Cache cache() {
        return new Cache(duration);
    }

}
