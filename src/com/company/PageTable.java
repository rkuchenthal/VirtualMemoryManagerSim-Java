package com.company;

import java.util.ArrayList;

public class PageTable {

    public ArrayList<UserPage> pageTables;

    public PageTable(){
        pageTables = new ArrayList<>(1025);
    }

    public void add(boolean modified,int address ){
        pageTables.add(new UserPage(modified,address));
    }




}
