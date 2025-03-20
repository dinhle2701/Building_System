package com.microservice.building_be.dto.response;

import com.microservice.building_be.model.Apartment;
import com.microservice.building_be.model.Staff;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AccountResponse {
    private Long id;
    private String role;
    private String email;
    private String create_date;
    private String status;
    private Long apartment_id;
    private Long staff_id;
}
