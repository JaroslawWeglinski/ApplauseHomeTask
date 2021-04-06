package com.weglinskij.applausehometask.config;

import com.weglinskij.applausehometask.entity.Bug;
import com.weglinskij.applausehometask.entity.Device;
import com.weglinskij.applausehometask.entity.Tester;
import com.weglinskij.applausehometask.entity.TesterDevice;
import com.weglinskij.applausehometask.repository.BugRepository;
import com.weglinskij.applausehometask.repository.DeviceRepository;
import com.weglinskij.applausehometask.repository.TesterRepository;
import org.sfm.csv.CsvMapper;
import org.sfm.csv.CsvMapperFactory;
import org.sfm.csv.CsvParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.Optional;

@Configuration
public class LoadDatabaseConfig {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabaseConfig.class);

    @Bean
    CommandLineRunner initDatabase(BugRepository bugRepository, DeviceRepository deviceRepository, TesterRepository testerRepository) {

        return args -> {
            CsvMapper<Device> deviceMapper = CsvMapperFactory.newInstance()
                    .addAlias("deviceId", "id")
                    .newMapper(Device.class);
            CsvParser.mapWith(deviceMapper).stream(new File("src/main/resources/data/devices.csv")).forEach(device -> {
                if (device.getDescription() != null) deviceRepository.save(device);
            });

            CsvMapper<Tester> testerMapper = CsvMapperFactory.newInstance()
                    .addAlias("testerId", "id")
                    .newMapper(Tester.class);
            CsvParser.mapWith(testerMapper).stream(new File("src/main/resources/data/testers.csv")).forEach(tester -> {
                if (tester.getFirstName() != null && tester.getLastName() != null && tester.getCountry() != null && tester.getLastLogin() != null)
                    testerRepository.save(tester);
            });

            CsvMapper<Bug> bugMapper = CsvMapperFactory.newInstance()
                    .addAlias("bugId", "id")
                    .newMapper(Bug.class);
            CsvParser.mapWith(bugMapper).stream(new File("src/main/resources/data/bugs.csv")).forEach(bug -> {
                if (bug.getDevice() != null && bug.getTester() != null) bugRepository.save(bug);
            });

            CsvMapper<TesterDevice> testerDeviceMapper = CsvMapperFactory.newInstance()
                    .newMapper(TesterDevice.class);
            CsvParser.mapWith(testerDeviceMapper).stream(new File("src/main/resources/data/tester_device.csv")).forEach(testerDevice -> {
                if (testerDevice.getTesterId() != null && testerDevice.getDeviceId() != null) {
                    Optional<Tester> testerOptional = testerRepository.findById(testerDevice.getTesterId());
                    Optional<Device> deviceOptional = deviceRepository.findById(testerDevice.getDeviceId());
                    if (testerOptional.isPresent() && deviceOptional.isPresent()) {
                        Tester tester = testerOptional.get();
                        Device device = deviceOptional.get();
                        tester.getDevices().add(device);
                        if (tester.getFirstName() != null && tester.getLastName() != null && tester.getCountry() != null && tester.getLastLogin() != null)
                            testerRepository.save(tester);
                    }
                }
            });
        };
    }

}