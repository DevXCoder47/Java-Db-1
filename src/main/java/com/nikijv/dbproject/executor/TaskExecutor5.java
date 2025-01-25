package com.nikijv.dbproject.executor;

import com.nikijv.dbproject.entity.Auto;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class TaskExecutor5 extends TaskExecutor {

    public TaskExecutor5(Connection connection) {
        super(connection);
    }

    @Override
    public void executeTasks() throws SQLException {
        updateEntryById(8, "TestEntryName");
    }

    private void addEntry(Auto car) throws SQLException {
        String sqlString = """
                insert into cars (id, manufacturer, name, engine_capacity, production_year, colour, type)
                values(?,?,?,?,?,?,?::type_enum);""";
        try(PreparedStatement statement = connection.prepareStatement(sqlString)) {
            statement.setInt(1, car.getId());
            statement.setString(2, car.getManufacturer());
            statement.setString(3, car.getName());
            statement.setInt(4, car.getEngineCapacity());
            statement.setInt(5, car.getProductionYear());
            statement.setString(6, car.getColour());
            statement.setString(7, car.getType());
            statement.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void deleteEntryById(int id) throws SQLException {
        String sqlString = """
                delete from cars where id = ?;""";
        try(PreparedStatement statement = connection.prepareStatement(sqlString)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void updateEntryById(int id, String name) {
        String sqlString = """
                update cars set name = ? where id = ?;""";
        try(PreparedStatement statement = connection.prepareStatement(sqlString)) {
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
