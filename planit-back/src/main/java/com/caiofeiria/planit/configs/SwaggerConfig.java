package com.caiofeiria.planit.configs;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
        		.info(new Info()
                        .title("PlanIt API")
                        .version("1.0.0")
                        .description("""
                            **PlanIt** é uma API REST para gerenciar _projetos_, _usuários_ e _tarefas_.
                            <br/>
                            Permite criar, listar, atualizar e deletar projetos, atribuir responsáveis,
                            filtrar tarefas por prazo e prioridade, e muito mais.
                            """)
                        .termsOfService("https://example.com/terms")
                        .contact(new Contact()
                            .name("Caio Feiria")
                            .email("caio.feiria@example.com"))
                        .license(new License()
                            .name("Apache 2.0")
                            .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                    )
                    // --- Agrupamento de endpoints por tag ---
                    .tags(List.of(
                        new Tag()
                            .name("Projetos")
                            .description("Operações de CRUD para Projetos"),
                        new Tag()
                            .name("Usuários")
                            .description("Operações de CRUD para Usuários"),
                        new Tag()
                            .name("Tarefas")
                            .description("Operações de CRUD para Tarefas")
                    ))
                    
                    // --- Documentação externa adicional (opcional) ---
                    .externalDocs(new ExternalDocumentation()
                        .description("Repositório no GitHub")
                        .url("https://github.com/caiofeiria/planit-api"));
            }
}
