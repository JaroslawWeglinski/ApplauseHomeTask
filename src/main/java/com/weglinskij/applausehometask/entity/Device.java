package com.weglinskij.applausehometask.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "device")
@Data
public class Device {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;
}
