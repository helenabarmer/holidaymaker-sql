package com.company;

import java.util.Scanner;

public class Menu {
    private Scanner input = new Scanner(System.in);
    private Booking booking = new Booking();
    private Destination destination = new Destination();

    public Menu(){
        mainMenu();
    }

    private void mainMenu(){
        boolean running = true;
        String choice = "";
        while(running){

        System.out.println("********** ADMIN MENU **********" + "\n" +
                "[1] Bookings (Add/Search/Delete)" + "\n" +
                "[2] Rooms (Add/Search/Delete)" + "\n" + // Change, find booking, delete booking
                "[3] Destinations (Add/Search/Delete)" + "\n" +
                "\n" +
                "[0] Logout");


        try{
            choice = input.nextLine();





                switch (choice){
                    case "1":
                        System.out.println("Bookings");
                        break;

                    case "2":
                        System.out.println("Rooms");
                        break;

                    case "3":
                        System.out.println("Destinations");
                        destination.addNewDestination();
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

    private void bookingMenu(){
        boolean running = true;

        String choice = "";

        System.out.println("********** BOOKING MENU **********" + "\n" +
                "[1] Add booking " + "\n" +
                "[2] Search booking " + "\n" + // Change, find booking, delete booking
                "[3] Delete booking " + "\n" +
                "\n" +
                "[0] Back to admin menu");


        try{

            while(running){
                choice = input.nextLine();

                switch (choice){

                    case "1":
                        booking.bookRoom();
                        break;

                    case "2":
                        System.out.println("Search booking");
                        break;

                    case "3":
                        System.out.println("Delete booking");
                        break;

                    case "0":
                        running = false;
                        break;

                    default:
                        System.out.println("You can only make one of the choices listed in the menu choice. ");
                        break;
                }

            }

        }

        catch(Exception e){
            System.out.println("Sorry something went wrong. " + e.getMessage());
        }



    }


}
