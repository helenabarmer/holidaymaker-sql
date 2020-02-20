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

    public void addRoomToDatabase(int destination_id, String room_type, int price_per_night, int maximum_guests) {
        try {
            statement = connection.prepareStatement("INSERT INTO rooms (destination_id, room_type, price_per_night, maximum_guests) VALUES (?, ?, ?, ?)");
            statement.setInt(1, destination_id);
            statement.setString(2, room_type);
            statement.setInt(3, price_per_night);
            statement.setInt(4, maximum_guests);
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
            statement = connection.prepareStatement("SELECT rooms.id, city, hotel_name, rating, distance_centre, distance_beach, room_type, price_per_night, booked_dates.checkin_date, \n" +
                    "booked_dates.checkout_date \n" +
                    "FROM destinations\n" +
                    "JOIN rooms\n" +
                    "ON destinations.id = rooms.destination_id\n" +
                    "JOIN booked_dates\n" +
                    "ON rooms.id = booked_dates.room_id\n" +
                    "WHERE city = ?\n" +
                    "AND ? NOT BETWEEN booked_dates.checkin_date AND booked_dates.checkout_date\n" +
                    "AND ? NOT BETWEEN booked_dates.checkin_date AND booked_dates.checkout_date\n" +
                    "AND ? BETWEEN '2020-05-31' AND '2020-07-30'\n" +
                    "AND ? BETWEEN '2020-06-02' AND '2020-07-31'\n" +
                    "AND ? > ? \n" +
                    "AND maximum_guests >= ? " +
                    "AND restaurant = ? \n" +
                    "AND kids_club = ? \n" +
                    "AND pool = ? \n" +
                    "AND entertainment = ?;");

            statement.setString(1, city);
            statement.setString(2, checkin_date);
            statement.setString(3, checkout_date);
            statement.setString(4, checkin_date);
            statement.setString(5, checkout_date);
            statement.setString(6, checkout_date);
            statement.setString(7, checkin_date);
            statement.setInt(8, maximum_guests);
            statement.setBoolean(9, restaurant);
            statement.setBoolean(10, kids_club);
            statement.setBoolean(11, pool);
            statement.setBoolean(12, entertainment);

            try {
                resultSet = statement.executeQuery();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }

            if(resultSet.next()){
            while(resultSet.next()){
                String filterRooms =
                        "ID: " + resultSet.getInt("rooms.id") + "\n" +
                                "CITY: " + resultSet.getString("city") + "\n" +
                                "HOTEL NAME: " + resultSet.getString("hotel_name") + "\n" +
                                "ROOM TYPE: " + resultSet.getString("room_type") + "\n" +
                                "PRICE: " + resultSet.getString("price_per_night") + "\n" +
                                "DISTANCE CENTRE: " + resultSet.getInt("distance_centre") + "\n" +
                                "DISTANCE BEACH: " + resultSet.getInt("distance_beach") + "\n" +
                                "RATING: " + resultSet.getString("rating") + "\n" +
                                "BOOKED DATE CHECK-IN: " + resultSet.getString("booked_dates.checkin_date") + "\n" +
                                "BOOKED DATE CHECKOUT: " + resultSet.getString("booked_dates.checkout_date") + "\n" +
                                "*************************" + "\n";

                System.out.println(filterRooms);

            }
            return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    // Add booked dates to bookings table?
    public String addBookingRoom(int room_id, String checkin_date, String checkout_date){
        StringBuilder ID = new StringBuilder();

        // Step 1: INSERT new data
        try {
            statement = connection.prepareStatement("INSERT INTO booked_dates(room_id, checkin_date, checkout_date)\n" +
                    "VALUES(?, ?, ?);");
            statement.setInt(1, room_id);
            statement.setString(2, checkin_date);
            statement.setString(3, checkout_date);

            System.out.println("Booking added. ");
            try {
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }


            // Step 2: Selecting and retrieving ID from INSERT query above
            statement = connection.prepareStatement("SELECT booked_ID FROM booked_dates \n" +
                    "WHERE room_id = ?\n" +
                    "AND checkin_date = ? " +
                    "AND checkout_date = ?; ");
            statement.setInt(1, room_id);
            statement.setString(2, checkin_date);
            statement.setString(3, checkout_date);

            try {
                resultSet = statement.executeQuery();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }
            while (resultSet.next()){
                ID.append(resultSet.getString("booked_ID"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ID.toString();
    }

    public String addAdditionalChoices(String meal_choice, String additional_bed){
        StringBuilder ID = new StringBuilder();
        try {
            // Step 1: Insert new data
            statement = connection.prepareStatement("INSERT INTO additional_choices(meal_choice, additional_bed) VALUES(?, ?);");
            statement.setString(1, meal_choice);
            statement.setString(2, additional_bed);
            System.out.println("STEP 1:  choices added. ");
            try {
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }

            // Step 2: Selecting and retrieving ID from INSERT query above
            statement = connection.prepareStatement("SELECT choice_ID FROM additional_choices \n" +
                    "WHERE meal_choice = ?\n" +
                    "AND additional_bed = ?; ");
            statement.setString(1, meal_choice);
            statement.setString(2, additional_bed);

            try {
                resultSet = statement.executeQuery();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }
            while (resultSet.next()){
                ID.append(resultSet.getString("choice_ID"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ID.toString();

    }



    public void addCustomerToDatabase(String first_name, String last_name, String email, String phonenumber) {

        try {
            statement = connection.prepareStatement("INSERT INTO guest_information (first_name, last_name, email, phonenumber) VALUES (?, ?, ?, ?)");
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, email);
            statement.setString(4, phonenumber);

            System.out.println("New customer " + first_name + " " + last_name + " added. " + "\n");

            try {
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getCustomerID(String first_name, String last_name) {
        StringBuilder ID = new StringBuilder();

        try {
            statement = connection.prepareStatement("SELECT id FROM guest_information\n" +
                    "WHERE first_name = ?\n" +
                    "AND last_name = ?");

            statement.setString(1, first_name);
            statement.setString(2, last_name);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                ID.append(resultSet.getString("ID"));
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println(ID);
        return ID.toString();
    }

    public void finishBooking(int guests_id, int room_id, int additional_choices_id, int booked_dates_id, int total_guests){
        try {
            statement = connection.prepareStatement("INSERT INTO guest_bookings (guests_id, room_id, additional_choices_id, booked_dates_id, total_guests) VALUES (?, ?, ?, ?, ?);");
            statement.setInt(1, guests_id);
            statement.setInt(2, room_id);
            statement.setInt(3, additional_choices_id);
            statement.setInt(4, booked_dates_id);
            statement.setInt(5, total_guests);
            try {
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean searchCustomerAndPrint(String first_name, String last_name) {
        try {
            statement = connection.prepareStatement("SELECT guest_bookings.id, first_name, last_name, city, hotel_name, checkin_date, checkout_date FROM guest_bookings\n" +
                    "JOIN guest_information\n" +
                    "ON guest_information.id = guest_bookings.guests_id\n" +
                    "JOIN booked_dates\n" +
                    "ON booked_dates.booked_ID = guest_bookings.booked_dates_id\n" +
                    "JOIN rooms\n" +
                    "ON rooms.id = guest_bookings.id\n" +
                    "JOIN destinations\n" +
                    "ON destinations.id = rooms.destination_id\n" +
                    "WHERE first_name = ?\n" +
                    "AND last_name = ?;");
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
                                "BOOKING ID: " + resultSet.getString("guest_bookings.id") + "\n" +
                                "CITY: " + resultSet.getString("city") + "\n" +
                                "HOTEL NAME: " + resultSet.getString("hotel_name") + "\n" +
                                "FIRST NAME: " + resultSet.getString("first_name") + "\n" +
                                "LAST NAME: " + resultSet.getString("last_name") + "\n" +
                                "CHECK-IN DATE: " + resultSet.getString("checkin_date") + "\n" +
                                "CHECKOUT DATE: " + resultSet.getString("checkout_date") + "\n" +
                                "*************************" + "\n";
                System.out.println(roomInformation);

            }
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void cancelBooking(int id){
        try {
            statement = connection.prepareStatement("DELETE guest_bookings, additional_choices, booked_dates  FROM guest_bookings\n" +
                    "INNER JOIN additional_choices\n" +
                    "ON additional_choices.choice_ID = guest_bookings.additional_choices_id\n" +
                    "INNER JOIN booked_dates\n" +
                    "ON booked_dates.booked_ID = guest_bookings.booked_dates_id\n" +
                    "WHERE guest_bookings.id = ?;\n");
            statement.setInt(1, id);
            System.out.println("Booking with ID " + id + " is now cancelled. ");

            try {
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }
        }
        catch (Exception e) {
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


