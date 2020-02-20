package com.company;

import java.util.Scanner;

public class Menu {
    private Scanner input = new Scanner(System.in);
    private Booking booking = new Booking();
    private Destination destination = new Destination();
    private Room room = new Room();
    private DatabaseConnection database = new DatabaseConnection();

    public Menu(){
        mainMenu();
    }

    private void mainMenu(){
        //boolean running = true;
        String choice = "";
        while(true){

        System.out.println("********** MAIN MENU **********" + "\n" +
                "[1] Add booking " + "\n" +
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
                        System.out.println("TEST");
                        //booking.test_dates();
                        //database.printBookedDates();
                        database.getCustomerID("Cookie", "Monster");
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
                    "[1] Search destinations and book " + "\n" +
                    "[2] Change booking " + "\n" + // Not implemented
                    "[3] Delete booking " + "\n" + // Not implemented
                    "[4] See all destinations " + "\n" +
                    "[5] Search customer by name " + "\n" +
                    "\n" +
                    "[0] Back to admin menu");
            try {
                choice = input.nextLine();

                switch (choice) {

                    case "1":
                        booking.bookRoom();
                        break;

                    case "2":
                        System.out.println("Change booking ");

                        break;

                    case "3":
                        System.out.println("Delete booking");
                        break;

                    case "4":
                        System.out.println("All destinations");
                        database.allDestinationsInDatabase();
                        break;

                    case "5":
                        System.out.println("Search customers");
                        //customer.registerNewCustomer();
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
        System.out.println("********** ROOM MENU **********" + "\n" +
                "[1] Add new destination " + "\n" +
                "[2] Add new room to destination " + "\n" +
                "\n" +
                "[0] Back to admin menu");

        try{

                choice = input.nextLine();

                switch (choice){

                    case "1":
                        System.out.println("Add destination");
                        break;

                    case "2":
                        System.out.println("Add new room. ");
                        room.addNewRoom();
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
