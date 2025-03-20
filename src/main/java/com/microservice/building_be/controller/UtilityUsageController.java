package com.microservice.building_be.controller;

import com.microservice.building_be.dto.request.UtilityRequest;
import com.microservice.building_be.dto.response.MonthlyUsageStats;
import com.microservice.building_be.exception.ResourceNotFoundException;
import com.microservice.building_be.model.Apartment;
import com.microservice.building_be.model.UtilityUsage;
import com.microservice.building_be.service.UtilityUsageService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/utility-usage")
@Slf4j
public class UtilityUsageController {
    @Autowired
    private UtilityUsageService utilityUsageService;

    @GetMapping("")
    public ResponseEntity<List<UtilityUsage>> getAllUtilityUsage(){
        try {
            List<UtilityUsage> utilityUsages = utilityUsageService.getAllUtility();
            return new ResponseEntity<>(utilityUsages, HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/latest/{apartmentId}")
    public ResponseEntity<UtilityUsage> getLatestUsage(@PathVariable Long apartmentId) {
        UtilityUsage latestUsage = utilityUsageService.getLatestUtilityUsage(apartmentId);
        return ResponseEntity.ok(latestUsage);
    }

    @GetMapping("/total-cost")
    public Long getTotalCostForMonth(@RequestParam("year") int year, @RequestParam("month") int month) {
        LocalDateTime startDate = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endDate = startDate.plusMonths(1).minusSeconds(1);

        Timestamp create_date = Timestamp.valueOf(startDate);
        Timestamp update_date = Timestamp.valueOf(endDate);

        return utilityUsageService.calculateTotalCostForPeriod(create_date, update_date);
    }

    @PostMapping("/{apartment_id}/create")
    public ResponseEntity<UtilityUsage> createUtilityUsage(@PathVariable Long apartment_id,  // Lấy apartment_id từ đường dẫn
                                                           @RequestBody UtilityRequest utilityRequest) {
        try {
            if (apartment_id == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Trả về lỗi nếu apartment_id là null
            }
            // Thiết lập apartment_id trong utilityRequest
            utilityRequest.setApartmentId(apartment_id);
            // Tạo utility usage
            UtilityUsage utilityUsage = utilityUsageService.createUtilityUsage(utilityRequest);

            return new ResponseEntity<>(utilityUsage, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.LENGTH_REQUIRED); // Lỗi khi số mới nhỏ hơn số cũ
        } catch (SqlScriptException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Lỗi hệ thống
        }
    }

    @GetMapping("/total-statistic")
    public ResponseEntity<Double> getTotalPrice(){
        try {
            Double total = utilityUsageService.getTotalPrice();
            return new ResponseEntity<>(total, HttpStatus.OK);
        } catch (SqlScriptException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/total-by-month")
//    public ResponseEntity<List<MonthlyUsageStats>> getTotalPriceByMonth(){
//        try {
//            List<MonthlyUsageStats> stats = utilityUsageService.getTotalPriceByMonth();
//            return new ResponseEntity<>(stats, HttpStatus.OK);
//        } catch (SqlScriptException e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
}
