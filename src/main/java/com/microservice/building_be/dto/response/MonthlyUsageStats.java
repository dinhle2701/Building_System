package com.microservice.building_be.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class MonthlyUsageStats {
    private String month;
    private Double totalPrice;

    // Constructor phải khớp với truy vấn
    public MonthlyUsageStats(String month, Double totalPrice) {
        this.month = month;
        this.totalPrice = totalPrice;
    }

    // Getter và Setter
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}

