package com.example.conflicttracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseTest implements CommandLineRunner {

    private final DataSource dataSource;

    public DatabaseTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("✅ Conexión correcta a PostgreSQL!");
            System.out.println("Versión: " + conn.getMetaData().getDatabaseProductVersion());
        } catch (Exception e) {
            System.out.println("❌ Error conectando a PostgreSQL");
            e.printStackTrace();
        }
    }
}
