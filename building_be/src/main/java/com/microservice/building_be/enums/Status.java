package com.microservice.building_be.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    ENABLED ("KÍCH HOẠT"),
    DISABLED ("KHÔNG KÍCH HOẠT");

    private final String statusDescription;
}
