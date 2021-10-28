package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Utilities {

    //checks for duplicates when we are adding pages to the working set
    //TODO add mem access here??
    public static int CompareAddresses(int address, ArrayList<PageFrame> list) {

        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).address == address) {
                //FOUND DUPLICATE
                return 1;
            }
        }
        //NO DUPLICATES FOUND
        return -1;
    }

    //choose page in working set to replace
    public static int Killer(ArrayList<PageFrame> pf){
        //add 10 cycles would be an acceptable constant for when selecting a victim
        Main.memCycles =  Main.memCycles + 10;

        // if we find a page that hasn't been modified it should cost less cycles since
        // it doesn't have to be written to disk. But the assignment says to just make it
        // a flat 10 or so cycles
        //for(int i = 0; i < pf.size(); i++){
            //checking for a read page since they are cheaper to replace
           // if (pf.get(i).modified == false){
            //    Main.AddressIndexer.add(pf.get(i).address);
            //    return i;
            //}
        //}

        Random rand = new Random();
        // which has to be saved to disk then the new one written to memory
        int target =rand.nextInt(Main.totalNumberOfPages+1);

        //add to address indexer
        Main.AddressIndexer.add(pf.get(target).address);
        //check for swapout
        if(pf.get(target).modified == false){
            Main.swapOuts++;
            Main.memCycles = Main.memCycles + 5000;
        }
        return target;
    }

    //used to check if a swap-in has occurred
    public static void SwapInChecker(ArrayList<Integer> addressList, ArrayList<PageFrame> workingSet, int address){

        for(int i= 0;i < addressList.size();i++){
            if(address == addressList.get(i) && CompareAddresses(address, workingSet) == -1){
                //swap criteria has been met so increment swap-ins
                Main.swapIns++;
                //add cycles
                Main.memCycles = Main.memCycles + 5000;
            }
        }
    }
}
