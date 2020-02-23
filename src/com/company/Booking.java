package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Booking {
    private Scanner input = new Scanner(System.in);
    private DatabaseConnection database = new DatabaseConnection();

    public Booking(){
    }

    public void bookRoom(){

        try {
            while (true) {
                // Add reg-ex?
                // Validate check-in and checkout dates
                String[] bookingDates = checkDates();
                String checkInDate = bookingDates[0];
                String checkOutDate = bookingDates[1];

                // Check number of guests
                int numberOfGuests = checkNumberOfGuests();
                // Coded to avoid skipping next line
                input.nextLine();

                // Filter amenities
                // Should be an optional choice?
                boolean[] amenities = filterAmenities();
                boolean restaurant = amenities[0];
                boolean kidsClub = amenities[1];
                boolean pool = amenities[2];
                boolean entertainment = amenities[3];

                // Filter distance to beach and distance to centre
                int[] distanceToBeachAndCentre = distanceSearch();
                int distanceBeach = distanceToBeachAndCentre[0];
                int distanceCentre = distanceToBeachAndCentre[1];
                System.out.println("*************************" + "\n");

                if (database.filterRoomsInDatabase(checkInDate, checkOutDate, numberOfGuests, restaurant, kidsClub, pool, entertainment, false, false, distanceBeach, distanceCentre)) {
                    System.out.println("Would you like to filter this search by rating or price? [Y]/[N]");
                    String filter = input.nextLine();

                    // Filter for ordering by rating or price
                    if(filter.equalsIgnoreCase("Y")){
                        boolean[] filterSearch = filterByRatingPrice();
                        boolean rating = filterSearch[0];
                        boolean price = filterSearch[1];

                        database.filterRoomsInDatabase(checkInDate, checkOutDate, numberOfGuests, restaurant, kidsClub, pool, entertainment, rating, price, distanceBeach, distanceCentre);
                    }

                        System.out.println("Would you like to proceed the booking? [Y]/[N]");
                        String choice = input.nextLine();

                        if (choice.equalsIgnoreCase("Y")) {
                            System.out.println("Please enter the room ID you would like to book: ");
                            int roomID = input.nextInt();
                            System.out.println("Room ID " + roomID);
                            input.nextLine();

                            // Add check-in and checkout dates to database
                            // Return booked dates ID for bookings table
                            String bookedDatesID = database.addBookingRoom(roomID, checkInDate, checkOutDate);
                            int datesID = Integer.parseInt(bookedDatesID);

                            // Meal choice
                            System.out.println("Please enter additional meal choices [half board]/[full board]/[none]");
                            String mealChoice = input.nextLine();

                            // Additional bed choice
                            System.out.println("Additional bed? [yes]/[no]");
                            String additionalBed = input.nextLine();

                            // Add additional choices to database
                            String additionalChoice = database.addAdditionalChoices(mealChoice, additionalBed, roomID, datesID);
                            int choiceID = Integer.parseInt(additionalChoice);


                            // Register new guest and get guest ID
                            if(addCustomerToBooking()){
                                int guestID = addExistingCustomer();
                                database.finishBooking(guestID, roomID, choiceID, datesID, numberOfGuests);
                                System.out.println("Booking successfully added." + "\n" +
                                        "Booking ID: " +guestID + "\n");
                                database.getTotalPrice(roomID, datesID, guestID);
                            }
                            else{
                                int guestID = registerNewCustomer();
                                database.finishBooking(guestID, roomID, choiceID, datesID, numberOfGuests);
                                System.out.println("Booking successfully added." + "\n" +
                                        "Booking ID: " +guestID + "\n");
                                database.getTotalPrice(roomID, datesID, guestID);
                            }

                            break;

                        } else {
                            break;
                        }

                    }

                else {
                    System.out.println("There are no results that match your search. Please try again.  ");
                    return;
                }

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void changeBooking(){
        try{
            System.out.println("Add room ID: ");
            int bookingID = input.nextInt();
            input.nextLine();

            System.out.println("UPDATE: " + "\n" +
                    "[1] Customer information" + "\n" +
                    "[2] Number of guests" + "\n" +
                    "[3] Check-in and/or checkout date");
            String choice = input.nextLine();

            switch (choice){
                case "1":
                    changeCustomerInformation();
                    break;

                case "2":
                    changeNumberOfGuests(bookingID);
                    break;

                case "3":
                    changeCheckinCheckoutDate();
                    break;

                default:
                    break;

            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void changeCheckinCheckoutDate(){
        try{
            System.out.println("Enter room ID: ");
            String stringRoomID = input.nextLine();
            int roomID = Integer.parseInt(stringRoomID);
            System.out.println("Enter new check-in and checkout dates below. " + "\n");
            String[] bookingDates = checkDates();
            String checkInDate = bookingDates[0];
            String checkOutDate = bookingDates[1];
            database.changeCheckinCheckoutDate(roomID, checkInDate, checkOutDate );
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    private void changeNumberOfGuests(int bookingID){
        try{
            System.out.println("Enter total guests for booking: ");
            int intTotalGuests = input.nextInt();
            input.nextLine();
            database.changeNumberOfGuests(intTotalGuests, bookingID);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void changeCustomerInformation(){
        try{
        System.out.println("Please enter customer ID: ");
        int conditionEquals = input.nextInt();
        input.nextLine();

        System.out.println("Change: " + "\n" +
                "[1] First name" + "\n" +
                "[2] Last name" + "\n" +
                "[3] E-mail" + "\n" +
                "[4] Phone number");

            String choice = input.nextLine();

            switch (choice){
                case "1":
                    System.out.println("Enter new first name: ");
                    String newFirstName = input.nextLine();
                    database.changeCustomerBooking("guest_information", "first_name", newFirstName, "id", conditionEquals);
                    break;

                case "2":
                    System.out.println("Enter new last name: ");
                    String newLastName = input.nextLine();
                    database.changeCustomerBooking("guest_information", "last_name", newLastName, "id", conditionEquals);
                    break;

                case "3":
                    System.out.println("Enter new email: ");
                    String newEmail = input.nextLine();
                    database.changeCustomerBooking("guest_information", "email", newEmail, "id", conditionEquals);
                    break;

                case "4":
                    System.out.println("Enter new phone number: ");
                    String newPhoneNumber = input.nextLine();
                    database.changeCustomerBooking("guest_information", "phonenumber", newPhoneNumber, "id", conditionEquals);
                    break;
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private boolean addCustomerToBooking(){
        int choice;
        while (true) {
            System.out.println("Select choice:" + "\n" +
                    "[1] Register new customer" + "\n" +
                    "[2] Find existing customer");
            try {
                choice = input.nextInt();
                input.nextLine();
                if (choice == 1) {
                    return false;
                }
                if (choice == 2) {
                    return true;
                } else {
                    System.out.println("You can only make an option 1 or 2. Please try again. ");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private int addExistingCustomer(){
        String guestID = "";

        while (true) {
            try {
                    System.out.println("Enter first name: ");
                    String firstName = input.nextLine();
                    System.out.println("Enter last name: ");
                    String lastName = input.nextLine();
                    System.out.println("Enter e-mail: ");
                    String email = input.nextLine();
                    guestID = database.getCustomerID(firstName, lastName, email);
                    break;
                }
            catch (Exception  e) {
                e.printStackTrace();
            }
        }
        return Integer.parseInt(guestID);
        }

    // Filter distance to beach and centre
    private int []distanceSearch(){
        while (true) {
            try {
                System.out.println("Minimum distance in metres to beach: ");
                int distanceBeach = input.nextInt();
                System.out.println("Minimum distance to centre: ");
                int distanceCentre = input.nextInt();
                input.nextLine();
                return new int[]{distanceBeach, distanceCentre};
            }
            catch (InputMismatchException e){
                input.nextLine();
            }
        }
    }

    private int registerNewCustomer(){
        String guestID = "";

        try{
            System.out.println("First name: ");
            String firstName = input.nextLine();
            System.out.println("Last name: ");
            String lastName = input.nextLine();
            System.out.println("E-mail: ");
            String email = input.nextLine();
            System.out.println("Phone number: ");
            String phoneNumber = input.nextLine();

            database.addCustomerToDatabase(firstName, lastName, email, phoneNumber);
            guestID = database.getCustomerID(firstName, lastName, email);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return Integer.parseInt(guestID);
    }

    private boolean[] filterByRatingPrice() {
        int choice;
        boolean rating = false;
        boolean price = false;

        while (true) {
            System.out.println("Filter search by " + "\n" +
                    "[1] Rating" + "\n" +
                    "[2] Price (low to high)");
            try {
                choice = input.nextInt();
                input.nextLine();
                if (choice == 1) {
                    rating = true;
                    break;
                }
                if (choice == 2) {
                    price = true;
                    break;
                } else {
                    System.out.println("You can only make an option 1 or 2. Please try again. ");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new boolean[]{rating, price};
    }

    public void cancelBooking(){
        try{
            System.out.println("Enter the booking ID to cancel the booking: ");
            int bookingID = input.nextInt();
            input.nextLine();
            System.out.println("You will now cancel the booking with ID " + bookingID + "\n" +
                    "Proceed? [Y]/[N]");
            String choice = input.nextLine();

            if(choice.equals("Y") || choice.equals("y")){
                database.cancelBooking(bookingID);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    // Add reg-ex?
    private String[] checkDates() {
        while (true) {
            try {
                System.out.println("Please enter check-in date. Format should be YYYY-MM-DD. ");
                String checkInDate = input.nextLine();
                System.out.println("Please enter check-out date. Format should be YYYY-MM-DD. ");
                String checkOutDate = input.nextLine();
                if (!database.checkBookingDates(checkInDate, checkOutDate)) {
                    System.out.println("Something went wrong please try again");
                } else {
                    return new String[] {checkInDate, checkOutDate};
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int checkNumberOfGuests(){
        int numberOfGuests;
        while(true){
            System.out.println("Please select number of guests");
            try{
                numberOfGuests = input.nextInt();
                return numberOfGuests;
            }
            catch (InputMismatchException e){
                input.nextLine();
            }
        }
    }

    private boolean[] filterAmenities() {
        boolean restaurant = false;
        boolean kidsClub = false;
        boolean pool = false;
        boolean entertainment = false;

        System.out.println(
                "Please fill in your choices by entering the numbers in one line (example 1, 4)" + "\n" +
                        "[1] Restaurant" + "\n" +
                        "[2] Kids club" + "\n" +
                        "[3] Pool" + "\n" +
                        "[4] Entertainment" + "\n");
        String choice = input.nextLine();

        char[] array = choice.toCharArray();
        for (char c : array) {
            switch (c) {
                case '1':
                    restaurant = true;
                    break;

                case '2':
                    kidsClub = true;
                    break;

                case '3':
                    pool = true;
                    break;

                case '4':
                    entertainment = true;
                    break;
            }
        }

        return new boolean[]{restaurant, kidsClub, pool, entertainment};
    }

    // Search bookings by first name and last name
    public void searchBooking(){
        try{
            System.out.println("Customer first name: ");
            String firstName = input.nextLine();
            System.out.println("Customer last name: ");
            String lastName = input.nextLine();
            System.out.println("Customer email: ");
            String email = input.nextLine();
            if (!database.searchBookingAndPrint(firstName, lastName, email)) {
                System.out.println("Sorry we could not find the name " +firstName+ " " +lastName);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    }



