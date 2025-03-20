package com.microservice.building_be.dto.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UtilityRequest {
    private Long electricity_new;
    private Long water_new;
    private Long apartmentId;
}
