package com.microservice.building_be.service;

import com.microservice.building_be.model.FireSafetyEquipment;
import com.microservice.building_be.model.Resident;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FireSafetyEquipmentService {
    //    List<FireSafetyEquipment> getAllEquipment();
    Page<FireSafetyEquipment> getAllResidents(int page, int size);

    FireSafetyEquipment getEquipmentById(Long id);

    FireSafetyEquipment addEquipment(FireSafetyEquipment equipment);

    FireSafetyEquipment updateEquipment(Long id, FireSafetyEquipment updatedEquipment);
}
