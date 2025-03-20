package com.microservice.building_be.controller;

import com.microservice.building_be.dto.request.CardRequest;
import com.microservice.building_be.dto.request.ResidentRequest;
import com.microservice.building_be.dto.request.VehicleRequest;
import com.microservice.building_be.dto.response.ResidentResponse;
import com.microservice.building_be.dto.response.VehicleResponse;
import com.microservice.building_be.exception.ResourceNotFoundException;
import com.microservice.building_be.model.Card;
import com.microservice.building_be.model.Resident;
import com.microservice.building_be.model.Vehicle;
import com.microservice.building_be.service.CardService;
import com.microservice.building_be.service.ResidentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resident")
@Slf4j
public class ResidentController {

    @Autowired
    private ResidentService residentService;
    @Autowired
    private CardService cardService;


    // api get residents - pagination resident
    @GetMapping("")
    public ResponseEntity<Page<ResidentResponse>> getAllResident(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        try {
            Page<ResidentResponse> residents = residentService.getAllResidents(page, size);
            log.info("Get residents successfully");
            return new ResponseEntity<>(residents, HttpStatus.OK);
        } catch (SqlScriptException e) {
            log.warn(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    public ResponseEntity<Resident> createNewResident(@RequestBody ResidentRequest residentRequest){
        try {
            Resident resident = residentService.createNewResident(residentRequest);
            return new ResponseEntity<>(resident, HttpStatus.CREATED);
        } catch (SqlScriptException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // api get resident by id
    @GetMapping("/{resident_id}")
    public ResponseEntity<Resident> getResidentById(@PathVariable("resident_id") Long resident_id) {
        try {
            Resident resident = residentService.getResidentById(resident_id);
            log.info("Found resident has id: " + resident_id);
            return new ResponseEntity<>(resident, HttpStatus.OK);
        } catch (SqlScriptException e) {
            log.warn("Not found residents or the path is incorrect!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // api update resident with id
    @PutMapping("/{resident_id}")
    public ResponseEntity<Resident> updateResident(@Valid @PathVariable("resident_id") Long resident_id, Resident updateResident) {
        try {
            Resident resident = residentService.updateResidentById(resident_id, updateResident);
            log.info("Update new resident completed!");
            return new ResponseEntity<>(resident, HttpStatus.OK);
        } catch (SqlScriptException e) {
            log.info("Update new resident has failed!" + e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // api delete resident with id
    @DeleteMapping("/{resident_id}")
    public ResponseEntity<String> deleteResident(@PathVariable("resident_id") Long resident_id) {
        try {
            residentService.deleteResidentById(resident_id);
            log.info("Delete resident with id: " + resident_id + " successfully!");
            return new ResponseEntity<>("Deleted!", HttpStatus.OK);
        } catch (SqlScriptException e) {
            log.info("Delete resident has failed! Not found resident");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/into/{apartment_id}/{resident_id}")
    public ResponseEntity<Resident> addResidentIntoApartment(
            @PathVariable ("resident_id") Long residentId,
            @PathVariable("apartment_id") Long apartmentId)
    {
        try {
            Resident resident = residentService.addResidentIntoApartment(residentId, apartmentId);
            return new ResponseEntity<>(resident, HttpStatus.OK);
        }catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // api get vehicles
    @GetMapping("/vehicles")
    public ResponseEntity<Page<VehicleResponse>> getAllVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        try {
            Page<VehicleResponse> vehicles = residentService.getAllVehicles(page, size);
            log.info("Get residents successfully");
            return new ResponseEntity<>(vehicles, HttpStatus.OK);
        } catch (SqlScriptException e) {
            log.warn(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/{resident_id}/vehicles/{parking_id}")
    public ResponseEntity<Vehicle> addVehicleToResident(
            @PathVariable("resident_id") Long residentId,
            @PathVariable("parking_id") Long parking_id,
            @Valid @RequestBody VehicleRequest vehicleRequest) {
        try {
            Vehicle vehicle = residentService.addVehicleToResident(residentId, parking_id, vehicleRequest);
            log.info("Added vehicle to resident with id: " + residentId);
            return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
        } catch (SqlScriptException e) {
            log.warn("Failed to add vehicle for resident id: " + residentId + ", error: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/vehicle/{vehicle_id}")
    public ResponseEntity<?> deleteVehicleById(@PathVariable("vehicle_id") Long vehicle_id) {
        try {
            residentService.deleteVehicle(vehicle_id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cards")
    public ResponseEntity<Page<Card>> getAllCards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        try {
            Page<Card> cards = cardService.getAllCards(page, size);
            log.info("Get residents successfully");
            return new ResponseEntity<>(cards, HttpStatus.OK);
        } catch (SqlScriptException e) {
            log.warn(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{resident_id}/card")
    public ResponseEntity<Card> addCardToResident(
            @PathVariable("resident_id") Long residentId) {
        try {
            Card card = cardService.addCardToResident(residentId);
            log.info("Added vehicle to resident with id: " + residentId);
            return new ResponseEntity<>(card, HttpStatus.CREATED);
        } catch (SqlScriptException e) {
            log.warn("Failed to add vehicle for resident id: " + residentId + ", error: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/card/{card_id}")
    public ResponseEntity<?> deleteCardById(@PathVariable("card_id") Long card_id) {
        try {
            cardService.deleteCardByResidentId(card_id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}