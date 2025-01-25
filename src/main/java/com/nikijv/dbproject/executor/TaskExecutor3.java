package com.nikijv.dbproject.executor;

import java.sql.*;

public class TaskExecutor3 extends TaskExecutor {

    public TaskExecutor3(Connection connection) {
        super(connection);
    }

    @Override
    public void executeTasks() throws SQLException {
        showCarsByProductionYearRange(2020, 2023);
    }

    private void showTheMostProductiveManufacturer() throws SQLException {
        String sqlString = """
                SELECT manufacturer, COUNT(name) AS car_count
                FROM cars
                GROUP BY manufacturer
                HAVING COUNT(name) = (
                    SELECT MAX(car_count)
                    FROM (
                             SELECT COUNT(name) AS car_count
                             FROM cars
                             GROUP BY manufacturer
                         ) AS subquery
                );
        """;
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlString)) {
            renderResult(resultSet);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showCarsByProductionYearRange(int startYear, int endYear) throws SQLException {

        if(startYear > endYear){
            int tmp = startYear;
            startYear = endYear;
            endYear = tmp;
        }

        String sqlString = """
                select id, name, production_year from cars
                where production_year >= ? and production_year <= ?;
        """;
        try(PreparedStatement statement = connection.prepareStatement(sqlString)) {
            statement.setInt(1, startYear);
            statement.setInt(2, endYear);
            try(ResultSet resultSet = statement.executeQuery()) {
                renderResult(resultSet);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showCarsByProductionYear(int year) throws SQLException {
        String sqlString = """
                select id, name, production_year from cars
                where production_year = ?;
        """;
        try(PreparedStatement statement = connection.prepareStatement(sqlString)) {
            statement.setInt(1, year);
            try(ResultSet resultSet = statement.executeQuery()) {
                renderResult(resultSet);
            }
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showTheLeastProductiveManufacturer() throws SQLException {
        String sqlString = """
                SELECT manufacturer, COUNT(name) AS car_count
                FROM cars
                GROUP BY manufacturer
                HAVING COUNT(name) = (
                    SELECT MIN(car_count)
                    FROM (
                             SELECT COUNT(name) AS car_count
                             FROM cars
                             GROUP BY manufacturer
                         ) AS subquery
                );
        """;
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlString)) {
            renderResult(resultSet);
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
