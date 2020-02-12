package ru.iac.ASGIHDTORIS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.iac.ASGIHDTORIS.api.ParserApi;
import ru.iac.ASGIHDTORIS.api.impl.ParserApiImpl;

@Configuration
public class ApiConfig {

    @Bean
    public ParserApi api() {
        return new ParserApiImpl();
    }

}
