package com.company;

import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception{
	// write your code here
        System.out.println("Hello World");

        // Passing the path to the file as a parameter
        File testFile = new File(
                "B:\\OneDrive\\Documents\\CSC 139\\VMM Lab\\Deliverables\\TestInput.txt");

        // Creating an object of BuffferedReader class
        BufferedReader br = new BufferedReader(new FileReader(testFile));

        String st;

        while ((st = br.readLine() ) != null){
            // Print the string of each line
            System.out.println(st);
        }


    }
}
