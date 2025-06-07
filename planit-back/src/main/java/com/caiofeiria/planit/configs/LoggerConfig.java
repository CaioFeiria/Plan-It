package com.caiofeiria.planit.configs;

import com.caiofeiria.planit.utils.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {

    @Bean
    public Logger logger() {
        return new Logger();
    }
}
