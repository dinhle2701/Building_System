package com.microservice.building_be.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity(name = "utilityUsage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UtilityUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "utilityUsage_id")
    private Long uuId;

    @Column(name = "create_date")
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy' 'HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Timestamp createDate;

    @Column(name = "update_date")
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy' 'HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Timestamp updateDate;

    @Column(name = "electricity_old")
    private Long electricity_old;

    @Column(name = "electricity_new")
    private Long electricity_new;

    @Column(name = "electricity_usage")
    private Long electricityTotalUsage;

    @Column(name = "electricTotalPrice")
    private Long electricTotalPrice;

    @Column(name = "water_old")
    private Long water_old;

    @Column(name = "water_new")
    private Long water_new;

    @Column(name = "water_usage")
    private Long waterTotalUsage;

    @Column(name = "waterTotal_price")
    private Long waterTotal_price;

    @Column(name = "hygiene_price")
    private int hygiene_price = 100000;

    @Column(name = "manage_price")
    private int manage_price = 150000;

    @Column(name = "total_usage")
    private Long totalUsage;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "total_all")
    private Long totalAll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    @JsonBackReference
    private Apartment apartment;

}
