package com.microservice.building_be.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("QUẢN TRỊ"),
    USER ("NGƯỜI DÙNG"),
    STAFF ("NHÂN VIÊN");

    private final String statusDescription;
}
