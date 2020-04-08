package ru.iac.ASGIHDTORIS.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${upload.path.pattern}")
    private String uploadPathPattern;
    @Value("${upload.path.pattern.table}")
    private String uploadPathPatternTable;

    @Bean
    WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerCustomizer () {
        return container -> container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/"));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/getPatternTableFile/**", "/getPatternFile/**")
                .addResourceLocations("file://" + uploadPathPatternTable, "file://" + uploadPathPattern);
    }
}