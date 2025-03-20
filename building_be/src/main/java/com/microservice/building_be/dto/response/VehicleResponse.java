package com.microservice.building_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {
    private Long vehicle_id;
    private String vehicle_name;
    private String license_plate;
    private String vehicle_type;
    private String color;
    private String resident_name;
    private String apartment_name;
}
