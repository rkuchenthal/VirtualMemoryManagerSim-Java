package com.company;

public class UserPage {
    int address=0, value=0 ;
    boolean resident = false, modified = false;

    public UserPage(int address, int value, boolean modified){
        this.address = address;
        this.value = value;
        this.modified = modified;
    }


    //public void changeModified(boolean bool){
        //modified = bool;
    //}

    //public void changeResident(boolean bool){
        //resident = bool;
    //}

}
