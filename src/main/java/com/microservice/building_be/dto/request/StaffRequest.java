package com.microservice.building_be.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class StaffRequest {
    private String staff_name;
    private String staff_code;
    private String staff_img;
    private String staff_position;
    private String phone;
    private String email;
    private LocalDate birthday;
    private int work_time = 0;
    private boolean staffStatus = false;
    private LocalDateTime create_date;
    private LocalDateTime update_date;
}
