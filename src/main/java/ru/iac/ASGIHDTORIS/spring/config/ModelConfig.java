package ru.iac.ASGIHDTORIS.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.iac.ASGIHDTORIS.common.model.data.DataModelCreator;
import ru.iac.ASGIHDTORIS.common.model.data.DataModelCreatorImpl;

@Configuration
public class ModelConfig {

    @Bean
    public DataModelCreator dataModelCreator() {
        return new DataModelCreatorImpl();
    }

}
