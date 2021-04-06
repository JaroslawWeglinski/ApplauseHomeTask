package com.weglinskij.applausehometask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TesterMatchDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer bugsTotal;
}
