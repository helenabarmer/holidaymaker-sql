package com.company;

import java.util.Scanner;

public class Booking {
    private Scanner input = new Scanner(System.in);


    public Booking(){

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
