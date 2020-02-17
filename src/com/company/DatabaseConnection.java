package com.company;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

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

    public void addDestinationToDatabase(String city, String hotelName, boolean restaurant, boolean kidsClub,
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

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addRoomToDatabase(int destination_id, String room_type, int price, boolean availability, int maximum_guests) {

        try {
            statement = connection.prepareStatement("INSERT INTO rooms (destination_id, room_type, price, availability, maximum_guests) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, destination_id);
            statement.setString(2, room_type);
            statement.setInt(3, price);
            statement.setBoolean(4, availability);
            statement.setInt(5, maximum_guests);
            try{
                statement.executeUpdate();
            }
            catch (SQLException e){
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void allDestinationsInDatabase() {
        try {
            statement = connection.prepareStatement("SELECT id, city, hotel_name, number_of_rooms FROM destinations ");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String roomInformation =
                        "ID: " + resultSet.getString("id") + "\n" +
                        "CITY: " + resultSet.getString("city") + "\n" +
                        "HOTEL NAME: " + resultSet.getString("hotel_name") + "\n" +
                        "NUMBER OF ROOMS: " + resultSet.getInt("number_of_rooms") + "\n" +
                        "*************************" + "\n";
                System.out.println(roomInformation);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
