package com.weglinskij.applausehometask.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MatchRequest {
    private List<String> countries;
    private List<Long> devices;
}
