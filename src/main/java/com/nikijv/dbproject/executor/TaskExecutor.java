package com.nikijv.dbproject.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public abstract class TaskExecutor {
    protected final Connection connection;

    public TaskExecutor(Connection connection) {
        this.connection = connection;
    }

    public abstract void executeTasks() throws SQLException;

    protected void renderResult(ResultSet resultSet) throws SQLException {

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            System.out.print(metaData.getColumnName(i) + "\t");
        }
        System.out.println();

        for (int i = 1; i <= columnCount; i++) {
            System.out.print("---------");
        }
        System.out.println();

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getString(i) + "\t");
            }
            System.out.println();
        }
    }
}
