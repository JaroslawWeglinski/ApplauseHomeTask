package com.weglinskij.applausehometask.service;

import com.weglinskij.applausehometask.dto.DeviceDTO;
import com.weglinskij.applausehometask.dto.TesterMatchDTO;
import com.weglinskij.applausehometask.entity.Bug;
import com.weglinskij.applausehometask.mapper.DeviceMapper;
import com.weglinskij.applausehometask.mapper.TesterMapper;
import com.weglinskij.applausehometask.repository.DeviceRepository;
import com.weglinskij.applausehometask.repository.TesterRepository;
import com.weglinskij.applausehometask.request.MatchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {
    private final DeviceRepository deviceRepository;
    private final TesterRepository testerRepository;
    private final DeviceMapper deviceMapper;
    private final TesterMapper testerMapper;

    @Autowired
    public MatchService(DeviceRepository deviceRepository, TesterRepository testerRepository, DeviceMapper deviceMapper, TesterMapper testerMapper) {
        this.deviceRepository = deviceRepository;
        this.testerRepository = testerRepository;
        this.deviceMapper = deviceMapper;
        this.testerMapper = testerMapper;
    }

    public List<TesterMatchDTO> findTestersByCountriesAndDevices(MatchRequest request) {
        List<TesterMatchDTO> testers = testerRepository.findDistinctByCountryInAndDevicesIdIn(request.getCountries(), request.getDevices()).stream().map(tester -> {
            int sum = 0;
            for (Bug bug : tester.getBugs()) {
                if (request.getDevices().contains(bug.getDevice().getId())) ++sum;
            }
            return testerMapper.convertToDto(tester, sum);
        }).collect(Collectors.toList());
        return testers.stream().sorted(Comparator.comparing(TesterMatchDTO::getBugsTotal)).collect(Collectors.toList());
    }

    public List<String> getCountries() {
        return testerRepository.findAllCountries().stream().sorted().collect(Collectors.toList());
    }

    public List<DeviceDTO> getDevices() {
        return deviceRepository.findAll().stream().map(deviceMapper::convertToDTO).sorted(Comparator.comparing(DeviceDTO::getDescription, String.CASE_INSENSITIVE_ORDER)).collect(Collectors.toList());
    }
}
