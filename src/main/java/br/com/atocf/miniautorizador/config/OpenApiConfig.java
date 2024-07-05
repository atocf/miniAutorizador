package br.com.atocf.miniautorizador.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Mini Autorizador VR")
                        .description("MiniAutorizadorVR é uma aplicação Spring Boot que simula o processo de autorização de transações de Vale Refeição e Vale Alimentação, garantindo a criação de cartões, consulta de saldo e autorização de transações conforme as regras definidas")
                        .version("1.0"));
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {
            operation.addParametersItem(new io.swagger.v3.oas.models.parameters.Parameter()
                    .name("Authorization")
                    .description("Basic Auth Token")
                    .required(true)
                    .in("header")
                    .schema(new io.swagger.v3.oas.models.media.StringSchema()));
        }));
    }
}