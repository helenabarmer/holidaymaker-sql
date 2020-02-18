package com.company;

import java.util.Scanner;

public class Booking {
    private Scanner input = new Scanner(System.in);
    private DatabaseConnection database = new DatabaseConnection();


    public Booking(){

    }

    public void filterAndBook(){

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

        database.filterRoomsInDatabase(numberOfGuests, restaurant, kidsClub, pool, entertainment, true);


        //AND destinations.restaurant = ? AND destinations.kids_club = ?
        ////AND destinations.pool = ? AND destinations.entertainment = ? AND rooms.availability = 1;

        // VG questions
        //System.out.println("Maximum distance to the beach: "); // Should be converted to int??
        //System.out.println("Maximum distance to the city centre: "); // Should be converted to int??

    }

    public void filterDestination(){
        System.out.println("********** BOOKING MENU **********" + "\n" +
                "Please select a destination: " + "\n" +
                "[1] Svalbard" + "\n" +
                "[2] Oslo" + "\n" +
                "[3] Stavanger" + "\n" +
                "[4] Bergen" + "\n" +
                "[5] Trondheim" + "\n" +
                "\n" +
                "[0] Back to booking menu");
        String destination = input.nextLine();
        //sql insert here to filter

        System.out.println("Filter: " + "\n" +
                "[1] Pool" + "\n" +
                "[2] Entertainment" + "\n" +
                "[3] Kids club" + "\n" +
                "[4] Restaurant" + "\n");
    }

    public void filterAdditionalChoices(){

    }

    public void bookRoom(){
        boolean running = true;


        // destination
        // dates
        // number of guests
        // additional choices
        // prices
        while(running){
            System.out.println("********** BOOKING MENU **********" + "\n" +
                    "Please select a destination: " + "\n" +
                    "[1] Svalbard" + "\n" +
                    "[2] Oslo" + "\n" +
                    "[3] Stavanger" + "\n" +
                    "[4] Bergen" + "\n" +
                    "[5] Trondheim" + "\n" +
                    "\n" +
                    "[0] Back to booking menu");
            String destination = input.nextLine();
            //sql insert here to filter

            System.out.println("Filter: " + "\n" +
                    "[1] Pool" + "\n" +
                    "[2] Entertainment" + "\n" +
                    "[3] Kids club" + "\n" +
                    "[4] Restaurant" + "\n");

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


            System.out.println("Proceed with booking? [Y]/[N}");
            String proceedBooking = input.nextLine();
        }


    }

    private void proceedBooking(){

    }
}
