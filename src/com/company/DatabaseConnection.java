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

    public String addDestinationToDatabase(String city, String hotelName, boolean restaurant, boolean kidsClub,
                                         boolean pool, boolean entertainment, String rating, String distanceCity,
                                         String distanceBeach, int numberOfRooms) {
        StringBuilder ID = new StringBuilder();


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
            try {
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }

            statement = connection.prepareStatement("SELECT id FROM destinations \n" +
                    "WHERE city = ?\n" +
                    "AND hotel_name = ?\n" +
                    "AND restaurant = ?\n" +
                    "AND kids_club = ?\n" +
                    "AND pool = ?\n" +
                    "AND entertainment = ?\n" +
                    "AND rating = ?\n" +
                    "AND distance_centre = ?\n" +
                    "AND distance_beach = ?\n" +
                    "AND number_of_rooms = ?; ");
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

            try {
                resultSet = statement.executeQuery();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }
            while (resultSet.next()){
                ID.append(resultSet.getString("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ID.toString();
    }


    public void addAdditionalChoicesPrice(int destination_id, int half_board, int full_board, int additional_bed){
        try {
            statement = connection.prepareStatement("INSERT INTO additional_prices\n" +
                    "SET destination_id = ?, half_board = ?, full_board = ?, additional_bed = ?;\n");
            statement.setInt(1, destination_id);
            statement.setInt(2, half_board);
            statement.setInt(3, full_board);
            statement.setInt(4, additional_bed);

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

    public boolean checkBookingDates(String checkin_date, String checkout_date){
        try {
            statement = connection.prepareStatement("CREATE OR REPLACE VIEW check_booking_dates AS SELECT * from bookings WHERE ? > ?\n" +
                    "AND ? BETWEEN '2020-06-01' AND '2020-07-30'\n" +
                    "AND ? BETWEEN '2020-06-02' AND '2020-07-31'");
            statement.setString(1, checkout_date);
            statement.setString(2, checkin_date);
            statement.setString(3, checkout_date);
            statement.setString(4, checkin_date);
            try{
                statement.executeUpdate();
                System.out.println("Created view. ");
            }
            catch (Exception e){
                e.printStackTrace();
            }

            statement = connection.prepareStatement("SELECT * FROM check_booking_dates\n" +
                    "WHERE ? NOT BETWEEN checkin_date AND checkout_date\n" +
                    "AND ? NOT BETWEEN checkin_date AND checkout_date\n" +
                    "OR checkin_date IS NULL\n" +
                    "OR checkout_date IS NULL;");

            statement.setString(1, checkin_date);
            statement.setString(2, checkout_date);

            try {
                resultSet = statement.executeQuery();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }
            return resultSet.next();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public boolean filterRoomsInDatabase(String checkin_date, String checkout_date, int maximum_guests, boolean restaurant, boolean kids_club, boolean pool, boolean entertainment, boolean filterRating,
                                   boolean filterPrice, int distance_beach, int distance_centre){
        try {
            String sqlFilterRooms = "CREATE OR REPLACE VIEW filter_rooms AS SELECT id, city, hotel_name, rating, distance_beach, distance_centre, room_type, price_per_night, checkin_date, \n" +
                    "checkout_date FROM bookings\n" +
                    "WHERE ? NOT BETWEEN checkin_date AND checkout_date\n" +
                    "AND ? NOT BETWEEN checkin_date AND checkout_date\n" +
                    "AND ? BETWEEN '2020-06-01' AND '2020-07-30'\n" +
                    "AND ? BETWEEN '2020-06-02' AND '2020-07-31'\n" +
                    "AND ? > ? \n" +
                    "OR checkin_date IS NULL\n" +
                    "OR checkout_date IS NULL\n" +
                    "AND maximum_guests >= ? " +
                    "AND restaurant = ? \n" +
                    "AND kids_club = ? \n" +
                    "AND pool = ? \n" +
                    "AND entertainment = ? \n" +
                    "AND distance_beach <= ? \n" +
                    "AND distance_centre <= ?;";

            String sqlFilterRating = "SELECT * FROM filter_rooms ORDER BY rating DESC;";

            String sqlFilterPrice = "SELECT * FROM filter_rooms GROUP BY price_per_night ORDER BY price_per_night ASC;";


            if(filterRating){
                statement = connection.prepareStatement(sqlFilterRating);
                resultSet = statement.executeQuery();
            }
            else if(filterPrice){
                statement = connection.prepareStatement(sqlFilterPrice);
                resultSet = statement.executeQuery();
            }
            else{
                statement = connection.prepareStatement(sqlFilterRooms);

                statement.setString(1, checkin_date);
                statement.setString(2, checkout_date);
                statement.setString(3, checkin_date);
                statement.setString(4, checkout_date);
                statement.setString(5, checkout_date);
                statement.setString(6, checkin_date);
                statement.setInt(7, maximum_guests);
                statement.setBoolean(8, restaurant);
                statement.setBoolean(9, kids_club);
                statement.setBoolean(10, pool);
                statement.setBoolean(11, entertainment);
                statement.setInt(12, distance_beach);
                statement.setInt(13, distance_centre);

                try {
                    statement.executeUpdate();
                    System.out.println("Updated view. ");
                } catch (NullPointerException e) {
                    System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
                }
            }

            try {
                resultSet = statement.executeQuery();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }

            if(resultSet.next()){
                while(resultSet.next()){
                    String filterRooms =
                            "ID: " + resultSet.getInt("id") + "\n" +
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

                }
                return true;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;

    }

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

    public String addAdditionalChoices(String meal_choice, String additional_bed, int room_id, int booked_dates_id){
        StringBuilder ID = new StringBuilder();
        try {
            // Step 1: Insert new data
            statement = connection.prepareStatement("INSERT INTO additional_choices(room_id, booked_dates_id, meal_choice, additional_bed) VALUES(?, ?, ?, ?);");
            statement.setInt(1, room_id);
            statement.setInt(2, booked_dates_id);
            statement.setString(3, meal_choice);
            statement.setString(4, additional_bed);

            try {
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }

            // Step 2: Selecting and retrieving ID from INSERT query above
            statement = connection.prepareStatement("SELECT choice_ID FROM additional_choices \n" +
                    "WHERE meal_choice = ?\n" +
                    "AND additional_bed = ?\n" +
                    "AND room_id = ?\n" +
                    "AND booked_dates_id = ?; ");
            statement.setString(1, meal_choice);
            statement.setString(2, additional_bed);
            statement.setInt(3, room_id);
            statement.setInt(4, booked_dates_id);

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
                ID.append(resultSet.getString("id"));
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
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

    // Get information from bookings view instead?
    public boolean searchBookingAndPrint(String first_name, String last_name, String email) {
        try {
            statement = connection.prepareStatement("SELECT id, first_name, last_name, city, hotel_name, checkin_date, checkout_date FROM all_booked_guests\n" +
                    "WHERE first_name = ?\n" +
                    "AND last_name = ?\n" +
                    "AND email = ?;");

            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, email);

            try {
                resultSet = statement.executeQuery();
            } catch (SQLException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }

            while (resultSet.next()) {
                String roomInformation =
                        "*************************" + "\n" +
                                "BOOKING ID: " + resultSet.getString("id") + "\n" +
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

    public void searchGuestAndPrint(String first_name, String last_name){
        try {
            statement = connection.prepareStatement("SELECT * FROM guest_information\n" +
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
                                "GUEST ID: " + resultSet.getString("id") + "\n" +
                                "FIRST NAME: " + resultSet.getString("first_name") + "\n" +
                                "LAST NAME: " + resultSet.getString("last_name") + "\n" +
                                "E-MAIL: " + resultSet.getString("email") + "\n" +
                                "PHONE NUMBER: " + resultSet.getString("phonenumber") + "\n" +
                                "*************************" + "\n";
                System.out.println(roomInformation);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    public void changeCustomerBooking(String tableName, String columnName, String newValue, String columnID, int id){
        String sql = "UPDATE " + tableName + " SET `" + columnName + "` = ? WHERE " + columnID + " =  ?";
        try {

            statement = connection.prepareStatement(sql);
            statement.setString(1, newValue);
            statement.setInt(2, id);

            try {
                statement.executeUpdate();
                String updatedInformation =
                        "*********************************************" + "\n" +
                                "CHANGED VALUE: " + columnName + "\n" +
                                "UPDATED INFORMATION: " + newValue + "\n" +
                                "*********************************************" + "\n";
                System.out.println(updatedInformation);
            } catch (NullPointerException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void changeNumberOfGuests(int total_guests, int id){
        String sql = "UPDATE all_booked_guests\n" +
                "SET total_guests = ?\n" +
                "WHERE ?<= maximum_guests\n" +
                "AND id = ?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, total_guests);
            statement.setInt(2, total_guests);
            statement.setInt(3, id);

            try {
                statement.executeUpdate();
                String updatedInformation =
                        "*********************************************" + "\n" +
                                "CHANGED VALUE: total_guests" + "\n" +
                                "UPDATED INFORMATION: " + total_guests + "\n" +
                                "*********************************************" + "\n";
                System.out.println(updatedInformation);
            } catch (NullPointerException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void changeCheckinCheckoutDate(int room_id, String checkin_date, String checkout_date){
        String sql = "UPDATE booked_dates\n" +
                "SET checkin_date = ?, checkout_date = ?\n" +
                "WHERE room_id = ?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, checkin_date);
            statement.setString(2, checkout_date);
            statement.setInt(3, room_id);

            try {
                statement.executeUpdate();
                String updatedInformation =
                        "*********************************************" + "\n" +
                                "CHANGED VALUE: booked_dates" + "\n" +
                                "UPDATED INFORMATION CHECK-IN DATE: " + checkin_date + "\n" +
                                "UPDATED INFORMATION CHECKOUT DATE: " + checkout_date + "\n" +
                                "*********************************************" + "\n";
                System.out.println(updatedInformation);
            } catch (NullPointerException e) {
                System.out.println("Error message: " + "\n" + e.getMessage() + "\n");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }





}


