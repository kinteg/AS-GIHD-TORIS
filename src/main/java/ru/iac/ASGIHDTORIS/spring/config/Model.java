package ru.iac.ASGIHDTORIS.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.iac.ASGIHDTORIS.common.model.table.TableModelCreator;
import ru.iac.ASGIHDTORIS.common.model.table.TableModelCreatorImpl;

@Configuration
public class Model {

    @Bean
    public TableModelCreator tableModelCreator() {
        return new TableModelCreatorImpl();
    }

}
