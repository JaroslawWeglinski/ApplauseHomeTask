package com.weglinskij.applausehometask.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tester")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Tester {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "country")
    private String country;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "testers_devices",
            joinColumns = {@JoinColumn(name = "tester_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "device_id", referencedColumnName = "id")}
    )
    private List<Device> devices;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tester")
    @Fetch(value = FetchMode.SELECT)
    private Set<Bug> bugs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tester tester = (Tester) o;
        return Objects.equals(id, tester.id) && Objects.equals(firstName, tester.firstName) && Objects.equals(lastName, tester.lastName) && Objects.equals(country, tester.country) && Objects.equals(lastLogin, tester.lastLogin) && Objects.equals(devices, tester.devices);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, country, lastLogin, devices);
    }
}
