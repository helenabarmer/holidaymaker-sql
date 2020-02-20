package com.company;

import java.util.Scanner;

public class Booking {
    private Scanner input = new Scanner(System.in);
    private DatabaseConnection database = new DatabaseConnection();
    //Customer customer = new Customer();

    public Booking(){
    }

    // TEST
    private void testBookARoom(int guestID){


        //Check for valid date
        while(true){
            System.out.println("Check-in date: ");
            String checkin = input.nextLine();
            System.out.println("Checkout-date: ");
            String checkout = input.nextLine();

            // guest_bookings.guests_id, room_id, checkin_date, checkout_date, total_guests, availability

            database.testInsertDates(guestID, checkin, checkout);
            if(database.testPrint(guestID).equals("NULL")){
                System.out.println("Not a valid date to enter. Please try again. ");
                database.testDeleteDates(guestID);
            }
            else{
                System.out.println("VALID! ");

                System.out.println("Number of guests: ");
                int numberOfGuests = input.nextInt();

                System.out.println("*** Additional choices ***");

                System.out.println("Restaurant [true]/[false]: ");
                boolean restaurant = input.nextBoolean();

                System.out.println("Kids club [true]/[false]: ");
                boolean kidsClub = input.nextBoolean();

                System.out.println("Pool [true]/[false]: ");
                boolean pool = input.nextBoolean();

                System.out.println("Entertainment [true]/[false]: ");
                boolean entertainment = input.nextBoolean();
                System.out.println("*************************" + "\n");

               // database.filterRoomsInDatabase(numberOfGuests, restaurant, kidsClub, pool, entertainment, true);
                break;
            }
        }
    }


    public void bookRoom(){

        while(true) {
            System.out.println("********** ROOM BOOKING MENU **********" + "\n" +
                    "Please enter a destination: " + "\n" +
                    "[Barentsburg] [Stavanger] [Trondheim] [Mo I Rana] [Longyearbyen]");
            String city = input.nextLine();

            System.out.println("Please enter check-in date. Format should be YYYY/MM/DD. ");
            String checkInDate = input.nextLine();

            // Make sure checkout date can not be before check-in date
            System.out.println("Please enter check-out date. Format should be YYYY/MM/DD. ");
            String checkOutDate = input.nextLine();

            // Not sure if should int or String, check database later
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


            if(database.filterRoomsInDatabase(city, checkInDate, checkOutDate, numberOfGuests, restaurant, kidsClub, pool, entertainment)){
                System.out.println("Would you like to proceed the booking? [Y]/[N]");
                String choice = input.nextLine();

                if(choice.equals("Y") || choice.equals("y")){
                    System.out.println("Please enter the room ID you would like to book: ");
                    int roomID = input.nextInt();
                    input.nextLine();

                    // Booking step 1
                    database.addBookingRoom(roomID, checkInDate, checkOutDate);

                    // Adding additional choices
                    System.out.println("Please enter additional meal choices [half board]/[full board]/[NULL]");
                    String mealChoice = input.nextLine();

                    System.out.println("Additional bed? [yes]/[no]");
                    String additionalBed = input.nextLine();

                    // Booking step 2, additional choices
                    database.addAdditionalChoices(roomID, mealChoice, additionalBed);

                    // Get price and proceed to payment

                    // Register new guest and get guest ID
                    int guestID = registerCustomer();

                    // Finish booking
                    database.finishBooking(guestID, roomID,numberOfGuests);
                    break;
                }
                else{
                    return;
                }
            }
            else{
                System.out.println("Something went wrong. Please try again. ");
                return;
            }
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


}
