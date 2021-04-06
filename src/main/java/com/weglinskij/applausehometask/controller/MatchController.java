package com.weglinskij.applausehometask.controller;

import com.weglinskij.applausehometask.dto.DeviceDTO;
import com.weglinskij.applausehometask.dto.TesterMatchDTO;
import com.weglinskij.applausehometask.request.MatchRequest;
import com.weglinskij.applausehometask.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/public")
public class MatchController {
    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping(value = "/testers", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TesterMatchDTO>> match(@RequestBody MatchRequest request) {
        List<TesterMatchDTO> testers = matchService.findTestersByCountriesAndDevices(request);
        return testers != null ? new ResponseEntity<>(testers, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/countries", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getCountries() {
        List<String> countries = matchService.getCountries();
        return countries != null ? new ResponseEntity<>(countries, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/devices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> devices = matchService.getDevices();
        return devices != null ? new ResponseEntity<>(devices, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
