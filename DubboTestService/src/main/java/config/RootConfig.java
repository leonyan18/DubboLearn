package config;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yan
 */
@Configuration
@ComponentScan(basePackages = { "service","config","dao"})
@EnableDubbo(scanBasePackages = "service")
@PropertySource("classpath:dubbo-provider.properties")
public class RootConfig {
}

