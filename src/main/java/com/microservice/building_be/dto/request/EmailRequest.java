package com.microservice.building_be.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmailRequest {
    private List<String> to;
    private String subject;
    private String text;
}
