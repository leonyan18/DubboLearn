package config;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@EnableDubbo(scanBasePackages = "controller")
@DubboComponentScan(basePackages = {"controller"})
@PropertySource("classpath:dubbo-consumer.properties")
public class DubboConfig {
}
