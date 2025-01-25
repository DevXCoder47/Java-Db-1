package com.nikijv.dbproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auto {

    private int id;
    private String manufacturer;
    private String name;
    private int engineCapacity;
    private int productionYear;
    private String colour;
    private String type;
}
