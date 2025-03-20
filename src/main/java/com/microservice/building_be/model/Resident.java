package com.microservice.building_be.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "Resident")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resident_id")
    private long resident_id;

    @Column(name = "resident_name")
    @NotBlank(message = "Tên không được để trống")
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ ]+$", message = "Tên không được chứa số hoặc ký tự đặc biệt")
    private String resident_name;

    @Column(name = "phone_number")
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại phải là 10 số và bắt đầu bằng 0")
    private String phone_number;

    @Column(name = "email")
    @NotBlank(message = "Email không được để trống")
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email phải có dạng @gmail.com")
    private String email;

    @Column(name = "cccd")
    @NotBlank(message = "Nhập đủ số CCCD")
    private String cccd;

    @Column(name = "birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Ho_Chi_Minh")
    @Temporal(TemporalType.DATE)
    private LocalDate birthday;

    @Column(name = "sex")
    private String sex;

    @Column(name = "move_in_date")
//    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy' 'HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Timestamp move_in_date;

    @Column(name = "move_out_date")
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy' 'HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private Timestamp move_out_date = null;

    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Card> cards;

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    @JsonBackReference("apartment-resident")
    private Apartment apartment;


}