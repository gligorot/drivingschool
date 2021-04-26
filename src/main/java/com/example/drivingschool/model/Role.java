package com.example.drivingschool.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Role() {
    }
}
