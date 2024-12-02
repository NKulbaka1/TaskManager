package ru.kulbaka.effectivemobile.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Менеджер задач",
                description = "API менеджера задач",
                version = "1.0.0",
                contact = @Contact(
                        name = "Кульбака Никита",
                        email = "nikita.kulbaka@mail.ru"
                )
        )
)
public class OpenApiConfig {
}
