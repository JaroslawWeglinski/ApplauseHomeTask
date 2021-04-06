package com.weglinskij.applausehometask.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bug")
@Data
public class Bug {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id", referencedColumnName = "id")
    private Device device;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tester_id", referencedColumnName = "id")
    private Tester tester;
}
