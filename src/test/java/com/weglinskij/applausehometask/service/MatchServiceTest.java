package com.weglinskij.applausehometask.service;

import com.weglinskij.applausehometask.dto.DeviceDTO;
import com.weglinskij.applausehometask.dto.TesterMatchDTO;
import com.weglinskij.applausehometask.mapper.TesterMapper;
import com.weglinskij.applausehometask.repository.TesterRepository;
import com.weglinskij.applausehometask.request.MatchRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class MatchServiceTest {

    @Autowired
    MatchService matchService;

    @Autowired
    TesterRepository testerRepository;

    @Autowired
    TesterMapper testerMapper;

    @Test
    void shouldReturnAllCountries() {
        List<String> countries = matchService.getCountries();
        assertTrue(countries.containsAll(new ArrayList<String>(Arrays.asList("GB", "JP", "US"))));
    }

    @Test
    void shouldReturnAllDevices() {
        List<DeviceDTO> devices = matchService.getDevices();
        List<String> devicesDescriptions = new ArrayList<>();
        devices.forEach(device -> devicesDescriptions.add(device.getDescription()));
        assertTrue(devicesDescriptions.containsAll(new ArrayList<String>(Arrays.asList("iPhone 3", "HTC One", "Droid DNA", "Droid Razor", "Nexus 4", "Galaxy S4", "Galaxy S3", "iPhone 5", "iPhone 4S", "iPhone 4"))));
    }

    @Test
    void shouldReturnEmptyObject() {
        MatchRequest request = new MatchRequest();
        request.setCountries(new ArrayList<String>());
        request.setDevices(new ArrayList<Long>());
        assertTrue(matchService.findTestersByCountriesAndDevices(request).isEmpty());
    }

    @Test
    @Transactional
    void shouldReturnTestersByCountriesAndDevices() {
        MatchRequest request = new MatchRequest();
        List<String> countries = new ArrayList<>();
        List<Long> devices = new ArrayList<>();

        devices.add(1L);
        request.setDevices(devices);

        countries.add("GB");
        request.setCountries(countries);
        List<TesterMatchDTO> result = matchService.findTestersByCountriesAndDevices(request);
        assertTrue(result.isEmpty());

        countries.add("JP");
        request.setCountries(countries);
        result = matchService.findTestersByCountriesAndDevices(request);
        assertTrue(result.size() == 2);
        assertTrue(result.contains(testerMapper.convertToDto(testerRepository.findById(5L).get(), 21)));
        assertTrue(result.contains(testerMapper.convertToDto(testerRepository.findById(8L).get(), 28)));
    }
}
