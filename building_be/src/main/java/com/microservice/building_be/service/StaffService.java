package com.microservice.building_be.service;

import com.microservice.building_be.dto.request.StaffRequest;
import com.microservice.building_be.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface StaffService {

//    List<Staff> getAllVehicles(int page, int size, String sort);

    Page<Staff> getAllStaffs(int page, int size);

    Optional<Staff> getStaffById(Long staff_id);

    Staff createNewStaff(StaffRequest staffRequest);

    Staff updateStaff(Long staff_id, Staff updateStaff);

    void deleteStaff(Long staff_id);

    void generateStaffCode(Staff staff);



}
