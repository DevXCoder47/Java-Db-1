package com.nikijv.dbproject.executor;

import java.sql.*;

public class TaskExecutor2 extends TaskExecutor {

    public TaskExecutor2(Connection connection) {
        super(connection);
    }

    @Override
    public void executeTasks() throws SQLException {
        showCarsCountGroupedByManufacturer();
    }

    private void showManufacturers() throws SQLException {
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id, manufacturer from cars")) {
            renderResult(resultSet);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showCarsCountGroupedByManufacturer() throws SQLException {
        String sqlString = """
            select manufacturer, count(name) from cars
            group by manufacturer;
            """;
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlString)) {
            renderResult(resultSet);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showTable() throws SQLException {
        try(Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from cars")) {
            renderResult(resultSet);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
