package com.weglinskij.applausehometask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weglinskij.applausehometask.dto.DeviceDTO;
import com.weglinskij.applausehometask.dto.TesterMatchDTO;
import com.weglinskij.applausehometask.request.MatchRequest;
import com.weglinskij.applausehometask.service.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;

    private JacksonTester<List<String>> jsonCountry;

    private JacksonTester<List<DeviceDTO>> jsonDevice;

    private JacksonTester<List<TesterMatchDTO>> jsonTesters;

    private JacksonTester<MatchRequest> jsonMatchRequest;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldReturnAllCountries() throws Exception {

        List<String> allCountries = new ArrayList<String>(Arrays.asList("GB", "JP", "US"));

        given(matchService.getCountries()).willReturn(allCountries);

        MockHttpServletResponse response = mockMvc.perform(get("/api/public/countries").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonCountry.write(allCountries).getJson());

    }

    @Test
    public void shouldReturnAllDevices() throws Exception {

        List<DeviceDTO> allDevices = Arrays.asList(new DeviceDTO(1L, "iPhone 3"), new DeviceDTO(2L, "HTC One"), new DeviceDTO(3L, "Droid DNA"), new DeviceDTO(4L, "Droid Razor"), new DeviceDTO(5L, "Nexus 4"), new DeviceDTO(6L, "Galaxy S4"), new DeviceDTO(7L, "Galaxy S3"), new DeviceDTO(8L, "iPhone 5"), new DeviceDTO(9L, "iPhone 4S"), new DeviceDTO(10L, "iPhone 4"));

        given(matchService.getDevices()).willReturn(allDevices);

        MockHttpServletResponse response = mockMvc.perform(get("/api/public/devices").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonDevice.write(allDevices).getJson());

    }

    @Test
    public void shouldReturnTestersByCountriesAndDevices() throws Exception {

        List<TesterMatchDTO> testers = Arrays.asList(new TesterMatchDTO(5L, "Mingquan", "Zheng", 21), new TesterMatchDTO(8L, "Sean", "Wellington", 28));

        MatchRequest request = new MatchRequest();
        List<String> countries = new ArrayList<>();
        List<Long> devices = new ArrayList<>();
        countries.add("GB");
        countries.add("JP");
        devices.add(1L);
        request.setDevices(devices);
        request.setCountries(countries);

        when(matchService.findTestersByCountriesAndDevices(Mockito.any())).thenReturn(testers);

        mockMvc.perform(post("/api/public/testers").accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE).content("{ \"countries\": [ \"GB\", \"JP\" ], \"devices\": [ 1 ]}")).andExpect(status().isOk()).andExpect(content().string(jsonTesters.write(testers).getJson()));

    }
}
