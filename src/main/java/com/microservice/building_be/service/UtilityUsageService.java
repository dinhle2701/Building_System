package com.microservice.building_be.service;

import com.microservice.building_be.dto.request.UtilityRequest;
import com.microservice.building_be.dto.response.MonthlyUsageStats;
import com.microservice.building_be.model.UtilityUsage;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public interface UtilityUsageService {

    List<UtilityUsage> getAllUtility();

    UtilityUsage createUtilityUsage(UtilityRequest request);

    Long calculateTotalCostForPeriod(Timestamp create_date, Timestamp update_date);

    void generateElectronicAndWaterUsage(UtilityUsage utilityUsage);

    Double getTotalPrice();

    UtilityUsage getLatestUtilityUsage(Long apartmentId);

//    List<MonthlyUsageStats> getTotalPriceByMonth();
}
