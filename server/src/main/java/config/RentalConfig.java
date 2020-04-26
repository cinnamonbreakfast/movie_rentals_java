package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"repository", "service", "domain", "ui"})
public class RentalConfig {

}
