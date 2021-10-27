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

        String line;
        char action;
        int pageAddress;
        int pageValue;

        while ((line = br.readLine() ) != null){
            //for(int i=0; i < line.length(); i++ ) {
                char[] lineArray = line.toCharArray();
                if (lineArray[0] == 'w' || lineArray[0] == 'W') {
                    System.out.println(lineArray[0]);
                } else if (lineArray[0] == 'r' || lineArray[0] == 'R') {
                    System.out.println(lineArray[0]);
                } else if (lineArray[0] == 'p' || lineArray[0] == 'P') {
                    System.out.println(lineArray[0]);
                }
            //}
            // Print the string of each line
            //System.out.println(line);
        }


    }
}
