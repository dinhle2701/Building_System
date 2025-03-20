package com.microservice.building_be.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkingRequest {
    private String park_name;
    private String park_type;
    private String park_description;
}
