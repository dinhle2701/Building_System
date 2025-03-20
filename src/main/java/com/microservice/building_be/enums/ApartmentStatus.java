package com.microservice.building_be.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApartmentStatus {
    IN_USE("ĐANG SỬ DỤNG"),
    VACANT("TRỐNG"),
    UNDER_REPAIR("ĐANG SỬA CHỮA");

    private final String value;

//    ApartmentStatus(String value) {
//        this.value = value;
//    }
//
//    @JsonValue
//    public String getValue() {
//        return value;
//    }
//
//    @JsonCreator
//    public static ApartmentStatus fromValue(String value) {
//        for (ApartmentStatus status : ApartmentStatus.values()) {
//            if (status.value.equalsIgnoreCase(value)) {
//                return status;
//            }
//        }
//        throw new IllegalArgumentException("Invalid status: " + value);
//    }
}
