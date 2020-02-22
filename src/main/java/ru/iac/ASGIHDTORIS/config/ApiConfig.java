package ru.iac.ASGIHDTORIS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.iac.ASGIHDTORIS.api.parser.Parser;
import ru.iac.ASGIHDTORIS.api.parser.ParserIntoJson;

@Configuration
public class ApiConfig {

    @Bean
    public Parser api() {
        return new ParserIntoJson();
    }

}
