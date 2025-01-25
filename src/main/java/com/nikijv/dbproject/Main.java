package com.nikijv.dbproject;

import com.nikijv.dbproject.manager.MyDbManager;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try(MyDbManager manager = new MyDbManager("jdbc:postgresql://localhost:5432/autos", "postgres", "")){
            manager.executeTasks();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
