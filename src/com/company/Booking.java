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
                /*System.out.println("********** ROOM BOOKING MENU **********" + "\n" +
                        "Please enter a destination: " + "\n" +
                        "[Barentsburg] [Stavanger] [Trondheim] [Mo I Rana] [Longyearbyen]");
                String city = input.nextLine();*/


                //Additional choices
                // Distance to beach
                // Distance to centre
                // price, low to high
                // Rating, high to low

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
                boolean[] amenities = filterAmenities();
                boolean restaurant = amenities[0];
                boolean kidsClub = amenities[1];
                boolean pool = amenities[2];
                boolean entertainment = amenities[3];

                System.out.println("*************************" + "\n");


                if (database.filterRoomsInDatabase(checkInDate, checkOutDate, numberOfGuests, restaurant, kidsClub, pool, entertainment)) {
                    System.out.println("Would you like to proceed the booking? [Y]/[N]");
                    String choice = input.nextLine();

                    if (choice.equalsIgnoreCase("Y")) {
                        System.out.println("Please enter the room ID you would like to book: ");
                        int roomID = input.nextInt();
                        input.nextLine();

                        // Add check-in and checkout dates to database
                        // Return booked dates ID for bookings table?
                        String bookedDatesID = database.addBookingRoom(roomID, checkInDate, checkOutDate);
                        int datesID = Integer.parseInt(bookedDatesID);

                        // Meal choice
                        System.out.println("Please enter additional meal choices [half board]/[full board]/[none]");
                        String mealChoice = input.nextLine();

                        // Additional bed choice
                        System.out.println("Additional bed? [yes]/[no]");
                        String additionalBed = input.nextLine();

                        // Add additional choices to database
                        String additionalChoice = database.addAdditionalChoices(mealChoice, additionalBed);
                        int choiceID = Integer.parseInt(additionalChoice);

                        // Register new guest and get guest ID
                        int guestID = registerCustomer();

                        // Finish booking and add to booking table in database
                        database.finishBooking(guestID, roomID, choiceID, datesID, numberOfGuests);
                        break;

                    } else {
                        break;
                    }
                } else {
                    System.out.println("Something went wrong. Please try again. ");
                    return;
                }

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private int registerCustomer(){
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
            guestID = database.getCustomerID(firstName, lastName);

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return Integer.parseInt(guestID);
    }

    public void cancelBooking(){
        try{
            System.out.println("Enter the booking ID to cancel the booking: ");
            int bookingID = input.nextInt();
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

    // Validate check-in and checkout dates
    // Add reg-ex?
    private String[] checkDates() {
        while (true) {
            try {
                System.out.println("Please enter check-in date. Format should be YYYY/MM/DD. ");
                String checkInDate = input.nextLine();
                System.out.println("Please enter check-out date. Format should be YYYY/MM/DD. ");
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

    }



