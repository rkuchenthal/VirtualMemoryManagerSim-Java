package com.company;

import java.io.*;
import java.util.ArrayList;


public class Main {

    public static int totalNumberOfPages = 0,swapIns = 0,swapOuts = 0, memCycles=0, cyclePerSwap= 5000, pageSize=4096,
    numberOfMemAccess = 0,maxDirectorySize = 1024, maxPageTableSize = 1024;


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
                System.out.println(totalNumberOfPages);
                continue;
            }
            //convert the items from inputLine[] from strings
            //to proper data types
            if(inputLine[0].equals("w") || inputLine[0].equals("W") ){
                operator = inputLine[0].toCharArray();
                int address = Integer.parseInt(inputLine[1]);
                System.out.println(inputLine[2]);
                int value = Integer.parseInt(inputLine[2]);

                //create Inputs object and insert inputLine[] values in
                Inputs IP = new Inputs(operator[0], address, value);
                //add IP object to the arrayList inputs to be compared later
                inputs.add(IP);
            }
            else{
                operator = inputLine[0].toCharArray();
                int address = Integer.parseInt(inputLine[1]);
                //System.out.println(inputLine[2]);
                //int value = Integer.parseInt(inputLine[2]);

                //create Inputs object and insert inputLine[] values in
                Inputs IP = new Inputs(operator[0], address, 0);
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
            System.out.println("Page Directory "+ i);

            //now iterate through the page tables
            for(int j = 0; j < maxPageTableSize; j++){
                System.out.println("Page #"+ j + " of 1023.");

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
                pageValue= inputs.get(c).value;


                //now entering the individual page values into list
                if ((inputs.get(c).operation == 'w') || (inputs.get(c).operation == 'W')) {
                    UserPage UP = new UserPage(operationBit,pageAddress,pageValue);
                    PD.directoryTable.get(i).add(UP.address, UP.value, UP.modified);

                    //System.out.println(pageAddress +" "+pageValue);
                }
                else if ((inputs.get(c).operation == 'r')  || (inputs.get(c).operation == 'R') ) {
                    UserPage UP = new UserPage(operationBit,pageAddress,pageValue);
                    PD.directoryTable.get(i).add(UP.address, UP.value, UP.modified);

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
            boolean modified = PD.directoryTable.get(0).pageTables.get(i).modified;
            int address = PD.directoryTable.get(0).pageTables.get(i).address;
            int value = PD.directoryTable.get(0).pageTables.get(i).value;
            //TODO have modified bit change to true when it is a w input
            PageFrame PF = new PageFrame(modified,address,value);
            int possibleDuplicate = Utilities.CompareAddresses(address,pf);

            //Making sure we don't go over our working set while filling it up
            if(pf.size() < totalNumberOfPages + 1){
                //Making sure while filling the working set doesn't have duplicates
                //TODO make sure reads remain un modified
                if(possibleDuplicate == (-1)){
                    pf.add(PF);
                    //TODO add page fault incrementer
                }
                //if duplicate found we replace it with the new one
                else if(possibleDuplicate != (-1)){
                    //TODO change to do nothing
                    //TODO if we read a read dont change modified bit
                    //Todo if we read a w or w a read then change to modified
                    //this is just going to change the value, but
                    // retain the same disk address for page
                    pf.set(possibleDuplicate, PF);

                    //TODO add modified counter here if it is a w input
                }
            }
            // if working set full then we need to perform swapping.
            else{
                //if duplicate found we replace it with the new one
                //so only the value wil change
                //TODO if we read a read dont change modified bit
                //Todo if we read a w or w a read then change to modified
                if(possibleDuplicate != (-1)){
                    //TODO change to nothing
                    pf.set(possibleDuplicate,PF);
                    //TODO add page fault incrementer
                }
                else{
                    //swap the page with a read only
                    //page or if all write pages then
                    // it chooses a page at random to swap.
                    //TODO make sure reads remain un modified
                    pf.set(Utilities.Killer(pf),PF);
                    //TODO add modified counter here if it is a w input
                }
            }
        }
    }
}
