package com.company;

import java.util.Scanner;

public class Booking {
    private Scanner input = new Scanner(System.in);
    private DatabaseConnection database = new DatabaseConnection();

    public Booking(){
    }


    public void bookRoom(){

        try {
            while (true) {
                System.out.println("********** ROOM BOOKING MENU **********" + "\n" +
                        "Please enter a destination: " + "\n" +
                        "[Barentsburg] [Stavanger] [Trondheim] [Mo I Rana] [Longyearbyen]");
                String city = input.nextLine();

                // Add reg-ex?
                System.out.println("Please enter check-in date. Format should be YYYY/MM/DD. ");
                String checkInDate = input.nextLine();

                System.out.println("Please enter check-out date. Format should be YYYY/MM/DD. ");
                String checkOutDate = input.nextLine();

                System.out.println("Please select number of guests");
                int numberOfGuests = input.nextInt();
                // Coded to avoid skipping next scanner
                input.nextLine();

                System.out.println("Restaurant [true]/[false]: ");
                boolean restaurant = input.nextBoolean();

                System.out.println("Kids club [true]/[false]: ");
                boolean kidsClub = input.nextBoolean();

                System.out.println("Pool [true]/[false]: ");
                boolean pool = input.nextBoolean();

                System.out.println("Entertainment [true]/[false]: ");
                boolean entertainment = input.nextBoolean();
                input.nextLine();
                System.out.println("*************************" + "\n");


                if (database.filterRoomsInDatabase(city, checkInDate, checkOutDate, numberOfGuests, restaurant, kidsClub, pool, entertainment)) {
                    System.out.println("Would you like to proceed the booking? [Y]/[N]");
                    String choice = input.nextLine();

                    if (choice.equals("Y") || choice.equals("y")) {
                        System.out.println("Please enter the room ID you would like to book: ");
                        int roomID = input.nextInt();
                        input.nextLine();

                        // Add check-in and checkout dates to database
                        // Return booked dates ID for bookings table?
                        String bookedDatesID = database.addBookingRoom(roomID, checkInDate, checkOutDate);
                        int datesID = Integer.parseInt(bookedDatesID);

                        // Meal choice
                        System.out.println("Please enter additional meal choices [half board]/[full board]/[NULL]");
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
                        return;
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
        System.out.println("First name: ");
        String firstName = input.nextLine();
        System.out.println("Last name: ");
        String lastName = input.nextLine();
        System.out.println("E-mail: ");
        String email = input.nextLine();
        System.out.println("Phone number: ");
        String phoneNumber = input.nextLine();

        database.addCustomerToDatabase(firstName, lastName, email, phoneNumber);
        String guestID = database.getCustomerID(firstName, lastName);
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


}
