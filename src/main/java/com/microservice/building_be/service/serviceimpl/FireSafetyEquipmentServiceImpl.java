package com.microservice.building_be.service.serviceimpl;

import com.microservice.building_be.model.FireSafetyEquipment;
import com.microservice.building_be.model.Resident;
import com.microservice.building_be.repository.FireSafetyEquipmentRepository;
import com.microservice.building_be.service.FireSafetyEquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireSafetyEquipmentServiceImpl implements FireSafetyEquipmentService {
    @Autowired
    private FireSafetyEquipmentRepository equipmentRepository;
//    @Override
//    public List<FireSafetyEquipment> getAllEquipment() {
//        return equipmentRepository.findAll();
//    }

    @Override
    public Page<FireSafetyEquipment> getAllResidents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return equipmentRepository.findAll(pageable);
    }

    @Override
    public FireSafetyEquipment getEquipmentById(Long id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
    }

    @Override
    public FireSafetyEquipment addEquipment(FireSafetyEquipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @Override
    public FireSafetyEquipment updateEquipment(Long id, FireSafetyEquipment updatedEquipment) {
        FireSafetyEquipment existingEquipment = getEquipmentById(id);
        existingEquipment.setName(updatedEquipment.getName());
        existingEquipment.setLocation(updatedEquipment.getLocation());
        existingEquipment.setStatus(updatedEquipment.getStatus());
        return equipmentRepository.save(existingEquipment);
    }
}
