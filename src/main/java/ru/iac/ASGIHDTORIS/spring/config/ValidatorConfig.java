package ru.iac.ASGIHDTORIS.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.common.validator.domain.PatternValidator;
import ru.iac.ASGIHDTORIS.common.validator.domain.SourceValidator;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;

@Configuration
public class ValidatorConfig {

    private final SourceRepo sourceRepo;

    public ValidatorConfig(SourceRepo sourceRepo) {
        this.sourceRepo = sourceRepo;
    }

    @Bean
    public Validator<Source> getSourceValidator() {
        return new SourceValidator();
    }

    @Bean
    public Validator<Pattern> getPatternValidator() {
        return new PatternValidator(sourceRepo);
    }

}
