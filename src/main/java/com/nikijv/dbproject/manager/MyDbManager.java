package com.nikijv.dbproject.manager;

import com.nikijv.dbproject.executor.*;
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

    public void createAutosTable() {
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
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void generateInitialValues() {
        try(Statement statement = connection.createStatement()) {
            String createValuesSql = """
                    INSERT INTO cars (id, manufacturer, name, engine_capacity, production_year, colour, type)
                    VALUES
                        (1, 'Toyota', 'Corolla', 1, 2020, 'White', 'sedan'),
                        (2, 'Volkswagen', 'Golf', 1, 2019, 'Blue', 'hatchback'),
                        (3, 'Ford', 'Focus', 2, 2021, 'Red', 'station wagon'),
                        (4, 'Honda', 'Civic', 3, 2022, 'Black', 'sedan'),
                        (5, 'Hyundai', 'i30', 1, 2018, 'Silver', 'hatchback'),
                        (6, 'BMW', '3 Series Touring', 2, 2023, 'Gray', 'station wagon'),
                        (7, 'Mazda', '6', 2, 2021, 'Red', 'sedan'),
                        (8, 'Honda', 'Jazz', 2, 2021, 'Red', 'sedan'),
                        (9, 'Volkswagen', 'Passat', 2, 2020, 'White', 'station wagon'),
                        (10, 'Ford', 'Mondeo', 1, 2018, 'Silver', 'sedan'),
                        (11, 'Honda', 'Jazz', 3, 2019, 'Blue', 'hatchback'),
                        (12, 'Hyundai', 'Elantra', 1, 2021, 'Gray', 'sedan'),
                        (13, 'BMW', 'X1', 3, 2023, 'Black', 'station wagon'),
                        (14, 'Mazda', 'CX-5', 2, 2022, 'Red', 'hatchback');
            """;
            statement.executeUpdate(createValuesSql);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void executeTasks() throws SQLException {
        try {
//            TaskExecutor taskExecutor = new TaskExecutor2(connection);
//            TaskExecutor taskExecutor = new TaskExecutor3(connection);
//            TaskExecutor taskExecutor = new TaskExecutor4(connection);
            TaskExecutor taskExecutor = new TaskExecutor5(connection);
            taskExecutor.executeTasks();
        }
        catch(SQLException e) {
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
