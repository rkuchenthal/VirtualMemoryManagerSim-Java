package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Utilities {

    //checks for duplicates when we are adding pages to the working set
    public static int CompareAddresses(int address, ArrayList<PageFrame> list) {

        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).address == address) {
                return i;
            }
        }
        return -1;
    }

    //choose page in working set to replace
    public static int Killer(ArrayList<PageFrame> pf){
        for(int i = 0; i < pf.size(); i++){
            //checking for a read page since they are cheaper to replace
            if (pf.get(i).modified == false){
                return i;
            }
        }
        Random rand = new Random();
        return rand.nextInt(Main.totalNumberOfPages+1);
    }
}
