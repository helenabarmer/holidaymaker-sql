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


    public DatabaseConnection() {
        connect();
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/holidaymaker?user=root&password=mysql&serverTimezone=UTC");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDestinationToDatabase(String city, String hotelName, boolean restaurant, boolean kidsClub,
                                         boolean pool, boolean entertainment, String rating, String distanceCity,
                                         String distanceBeach, int numberOfRooms) {

        try {
            //statement = connection.prepareStatement("INSERT INTO destinations SET name = ?, type = ?, email = ?");
            statement = connection.prepareStatement("INSERT INTO destinations (city, hotel_name, restaurant, kids_club, pool, entertainment, rating, distance_centre, distance_beach, number_of_rooms) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, city);
            statement.setString(2, hotelName);
            statement.setBoolean(3, restaurant);
            statement.setBoolean(4, kidsClub);
            statement.setBoolean(5, pool);
            statement.setBoolean(6, entertainment);
            statement.setString(7, rating);
            statement.setString(8, distanceCity);
            statement.setString(9, distanceBeach);
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
            try {
                statement.executeUpdate();
            } catch (SQLException e) {
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


    public boolean filterRoomsInDatabase(String city, String checkin_date, String checkout_date, int maximum_guests, boolean restaurant, boolean kids_club, boolean pool, boolean entertainment) {
        try {
            statement = connection.prepareStatement("SELECT rooms.id, city, hotel_name, rating, distance_centre, distance_beach, room_type, price_per_night, checkin_date, checkout_date \n" +
                    "FROM destinations \n" +
                    "JOIN rooms\n" +
                    "ON destinations.id = rooms.destination_id \n" +
                    "WHERE city = ? \n" +
                    "AND ? NOT BETWEEN checkin_date AND checkout_date \n" +
                    "AND ? NOT BETWEEN checkin_date AND checkout_date \n" +
                    "AND ? >= maximum_guests  \n" +
                    "AND restaurant = ?\n" +
                    "AND kids_club = ?\n" +
                    "AND pool = ?\n" +
                    "AND entertainment = ?;");

            statement.setString(1, city);
            statement.setString(2, checkin_date);
            statement.setString(3, checkout_date);
            statement.setInt(4, maximum_guests);
            statement.setBoolean(5, restaurant);
            statement.setBoolean(6, kids_club);
            statement.setBoolean(7, pool);
            statement.setBoolean(8, entertainment);

            try {
                resultSet = statement.executeQuery();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }

            if (resultSet.next()) {
                String filterRooms =
                        "ID: " + resultSet.getInt("rooms.id") + "\n" +
                                "CITY: " + resultSet.getString("city") + "\n" +
                                "HOTEL NAME: " + resultSet.getString("hotel_name") + "\n" +
                                "ROOM TYPE: " + resultSet.getString("room_type") + "\n" +
                                "PRICE: " + resultSet.getString("price_per_night") + "\n" +
                                "DISTANCE CENTRE: " + resultSet.getInt("distance_centre") + "\n" +
                                "DISTANCE BEACH: " + resultSet.getInt("distance_beach") + "\n" +
                                "RATING: " + resultSet.getString("rating") + "\n" +
                                "BOOKED DATE CHECK-IN: " + resultSet.getString("checkin_date") + "\n" +
                                "BOOKED DATE CHECKOUT: " + resultSet.getString("checkout_date") + "\n" +
                                "*************************" + "\n";

                System.out.println(filterRooms);
                return true;
            } else {
                System.out.println("No rooms found. Please try with different choices.  " + "\n");
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;

    }

    public void addCustomerToDatabase(String first_name, String last_name, String email, String phonenumber) {

        try {
            statement = connection.prepareStatement("INSERT INTO guest_information (first_name, last_name, email, phonenumber) VALUES (?, ?, ?, ?)");
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, email);
            statement.setString(4, phonenumber);
            try {
                statement.executeUpdate();
            } catch (SQLException e) {
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

    // TEST!
    public void testInsertDates(int guests_id, String checkin_date, String checkout_date) {
        try {
            statement = connection.prepareStatement
                    ("INSERT INTO view_guest_bookings(guest_bookings.guests_id, room_id, checkin_date, checkout_date, total_guests, availability) VALUES(?, 4, CASE WHEN ? BETWEEN '2020-06-01' AND '2020-07-30' THEN ? ELSE NULL END, CASE WHEN ? BETWEEN '2020-06-01' AND '2020-07-30' THEN ? ELSE NULL END, 2);");
            statement.setInt(1, guests_id);
            statement.setString(2, checkin_date);
            statement.setString(3, checkin_date);
            statement.setString(4, checkout_date);
            statement.setString(5, checkout_date);

            try {
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String testPrint(int guests_id) {
        String isNull = "";
        try {
            statement = connection.prepareStatement("SELECT id, checkin_date, checkout_date FROM guest_bookings WHERE checkin_date IS NULL AND checkout_date IS NULL AND guests_id = ? ");
            statement.setInt(1, guests_id);

            try {
                resultSet = statement.executeQuery();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }


            while (resultSet.next()) {
                isNull =
                        "*************************" + "\n" +
                                "ID: " + resultSet.getString("id") + "\n" +
                                "CHECK-IN DATE: " + resultSet.getString("checkin_date") + "\n" +
                                "CHECK-OUT DATE: " + resultSet.getString("checkout_date") + "\n" +
                                "*************************" + "\n";
                System.out.println(isNull);

                if (resultSet.getString("checkin_date") == null) {
                    return "NULL";
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return isNull;
    }

    public void testDeleteDates(int guests_id) {

        try {
            statement = connection.prepareStatement("DELETE FROM guest_bookings WHERE checkin_date IS NULL AND checkout_date IS NULL AND guests_id = ?;");
            statement.setInt(1, guests_id);

            try {
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Filter with city name?
    public void printBookedDates() {
        try {
            statement = connection.prepareStatement("SELECT city, hotel_name, room_type,checkin_date, checkout_date FROM guest_bookings\n" +
                    "JOIN rooms\n" +
                    "ON rooms.id = guest_bookings.room_id\n" +
                    "JOIN destinations\n" +
                    "ON destinations.id = rooms.destination_id");
            resultSet = statement.executeQuery();

            System.out.println("CITY          |          HOTEL NAME          |          ROOM TYPE          |          CHECK-IN          |          CHECKOUT          |" + "\n" +
                    "**************************************************************************************************************************************" + "\n");
            while (resultSet.next()) {
                String city =resultSet.getString("city");
                String hotelName = resultSet.getString("hotel_name");
                String roomtype = resultSet.getString("room_type") ;
                String checkin = resultSet.getString("checkin_date");
                String checkout = resultSet.getString("checkout_date");
                String roomInformation =
                        city + "              |" + "          " + hotelName + "          |" +
                                "          " + roomtype + "          |" + "          " + checkin + "          |" +
                                "          " + checkout + "          |" + "\n" +
                                "**************************************************************************************************************************************" + "\n";
                System.out.println(roomInformation);

                /*"CITY |   HOTEL NAME |  ROOM TYPE |  CHECK-IN |    CHECKOUT |" + "\n" +
                        "***************************************************" + "\n" +
                        "CITY: " + resultSet.getString("city") + "\n" +
                        "HOTEL NAME: " + resultSet.getString("hotel_name") + "\n" +
                        "ROOM TYPE: " + resultSet.getString("room_type") + "\n" +
                        "CHECK-IN: " + resultSet.getString("checkin_date") + "\n" +
                        "CHECKOUT: " + resultSet.getString("checkout_date") + "\n" +
                        "*************************" + "\n";*/
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
