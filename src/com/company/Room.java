package com.company;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

public class Room {
    private Scanner input = new Scanner(System.in);
    private DatabaseConnection database = new DatabaseConnection();

    public Room(){

    }

    public void addNewRoom() {

        try{
            System.out.println("Enter the destination ID: ");
            int destinationID = input.nextInt();
            input.nextLine();

            System.out.println("Add room type (single/double/suite): ");
            String roomType = input.nextLine();
            //input.nextLine(); // To avoid skipping next line

            System.out.println("Add price: ");
            int price = input.nextInt();

            System.out.println("Add maximum number of guests: ");
            int maxGuests = input.nextInt();

            database.addRoomToDatabase(destinationID, roomType, price, true, maxGuests);
            System.out.println("New room is now added! ");

        } catch (Exception e){
            System.out.println("Oh no something went wrong. Please try again.  " + e.getMessage());
        }
    }

}
