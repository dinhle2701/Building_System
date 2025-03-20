package com.microservice.building_be.controller;

import com.microservice.building_be.dto.request.ParkingRequest;
import com.microservice.building_be.model.Parking;
import com.microservice.building_be.service.ParkingService;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {
    @Autowired
    private ParkingService parkingService;

    @GetMapping("")
    public ResponseEntity<List<Parking>> getAllParking(){
        try {
            List<Parking> parkings = parkingService.getAllParking();
            return new ResponseEntity<>(parkings, HttpStatus.OK);
        } catch (SqlScriptException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parking> getParkingById(@PathVariable("id") Long id){
        try {
            Parking parking = parkingService.getParkingById(id);
            return new ResponseEntity<>(parking, HttpStatus.OK);
        } catch (SqlScriptException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    public ResponseEntity<Parking> createNewParking(@RequestBody ParkingRequest parking){
        try {
            Parking newParking = parkingService.createParking(parking);
            return new ResponseEntity<>(newParking, HttpStatus.CREATED);
        } catch (SqlScriptException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
