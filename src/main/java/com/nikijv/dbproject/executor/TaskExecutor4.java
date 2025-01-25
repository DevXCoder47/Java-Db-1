package com.nikijv.dbproject.executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskExecutor4 extends TaskExecutor {

    public TaskExecutor4(Connection connection) {
        super(connection);
    }

    @Override
    public void executeTasks() throws SQLException {
        showCarsByType("sedan");
    }

    public void showCarsByManufacturer(String manufacturer) throws SQLException {
        String sqlString = """
                select id, manufacturer, name from cars
                where manufacturer like ?;
        """;
        try(PreparedStatement statement = connection.prepareStatement(sqlString)) {
            statement.setString(1, manufacturer);
            try(ResultSet resultSet = statement.executeQuery()) {
                renderResult(resultSet);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showCarsByColour(String colour) throws SQLException {
        String sqlString = """
                select id, name, colour from cars
                where colour like ?;
        """;
        try(PreparedStatement statement = connection.prepareStatement(sqlString)) {
            statement.setString(1, colour);
            try(ResultSet resultSet = statement.executeQuery()) {
                renderResult(resultSet);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showCarsByEngineCapacity(int capacity) throws SQLException {
        String sqlString = """
                select id, name, engine_capacity from cars
                where engine_capacity = ?;
        """;
        try(PreparedStatement statement = connection.prepareStatement(sqlString)) {
            statement.setInt(1, capacity);
            try(ResultSet resultSet = statement.executeQuery()) {
                renderResult(resultSet);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void showCarsByType(String type) throws SQLException {
        String sqlString = """
                select id, name, type from cars
                where type = ?::type_enum;
        """;
        try(PreparedStatement statement = connection.prepareStatement(sqlString)) {
            statement.setString(1, type);
            try(ResultSet resultSet = statement.executeQuery()) {
                renderResult(resultSet);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
