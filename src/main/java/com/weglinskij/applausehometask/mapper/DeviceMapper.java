package com.weglinskij.applausehometask.mapper;

import com.weglinskij.applausehometask.dto.DeviceDTO;
import com.weglinskij.applausehometask.entity.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {
    public DeviceDTO convertToDTO(Device device) {
        DeviceDTO dto = new DeviceDTO();
        dto.setId(device.getId());
        dto.setDescription(device.getDescription());
        return dto;
    }
}
