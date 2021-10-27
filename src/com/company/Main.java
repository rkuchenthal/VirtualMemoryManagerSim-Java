package com.company;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static int totalNumberOfPages = 0,swapIns = 0,swapOuts = 0, memCycles=0, cyclePerSwap= 5000, pageSize=4096,
    numberOfMemAccess = 0;

    public static void main(String[] args) throws Exception{

	// write your code here
        System.out.println("Hello World");

        // Passing the path to the file as a parameter
        File testFile = new File(
                "B:\\OneDrive\\Documents\\CSC 139\\VMM Lab\\Deliverables\\TestInput.txt");

        // Creating an object of BufferedReader class
        BufferedReader br = new BufferedReader(new FileReader(testFile));

        String line;
        int pageAddress,pageValue;
        PageDirectory  PD = new PageDirectory();

        while ((line = br.readLine() ) != null){

                String[] temp = line.split(" ");

                if (temp[0].equals("w") || temp[0].equals("W")) {
                    pageAddress = Integer.parseInt(temp[1]);
                    pageValue= Integer.parseInt(temp[2]);
                    PD.store(pageAddress,pageValue);
                    System.out.println(pageAddress +" "+pageValue);
                }
                else if (temp[0].equals("r") || temp[0].equals("R")) {
                    pageAddress = Integer.parseInt(temp[1]);
                    PD.read(pageAddress);
                    System.out.println(pageAddress);
                }
                else if (temp[0].equals("p") || temp[0].equals("P")) {
                    totalNumberOfPages = Integer.parseInt(temp[1]);
                    System.out.println(totalNumberOfPages);
                }

            // Print the string of each line
            /*System.out.println(temp[0]);
            System.out.println(temp[1]);
            System.out.println(temp[2]);
            System.out.println(temp[3]);
            System.out.println(temp[4]);*/
        }


    }
}
