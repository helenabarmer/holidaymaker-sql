package com.company;

import java.util.Scanner;

public class Room {
    private Scanner input = new Scanner(System.in);

    public Room(){

    }

    public void addNewRoom(){
        // SELECT NO OF ROOMS FROM DATABASE IN THE LOCATION
        System.out.println("How many single rooms are there in the hotel? ");
        String singleRoom = input.nextLine();

        System.out.println("How many double rooms are there in the hotel? ");
        String doubleRoom = input.nextLine();

        System.out.println("How many suites are there in the hotel? ");
        String suite = input.nextLine();

    }
}
