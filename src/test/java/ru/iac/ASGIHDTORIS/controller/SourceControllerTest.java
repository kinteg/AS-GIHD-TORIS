package ru.iac.ASGIHDTORIS.controller;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.iac.ASGIHDTORIS.spring.controller.SourceController;
import ru.iac.ASGIHDTORIS.spring.domain.Source;

import java.util.Random;

@EnableAspectJAutoProxy
@SpringBootTest
public class SourceControllerTest {

    @Autowired
    private SourceController sourceController;


    @Test
    public void createSource() {
        Source source = sourceBuilder();

        System.out.println(sourceController.createSource(source));

    }


    private Source sourceBuilder() {

        RandomStringGenerator generator =
                new RandomStringGenerator
                        .Builder()
                        .withinRange('a', 'z')
                        .build();

        return Source.builder()
                .id(new Random().nextLong())
                .name(generator.generate(10))
                .longName(generator.generate(10))
                .shortName(generator.generate(10))
                .description(generator.generate(10))
                .addDescription(generator.generate(10))
                .scope(generator.generate(10))
                .periodicity(generator.generate(10))
                .renewalPeriod(generator.generate(10))
                .type(generator.generate(10))
                .tags(generator.generate(10))
                .providerLink(generator.generate(10))
                .dataSource(generator.generate(10))
                .isArchive(false)
                .build();
    }

}
