package com.microservice.building_be.controller;

import com.microservice.building_be.exception.ResourceNotFoundException;
import com.microservice.building_be.model.FireSafetyEquipment;
import com.microservice.building_be.model.Resident;
import com.microservice.building_be.repository.FireSafetyEquipmentRepository;
import com.microservice.building_be.service.FireSafetyEquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fire-safety-equipment")
@Slf4j
public class FireSafetyEquipmentController {

    @Autowired
    private FireSafetyEquipmentService fireSafetyEquipmentService;
    @Autowired
    private FireSafetyEquipmentRepository equipmentRepository;


    @GetMapping("")
    public ResponseEntity<Page<FireSafetyEquipment>> getAllFireSafetyEquipment(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        try {
            Page<FireSafetyEquipment> fireSafetyEquipments = fireSafetyEquipmentService.getAllResidents(page, size);
            log.info("Get fireSafety equipments successfully");
            return new ResponseEntity<>(fireSafetyEquipments, HttpStatus.OK);
        } catch (SqlScriptException e) {
            log.warn(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/status-summary")
    public Map<String, Long> getStatusSummary() {
        Map<String, Long> summary = new HashMap<>();
        summary.put("ACTIVE", equipmentRepository.countByStatus("ACTIVE"));
        summary.put("INACTIVE", equipmentRepository.countByStatus("INACTIVE"));
        summary.put("MAINTENANCE", equipmentRepository.countByStatus("MAINTENANCE"));
        return summary;
    }

    @PostMapping("")
    public ResponseEntity<FireSafetyEquipment> addEquipment(@RequestBody FireSafetyEquipment equipment) {
        try {
            FireSafetyEquipment fireSafetyEquipment = fireSafetyEquipmentService.addEquipment(equipment);
            return new ResponseEntity<>(fireSafetyEquipment, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FireSafetyEquipment> updateEquipment(@PathVariable Long id, @RequestBody FireSafetyEquipment equipment) {
        try {
            FireSafetyEquipment fireSafetyEquipment = fireSafetyEquipmentService.updateEquipment(id, equipment);
            return new ResponseEntity<>(fireSafetyEquipment, HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

