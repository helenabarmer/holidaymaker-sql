package com.company;

import java.util.Scanner;

public class Destination {
    private Scanner input = new Scanner(System.in);
    private DatabaseConnection database = new DatabaseConnection();


    public Destination(){
        addNewDestination();
    }

    private void addNewDestination(){

        try{
            System.out.println("Enter the city: ");
            String city = input.nextLine();

            System.out.println("Enter the hotel name: ");
            String hotelName = input.nextLine();

            System.out.println("Does " + hotelName + " have a restaurant? [true]/[false]");
            //String restaurant = input.nextLine();
            boolean restaurant = input.nextBoolean();

            System.out.println("Does " + hotelName + " have a kids club? [true]/[false]");
            //String kidsClub = input.nextLine();
            boolean kidsClub = input.nextBoolean();

            System.out.println("Does " + hotelName + " have a pool? [true]/[false]");
            //String pool = input.nextLine();
            boolean pool = input.nextBoolean();

            System.out.println("Does " + hotelName + " provide entertainment? [true]/[false]");
            //String entertainment = input.nextLine();
            boolean entertainment = input.nextBoolean();
            input.nextLine();

            System.out.println("Enter the rating of the hotel (1-5). " + "\n" +
                    "[1] Basic accommodation " + "\n" +
                    "[2] Comfortable" + "\n" +
                    "[3] Unique amenities and quality service " + "\n" +
                    "[4] Upscale quality and extraordinary comfort  "  + "\n" +
                    "[5] Flawless guest services in a state-of-the-art facility " + "\n");
            String rating = input.nextLine();

            System.out.println("Distance to the city center in metres: ");
            String distanceCity = input.nextLine();

            System.out.println("Distance to the beach in metres: ");
            String distanceBeach = input.nextLine();

            System.out.println("Enter number of rooms in the hotel: ");
            int numberOfRooms = input.nextInt();

            String destinationID = database.addDestinationToDatabase(city, hotelName, restaurant, kidsClub,
                    pool, entertainment, rating, distanceCity,
                    distanceBeach, numberOfRooms);
            int ID = Integer.parseInt(destinationID);

            System.out.println("Enter price half board: ");
            int halfBoard = input.nextInt();
            System.out.println("Enter price full board: ");
            int fullBoard = input.nextInt();
            System.out.println("Enter price additional bed: ");
            int bed = input.nextInt();
            input.nextLine();

            // Add prices to additional choices
            database.addAdditionalChoicesPrice(ID, halfBoard, fullBoard, bed);

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
