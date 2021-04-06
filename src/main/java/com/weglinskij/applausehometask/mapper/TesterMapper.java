package com.weglinskij.applausehometask.mapper;

import com.weglinskij.applausehometask.dto.TesterMatchDTO;
import com.weglinskij.applausehometask.entity.Tester;
import org.springframework.stereotype.Component;

@Component
public class TesterMapper {
    public TesterMatchDTO convertToDto(Tester tester, Integer bugsTotal) {
        TesterMatchDTO dto = new TesterMatchDTO();
        dto.setId(tester.getId());
        dto.setFirstName(tester.getFirstName());
        dto.setLastName(tester.getLastName());
        dto.setBugsTotal(bugsTotal);
        return dto;
    }
}
