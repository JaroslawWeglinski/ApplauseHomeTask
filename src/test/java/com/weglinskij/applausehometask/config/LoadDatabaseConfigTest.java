package com.weglinskij.applausehometask.config;

import com.weglinskij.applausehometask.entity.Bug;
import com.weglinskij.applausehometask.entity.Device;
import com.weglinskij.applausehometask.entity.Tester;
import com.weglinskij.applausehometask.repository.BugRepository;
import com.weglinskij.applausehometask.repository.DeviceRepository;
import com.weglinskij.applausehometask.repository.TesterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LoadDatabaseConfigTest {

    @Autowired
    BugRepository bugRepository;

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    TesterRepository testerRepository;


    @Test
    void shouldSuccessfullyLoadDevices() {

        List<Device> deviceList = deviceRepository.findAll();

        assertEquals(10, deviceList.size());

        Device device1 = deviceList.get(0);

        assertEquals(1, device1.getId());
        assertEquals("iPhone 4", device1.getDescription());

        Device device5 = deviceList.get(4);

        assertEquals(5, device5.getId());
        assertEquals("Galaxy S4", device5.getDescription());

        Device device10 = deviceList.get(9);

        assertEquals(10, device10.getId());
        assertEquals("iPhone 3", device10.getDescription());
    }

    @Test
    void shouldSuccessfullyLoadTesters() {

        List<Tester> testerList = testerRepository.findAll();

        assertEquals(9, testerList.size());

        Tester tester1 = testerList.get(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        assertEquals(1, tester1.getId());
        assertEquals("Miguel", tester1.getFirstName());
        assertEquals("Bautista", tester1.getLastName());
        assertEquals("US", tester1.getCountry());
        assertEquals(LocalDateTime.parse("2013-08-04 23:57:38", formatter), tester1.getLastLogin());

        Tester tester5 = testerList.get(4);

        assertEquals(5, tester5.getId());
        assertEquals("Mingquan", tester5.getFirstName());
        assertEquals("Zheng", tester5.getLastName());
        assertEquals("JP", tester5.getCountry());
        assertEquals(LocalDateTime.parse("2013-08-04 22:07:38", formatter), tester5.getLastLogin());

        Tester tester9 = testerList.get(8);

        assertEquals(9, tester9.getId());
        assertEquals("Darshini", tester9.getFirstName());
        assertEquals("Thiagarajan", tester9.getLastName());
        assertEquals("GB", tester9.getCountry());
        assertEquals(LocalDateTime.parse("2013-08-05 15:00:38", formatter), tester9.getLastLogin());
    }

    @Test
    void shouldSuccessfullyLoadBugs() {

        List<Bug> bugList = bugRepository.findAll();

        assertEquals(1000, bugList.size());

        Bug bug1 = bugList.get(0);

        assertEquals(1, bug1.getId());
        assertEquals(1, bug1.getDevice().getId());
        assertEquals(4, bug1.getTester().getId());

        Bug bug500 = bugList.get(499);

        assertEquals(500, bug500.getId());
        assertEquals(3, bug500.getDevice().getId());
        assertEquals(6, bug500.getTester().getId());

        Bug bug1000 = bugList.get(999);

        assertEquals(1000, bug1000.getId());
        assertEquals(9, bug1000.getDevice().getId());
        assertEquals(8, bug1000.getTester().getId());
    }

    @Test
    void shouldSuccessfullyLoadTestersDevices() {

        List<Tester> testerList = testerRepository.findAll();

        Tester tester1 = testerList.get(0);
        Tester tester5 = testerList.get(4);
        Tester tester9 = testerList.get(8);

        List<Device> devices1 = tester1.getDevices();
        List<Device> devices5 = tester5.getDevices();
        List<Device> devices9 = tester9.getDevices();

        assertEquals(1, devices1.get(0).getId());
        assertEquals(2, devices1.get(1).getId());
        assertEquals(10, devices1.get(3).getId());

        assertEquals(5, devices5.get(0).getId());
        assertEquals(7, devices5.get(2).getId());
        assertEquals(10, devices5.get(4).getId());

        assertEquals(5, devices9.get(0).getId());
        assertEquals(6, devices9.get(1).getId());
        assertEquals(9, devices9.get(3).getId());
    }


}
