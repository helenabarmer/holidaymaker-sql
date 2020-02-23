package com.company;

import java.util.Scanner;

public class Menu {
    private Scanner input = new Scanner(System.in);
    private Booking booking = new Booking();
    private DatabaseConnection database = new DatabaseConnection();

    public Menu(){
        mainMenu();
    }

    private void mainMenu(){
        String choice = "";
        while(true){

        System.out.println("********** MAIN MENU **********" + "\n" +
                "[1] Booking menu " + "\n" +
                "[2] Admin menu [Add room/destination] " + "\n" + // Change, find booking, delete booking
                "[3] TEST STUFF HERE" + "\n" +
                "\n" +
                "[0] Logout");


        try{
            choice = input.nextLine();

                switch (choice){
                    case "1":
                        System.out.println("Booking");
                        bookingMenu();
                        break;

                    case "2":
                        System.out.println("Admin choices");
                        adminMenu();
                        break;

                        // Test purpose only
                    case "3":
                        System.out.println();

                        break;

                    case "0":
                        System.exit(0);
                        break;

                    default:
                        break;

                }
            }


        catch(Exception e){
            System.out.println(e.getMessage());
        }
        }
    }



    private void bookingMenu() {
        boolean running = true;

        String choice = "";

        while (running) {
            System.out.println("********** BOOKING MENU **********" + "\n" +
                    "[1] Add new booking " + "\n" +
                    "[2] Change booking " + "\n" + // Not implemented
                    "[3] Cancel booking " + "\n" +
                    "[4] Search booking " + "\n" +
                    "\n" +
                    "[0] Back to admin menu");
            try {
                choice = input.nextLine();

                switch (choice) {

                    case "1":
                        booking.bookRoom();
                        break;

                    case "2":
                        booking.changeBooking();
                        break;

                    case "3":
                        booking.cancelBooking();
                        break;

                    case "4":
                        booking.searchBooking();
                        break;

                    case "0":
                        running = false;
                        break;

                    default:
                        System.out.println("You can only make one of the choices listed in the menu choice. ");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Sorry something went wrong. " + e.getMessage());
            }
        }
    }

    private void adminMenu(){
        boolean running = true;

        String choice = "";

        while(running){
        System.out.println("********** ADMIN MENU **********" + "\n" +
                "[1] Add new destination to database " + "\n" +
                "[2] Add new room to database " + "\n" +
                "[3] See all destinations with destination ID " + "\n" +
                "\n" +
                "[0] Back to admin menu");

        try{

                choice = input.nextLine();

                switch (choice){

                    case "1":
                        new Destination();
                        break;

                    case "2":
                        new Booking();
                        break;

                    case "3":
                        database.allDestinationsInDatabase();
                        break;

                    case "0":
                        running = false;
                        break;

                    default:
                        System.out.println("You can only make one of the choices listed in the menu choice. ");
                        break;
                }
        }

        catch(Exception e){
            System.out.println("Sorry something went wrong. " + e.getMessage());
        }
        }
    }


}
