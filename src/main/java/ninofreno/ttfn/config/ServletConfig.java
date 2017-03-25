package ninofreno.ttfn.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class ServletConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServletConfig.class);

    @Value("${stats.servletPort}")
    private int servletPort;

    @Bean
    EmbeddedServletContainerFactory servletContainer() {

        final TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();

        factory.setPort(servletPort);
        LOGGER.info("Set servlet port to {}.", servletPort);

        return factory;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
