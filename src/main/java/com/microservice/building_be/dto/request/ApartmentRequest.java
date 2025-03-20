package com.microservice.building_be.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApartmentRequest {
    private String apartment_name;
    private double area;
    private int number_of_room;
    private String apartmentStatus;
}
