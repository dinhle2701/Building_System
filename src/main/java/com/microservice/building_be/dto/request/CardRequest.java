package com.microservice.building_be.dto.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CardRequest {
    private String cardCode;
    private String card_status;
    private LocalDate create_date;
}
