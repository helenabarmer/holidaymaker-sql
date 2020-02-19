package com.company;

import java.util.Scanner;

public class Booking {
    private Scanner input = new Scanner(System.in);
    private DatabaseConnection database = new DatabaseConnection();


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

    public void filterAndBook(int customerID){

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

        //database.filterRoomsInDatabase(numberOfGuests, restaurant, kidsClub, pool, entertainment, true);

        System.out.println("Would you like to book one of these rooms? [Y]/[N] ");
        String bookRoom = input.nextLine();

        if(bookRoom.equals("Y") || bookRoom.equals("y")){
            System.out.println("Please enter the room ID: ");
            int roomID = input.nextInt();
            input.nextLine();
            System.out.println("Please enter check-in date: " );
            String checkinDate = input.nextLine();
            System.out.println("Please enter check-out date: ");
            String checkoutDate = input.nextLine();
            System.out.println("Please enter additional meal choices [half board]/[full board]/[NULL]");
            String mealChoice = input.nextLine();
            System.out.println("Additional bed required? [yes]/[no]");
            String additionalBed = input.nextLine();

        }

    }


    public void bookRoom(){

        while(true) {
            System.out.println("********** ROOM BOOKING MENU **********" + "\n" +
                    "Please enter a destination: " + "\n" +
                    "[Svalbard] [Oslo] [Stavanger] [Bergen] [Trondheim]");
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

            // Tested and working! 2020-02-20
            if(database.filterRoomsInDatabase(city, checkInDate, checkOutDate, numberOfGuests, restaurant, kidsClub, pool, entertainment)){
                System.out.println("Would you like to proceed the booking? [Y]/[N]");
                String choice = input.nextLine();

                if(choice.equals("Y") || choice.equals("y")){
                    System.out.println("Enter room ID: ");
                }
                else{
                    return;
                }
            }
            else{
                break;
            }
        }

        // Proceed booking?
        // Room ID

        // Add meal to booking?
        // Add additional bed to booking?
        // Get price and proceed to payment





    }

    private void proceedBooking(){

    }
}
