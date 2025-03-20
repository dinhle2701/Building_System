package com.microservice.building_be.service.serviceimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DatabaseHandler {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/building";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    public void saveImageUrlToDB(String imageUrl) {
        String sql = "UPDATE staff SET staff_img = ? WHERE staff_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, imageUrl);
            preparedStatement.executeUpdate();

            System.out.println("Đã lưu URL ảnh vào cột staff_img thành công.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveStaffImageUrlToDB(int staffId, String imageUrl) {
        String sql = "UPDATE staff SET staff_img = ? WHERE staff_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, imageUrl);
            preparedStatement.setInt(2, staffId);
            preparedStatement.executeUpdate();

            System.out.println("Đã lưu URL ảnh vào cột staff_img thành công.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}