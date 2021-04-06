package com.weglinskij.applausehometask.repository;

import com.weglinskij.applausehometask.entity.Tester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TesterRepository extends JpaRepository<Tester, Long> {

    @Query("select distinct country from Tester")
    List<String> findAllCountries();

    List<Tester> findDistinctByCountryInAndDevicesIdIn(List<String> countries, List<Long> devices);

}
