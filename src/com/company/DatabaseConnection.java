package com.company;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseConnection {

    private Connection connection = null;
    private PreparedStatement statement;
    private ResultSet resultSet;


    public DatabaseConnection(){
        connect();
    }

    private void connect(){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost/holidaymaker?user=root&password=mysql&serverTimezone=UTC");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addDestination(String city, String hotelName, boolean restaurant, boolean kidsClub,
                               boolean pool, boolean entertainment, String rating, String distanceCity,
                               String distanceBeach, int numberOfRooms){

        try {
            //statement = connection.prepareStatement("INSERT INTO destinations SET name = ?, type = ?, email = ?");
            statement = connection.prepareStatement("INSERT INTO destinations (city, hotel_name, restaurant, kids_club, pool, entertainment, rating, distance_centre, distance_beach, number_of_rooms) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1,city);
            statement.setString(2,hotelName);
            statement.setBoolean(3,restaurant);
            statement.setBoolean(4,kidsClub);
            statement.setBoolean(5,pool);
            statement.setBoolean(6,entertainment);
            statement.setString(7,rating);
            statement.setString(8,distanceCity);
            statement.setString(9,distanceBeach);
            statement.setInt(10, numberOfRooms);
            statement.executeUpdate();

        } catch (Exception ex) { ex.printStackTrace(); }

    }

    public void addBookingToDatabase(){

    }
}
