package com.example.hair_salon.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Salonu Fryzjerskiego")
                        .version("1.0.0")
                        .description("Dokumentacja dla API salonu fryzjerskiego z potencjałem."));
    }
}
