package com.company;

import java.io.*;
import java.util.ArrayList;


public class Main {

    public static int totalNumberOfPages = 0,swapIns = 0,swapOuts = 0, totalInputs = 0, memCycles =0,
    numberOfMemAccess = 0,maxDirectorySize = 1024, maxPageTableSize = 1024, totalNumberOfPageTables=0;

    public static ArrayList<Integer> AddressIndexer = new ArrayList<>();

    public static void main(String[] args) throws Exception{

	// write your code here
        System.out.println("Hello World");

        //initiate all variables, objects, and lists to be used
        PageDirectory  PD = new PageDirectory();
        ArrayList<PageFrame> pf = new ArrayList(totalNumberOfPages);
        ArrayList<Inputs> inputs = new ArrayList();
        String[] inputLine;
        int pageAddress,pageValue;
        String line;
        char[] operator;
        boolean operationBit = false;

        // Passing the path to the file as a parameter
        File testFile = new File(
                "B:\\OneDrive\\Documents\\CSC 139\\VMM Lab\\Deliverables\\TestInput.txt");

        // Creating an object of BufferedReader class
        BufferedReader br = new BufferedReader(new FileReader(testFile));

        //This loop extracts all lines from Input doc, switches them to correct data types,
        //and inputs them into a array list which will allow us to insert them into
        //the PageTable later
        while ((line = br.readLine() ) != null) {

            //parse each line by " " and add all parsed
            //items in to the inputLine[]
            inputLine = line.split(" ");

            //assign the working set allocation
            if (inputLine[0].equals("p") || inputLine[0].equals("P")) {
                totalNumberOfPages = Integer.parseInt(inputLine[1]);
                //System.out.println(totalNumberOfPages);
                continue;
            }

            //increment the totalInputs so we can track the total
            totalInputs++;

            //convert the items from inputLine[] from strings
            //to proper data types
            if(inputLine[0].equals("w") || inputLine[0].equals("W") ){
                operator = inputLine[0].toCharArray();
                int address = Integer.parseInt(inputLine[1]);
                //System.out.println(inputLine[2]);
                //int value = Integer.parseInt(inputLine[2]);

                //create Inputs object and insert inputLine[] values in
                Inputs IP = new Inputs(operator[0], address);
                //add IP object to the arrayList inputs to be compared later
                inputs.add(IP);
            }
            else{
                operator = inputLine[0].toCharArray();
                int address = Integer.parseInt(inputLine[1]);
                //System.out.println(inputLine[2]);
                //int value = Integer.parseInt(inputLine[2]);

                //create Inputs object and insert inputLine[] values in
                Inputs IP = new Inputs(operator[0], address);
                //add IP object to the arrayList inputs to be compared later
                inputs.add(IP);
            }
        }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //GETTING THE INPUTS INSERTED INTO THE PAGEDIRECTORY->PAGETABLE ARRAYLISTS.

        //will keep track of total number of inputs in
        // case we need to start a new table
        int c = 0;
        //iterate through PageTableDirectories as they fill up
        //for this assignment we are to assume we will use no more
        //than 1 directory so it is hard coded to 1
        for(int i = 0; i < 1; i++ ){
            //System.out.println("Page Directory "+ i);

            //now iterate through the page tables
            for(int j = 0; j < maxPageTableSize; j++){
                //Test comment out before submission
                //System.out.println("Page #"+ j + " of 1023.");

                //increment totalNumberOfPageTables to reflect how
                // many tables we have created
                totalNumberOfPageTables++;

                //check to make sure we dont run off the stack
                //of inputs list
                if(c == inputs.size()-1){
                    break;
                }

                //making sure w is has the modified bit checked
                //while leaving all read as not checked
                if((inputs.get(c).operation) == 'w'){
                    operationBit = true;
                }
                pageAddress = inputs.get(c).address;

                //now entering the individual page values into list
                if ((inputs.get(c).operation == 'w') || (inputs.get(c).operation == 'W')) {
                    UserPage UP = new UserPage(operationBit,pageAddress);
                    PD.directoryTable.get(i).add(UP.modified,UP.address);

                    //System.out.println(pageAddress +" "+pageValue);
                }
                else if ((inputs.get(c).operation == 'r')  || (inputs.get(c).operation == 'R') ) {
                    UserPage UP = new UserPage(operationBit,pageAddress);
                    PD.directoryTable.get(i).add(UP.modified,UP.address);

                    //System.out.println(pageAddress);
                }
                //increment input counter
                c++;
            }
        }
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //INSERTING INPUTS INTO THE PAGE TABLE OR SWAPPING THEM OUT

        //Add the inputs list into the Working Set of Frames
        //and performs required swapping
        for(int i = 0; i < PD.directoryTable.get(0).pageTables.size()-1; i++){

            //Initializing the page vars from the pageTable array
            boolean incModified = PD.directoryTable.get(0).pageTables.get(i).modified;
            int incAddress = PD.directoryTable.get(0).pageTables.get(i).address;
            int possibleDuplicate = Utilities.CompareAddresses(incAddress,pf);


            //check to see if it is a swap-in and if so increment memCycles & swap-ins
            Utilities.SwapInChecker(AddressIndexer, pf,incAddress);

            //Making sure we don't go over our working set while filling it up
            if(pf.size() < totalNumberOfPages + 1){
                //Making sure while filling the working set doesn't have duplicates
                if(possibleDuplicate == (-1)){
                    PageFrame PF = new PageFrame(incModified,incAddress);
                    //check to see if it is a swap-in and if so increment memCycles & swap-ins
                    Utilities.SwapInChecker(AddressIndexer, pf,incAddress);
                    pf.add(PF);

                }
                //TODO optimize if statements
                //if duplicate found we change its modified bit if applicable
                else if(possibleDuplicate != (-1)){

                    //if we read a read don't change modified bit
                    // if we read a w or w a read then change to modified
                    if(pf.get(possibleDuplicate).modified == true && incModified == false
                        || pf.get(possibleDuplicate).modified == false && incModified == true ){
                        PageFrame PF = new PageFrame(true,incAddress);
                        //check to see if it is a swap-in and if so increment memCycles & swap-ins
                        //Utilities.SwapInChecker(AddressIndexer, pf,incAddress);
                        //this is just going to change the value, but
                        // retain the same disk address for page
                        pf.set(possibleDuplicate, PF);
                    }
                    //if we read a read we don't change modified bit
                    else{
                        PageFrame PF = new PageFrame(incModified,incAddress);

                        //this is just going to change the value, but
                        // retain the same disk address for page
                        pf.set(possibleDuplicate, PF);
                    }
                }
            }
            // if working set full then we need to perform swapping.
            //TODO optimize if statements
            else{

                //if duplicate found we replace change the modified bit if applicable
                if(possibleDuplicate != (-1)){

                    //if we read a read don't change modified bit
                    // if we read a w or w a read then change to modified
                    if(pf.get(possibleDuplicate).modified == true && incModified == false
                            || pf.get(possibleDuplicate).modified == false && incModified == true ){
                        PageFrame PF = new PageFrame(true,incAddress);
                        //check to see if it is a swap-in and if so increment memCycles & swap-ins
                        //Utilities.SwapInChecker(AddressIndexer, pf,incAddress);
                        //this is just going to change the value, but
                        // retain the same disk address for page
                        pf.set(possibleDuplicate, PF);
                    }
                    //if we read a read we don't change modified bit
                    else{
                        PageFrame PF = new PageFrame(incModified,incAddress);
                        //check to see if it is a swap-in and if so increment memCycles & swap-ins
                        //Utilities.SwapInChecker(AddressIndexer, pf,incAddress);
                        //this is just going to change the value, but
                        // retain the same disk address for page
                        pf.set(possibleDuplicate, PF);
                    }
                }
                //else find a page to kill/swap with
                else{
                    PageFrame PF = new PageFrame(incModified,incAddress);

                    //check to see if it is a swap-in and if so increment memCycles & swap-ins
                    //Utilities.SwapInChecker(AddressIndexer, pf,incAddress);

                    //swap the page with a read only
                    //page or if all write pages then
                    // it chooses a page at random to swap.
                    //TODO make sure reads remain un modified
                    pf.set(Utilities.Killer(pf),PF);
                    //TODO add modified counter here if it is a w input
                }
            }
        }

        //Print all outputs to cmd line
        Outputs.OutputToCmd();

    }
}
