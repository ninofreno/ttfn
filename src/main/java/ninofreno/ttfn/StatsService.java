package ninofreno.ttfn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StatsService {

    public static void main(String[] args) {

        System.err.println("Current time ms: " + System.currentTimeMillis());
        SpringApplication.run(StatsService.class, args);
    }
}
