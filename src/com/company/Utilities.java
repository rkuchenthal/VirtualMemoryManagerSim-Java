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
    //TODO add the disk write time here
    public static int Killer(ArrayList<PageFrame> pf){
        for(int i = 0; i < pf.size(); i++){
            //TODO switch from comparing r/w to comparing modified
            //checking for a read page since they are cheaper to replace
            if (pf.get(i).modified == false){
                return i;
            }
        }
        Random rand = new Random();
        return rand.nextInt(Main.totalNumberOfPages+1);
    }
}
