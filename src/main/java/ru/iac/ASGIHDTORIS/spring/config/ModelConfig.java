package ru.iac.ASGIHDTORIS.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.iac.ASGIHDTORIS.common.model.data.DataModelCreator;
import ru.iac.ASGIHDTORIS.common.model.data.impl.DataModelCreatorImpl;
import ru.iac.ASGIHDTORIS.common.model.table.TableModelCreator;
import ru.iac.ASGIHDTORIS.common.model.table.impl.TableModelCreatorImpl;

@Configuration
public class ModelConfig {

    @Bean
    public DataModelCreator dataModelCreator() {
        return new DataModelCreatorImpl();
    }

    @Bean
    public TableModelCreator tableModelCreator() {
        return new TableModelCreatorImpl();
    }

}
