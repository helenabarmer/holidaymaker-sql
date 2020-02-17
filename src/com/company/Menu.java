package com.company;

import java.util.Scanner;

public class Menu {
    Scanner input = new Scanner(System.in);

    public Menu(){
        mainMenu();
    }

    private void mainMenu(){
        System.out.println("********** MAIN MENU **********" + "\n" +
                "[1] Book a room" + "\n" +
                "[2] Search booking" + "\n" + // Change, find booking, delete booking
                "[3] Logout" + "\n");

        String choice = "1";

        switch (choice){
            case "1":
                System.out.println("Book room");
                break;

            case "2":
                System.out.println("Search booking");
                break;

            case "3":
                System.exit(0);
                break;

        }
    }

    private void bookARoom(){

        // destination
        // dates
        // number of guests
        // additional choices
        // prices

        System.out.println("********** BOOKING MENU **********" + "\n" +
                "Please select a destination: " + "\n" +
                "[1] Svalbard" + "\n" +
                "[2] Oslo" + "\n" +
                "[3] Stavanger" + "\n" +
                "[4] Bergen" + "\n" +
                "[5] Trondheim" + "\n" +
                "[0] Back to main menu" + "\n");
        String destination = input.nextLine();

        System.out.println("Please select arrival date. Format should be YYYY/MM/DD. ");
        String checkInDate = input.nextLine();

        System.out.println("Please select departure date. Format should be YYYY/MM/DD. ");
        String checkOutDate = input.nextLine();

        System.out.println("Please select number of guests");
        int numberOfGuests = input.nextInt();

        System.out.println("Proceed with booking? [Y] / [N}");

        String choice = "1";

        switch (choice){
            case "1":
                System.out.println("SELECT ALL HOTELS ETC");
                break;

            case "2":

                break;

            case "0":
                System.out.println("Back to main menu! ");
                System.exit(0);
                break;

        }

    }

    private void proceedBooking(){

    }
}
