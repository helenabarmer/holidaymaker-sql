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

    public void addRoomToDatabase(int destination_id, String room_type, int price_per_night, boolean availability, int maximum_guests) {

        try {
            statement = connection.prepareStatement("INSERT INTO rooms (destination_id, room_type, price_per_night, availability, maximum_guests) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, destination_id);
            statement.setString(2, room_type);
            statement.setInt(3, price_per_night);
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


    public void filterRoomsInDatabase(int maximum_guests, boolean restaurant, boolean kids_club, boolean pool, boolean entertainment, boolean availability) {
        try {
            statement = connection.prepareStatement("SELECT rooms.id, destinations.city, destinations.hotel_name, rooms.room_type, rooms.price " +
                    "FROM rooms, destinations WHERE rooms.maximum_guests = ? AND destinations.restaurant = ? " +
                    "AND destinations.kids_club = ? AND destinations.pool = ? AND destinations.entertainment = ? AND rooms.availability = ? GROUP BY city;");

            statement.setInt(1, maximum_guests);
            statement.setBoolean(2, restaurant);
            statement.setBoolean(3, kids_club);
            statement.setBoolean(4, pool);
            statement.setBoolean(5, entertainment);
            statement.setBoolean(6, availability);

            try{
                resultSet = statement.executeQuery();
            }
            catch (SQLException e){
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }

            if (resultSet.next()) {
                String filterRooms =
                        "ID: " + resultSet.getString("rooms.id") + "\n" +
                        "CITY: " + resultSet.getString("city") + "\n" +
                                "HOTEL NAME: " + resultSet.getString("hotel_name") + "\n" +
                                "ROOM TYPE: " + resultSet.getString("room_type") + "\n" +
                                "PRICE: " + resultSet.getInt("price") + "\n" +
                                "*************************" + "\n";
                System.out.println(filterRooms);
            }
            else{
                System.out.println("No rooms found. Please try with different choices.  " + "\n");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void addCustomerToDatabase(String first_name, String last_name, String email, String phonenumber) {

        try {
            statement = connection.prepareStatement("INSERT INTO guest_information (first_name, last_name, email, phonenumber) VALUES (?, ?, ?, ?)");
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, email);
            statement.setString(4, phonenumber);
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

    public void searchCustomerAndPrint(String first_name, String last_name) {
        try {
            statement = connection.prepareStatement("SELECT id, first_name, last_name FROM guest_information WHERE first_name = ? AND last_name = ? ");
            statement.setString(1, first_name);
            statement.setString(2, last_name);

            try {
                resultSet = statement.executeQuery();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }


            while (resultSet.next()) {
                String roomInformation =
                        "*************************" + "\n" +
                                "ID: " + resultSet.getString("id") + "\n" +
                                "FIRST NAME: " + resultSet.getString("first_name") + "\n" +
                                "LAST NAME: " + resultSet.getString("last_name") + "\n" +
                                "*************************" + "\n";
                System.out.println(roomInformation);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }






}
