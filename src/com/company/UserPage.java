package com.company;

public class UserPage {
    int address=0, value=0 ;
    boolean resident = false, modified = false;

    public UserPage(boolean modified, int address, int value ){
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
