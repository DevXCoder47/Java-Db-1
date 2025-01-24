package com.nikijv.dbproject.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDbManager implements AutoCloseable {

    private Connection connection;

    public MyDbManager(String url, String username, String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void createAutosTable(){
        try(Statement statement = connection.createStatement()) {
            String checkTypeSql = """
            DO $$
            BEGIN
                IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'type_enum') THEN
                    CREATE TYPE type_enum AS ENUM ('sedan', 'hatchback', 'station wagon');
                END IF;
            END $$;
        """;
            statement.execute(checkTypeSql);
            String createTableSql = """
            CREATE TABLE IF NOT EXISTS cars (
                id SERIAL PRIMARY KEY,
                manufacturer VARCHAR(50) NOT NULL,
                name VARCHAR(50) NOT NULL,
                engine_capacity INT NOT NULL,
                production_year INT NOT NULL,
                colour VARCHAR(50) NOT NULL,
                type type_enum NOT NULL
            );
            """;
            statement.executeUpdate(createTableSql);
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        try {
            if(connection != null && !connection.isClosed())
                connection.close();
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
