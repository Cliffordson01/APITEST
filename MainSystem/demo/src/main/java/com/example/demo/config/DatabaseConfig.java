package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class DatabaseConfig {

    @Bean
    public CommandLineRunner testDatabaseConnection(DataSource dataSource) {
        return args -> {
            try (Connection conn = dataSource.getConnection()) {
                System.out.println("✅ Conexão com o banco de dados estabelecida com sucesso!");
            } catch (Exception e) {
                System.err.println("❌ Falha na conexão com o banco de dados: " + e.getMessage());
            }
        };
    }
}