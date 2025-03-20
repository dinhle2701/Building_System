package com.microservice.building_be.controller;


import com.microservice.building_be.dto.request.ApartmentRequest;
import com.microservice.building_be.dto.request.ResidentRequest;
import com.microservice.building_be.exception.ResourceNotFoundException;
import com.microservice.building_be.model.Apartment;
import com.microservice.building_be.model.Resident;
import com.microservice.building_be.service.ApartmentService;
import com.microservice.building_be.service.ResidentService;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.util.List;


@RestController
@RequestMapping("/api/v1/apartment")
@Slf4j
public class ApartmentController {

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private ResidentService residentService;


    // api get all apartment - pagination api
    @GetMapping("")
    public ResponseEntity<Page<Apartment>> getAllApartment(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "id") String sort
    ){
        try {
            Page<Apartment> staffs = apartmentService.getAllStaffs(page, size);
            return new ResponseEntity<>(staffs, HttpStatus.OK);
        } catch (SqlScriptException e){
            log.warn(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // api get apartment with id
    @GetMapping("/{apartment_id}")
    public ResponseEntity<Apartment> getApartmentById(@PathVariable("apartment_id") Long apartment_id){
        try {
            Apartment apartment = apartmentService.getApartmentById(apartment_id);
            log.info("Found apartment has id " + apartment_id);
            return new ResponseEntity<>(apartment, HttpStatus.OK);
        } catch (Exception e){
            log.warn("Không tìm thấy dữ liệu!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // api create new apartment
    @PostMapping("")
    public ResponseEntity<Apartment> createApartment(@RequestBody ApartmentRequest apartmentRequest){
        try {
            Apartment newApartment = apartmentService.saveApartment(apartmentRequest);
            log.info("Create new apartment successfully!");
            return new ResponseEntity<>(newApartment, HttpStatus.CREATED);
        } catch (Exception e){
            log.error("Create new apartment has error!" + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // api update apartment has id
    @PutMapping("/{apartment_id}")
    public ResponseEntity<Apartment> updateApartmentById(@PathVariable("apartment_id") Long apartment_id, @RequestBody ApartmentRequest apartmentRequest){
        try {
            Apartment apartment = apartmentService.updateApartmentById(apartment_id, apartmentRequest);
            log.info("Update has successfully!");
            return new ResponseEntity<>(apartment, HttpStatus.OK);
        } catch (SqlScriptException e) {
            log.error("Update has error!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // api delete apartment with id
    @DeleteMapping("/{apartment_id}")
    public ResponseEntity<String> deleteApartment(@PathVariable("apartment_id") Long apartment_id){
        try {
            apartmentService.deleteApartmentById(apartment_id);
            log.info("Delete has successfully!");
            return new ResponseEntity<>("Deleted!", HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            log.error("Delete has error");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // api create resident in apartment with apartment id
    @PostMapping("/{apartment_id}/resident")
    public ResponseEntity<Resident> createResidentByApartmentId(
            @PathVariable("apartment_id") Long apartment_id,
            @RequestBody ResidentRequest residentRequest) {
        try {
            // Tạo Resident và lưu vào Apartment
            Resident newResident = residentService.createResident(residentRequest, apartment_id);

            // Trả về response với Resident vừa tạo
            return new ResponseEntity<>(newResident, HttpStatus.CREATED);
        } catch (SqlScriptException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{apartment_id}/delete-resident-from-apartment/{resident_id}")
    public ResponseEntity<?> deleteResidentFromApartment(
            @PathVariable("apartment_id") Long apartmentId,
            @PathVariable("resident_id") Long residentId) {
        try {
            // Gọi service để xóa liên kết cư dân với căn hộ
            Resident updatedResident = residentService.removeResidentFromApartment(residentId, apartmentId);
            return new ResponseEntity<>(updatedResident, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}