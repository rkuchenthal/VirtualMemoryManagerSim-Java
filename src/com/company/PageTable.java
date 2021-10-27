package com.company;

import java.util.ArrayList;

public class PageTable {

    public ArrayList<UserPage> pageTables;

    public PageTable(){
        pageTables = new ArrayList<>(1025);
    }

    public void add(int address, int value, boolean modified){
        pageTables.add(new UserPage(address, value, modified));
    }




}
