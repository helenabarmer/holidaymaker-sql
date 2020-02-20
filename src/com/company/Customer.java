package com.company;

import java.util.Scanner;

public class Customer {
    private Scanner input = new Scanner(System.in);
    private Booking booking = new Booking();
    private DatabaseConnection database = new DatabaseConnection();

    public Customer(){

    }

    public void registerNewCustomer(){
        System.out.println("First name: ");
        String firstName = input.nextLine();
        System.out.println("Last name: ");
        String lastName = input.nextLine();
        System.out.println("E-mail: ");
        String email = input.nextLine();
        System.out.println("Phone number: ");
        String phoneNumber = input.nextLine();

        database.addCustomerToDatabase(firstName, lastName, email, phoneNumber);
        System.out.println( "\n" + "New customer added: ");
        database.searchCustomerAndPrint(firstName, lastName);
        System.out.println("Would you like to continue the booking? [Y]/[N]");
        String continueBooking = input.nextLine();
        if(continueBooking.equals("Y") || continueBooking.equals("y")){
            System.out.println("Please enter the customer ID: ");
            int customerID = input.nextInt();
            //booking.filterAndBook(customerID);
        }
    }
}
