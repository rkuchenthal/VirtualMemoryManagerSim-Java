package com.company;

import java.util.ArrayList;

public class PageDirectory {

    public ArrayList<PageTable> directoryTable;

    public PageDirectory(){
        directoryTable = new ArrayList<>();
        directoryTable.add(new PageTable());
    }

    //stores the address and value into the Arraylist
    public void store(int address, int value){
        directoryTable.get(0).add(address, value);
    }

    public int read(int address){
        int a = directoryTable.get(0).read(address);
        return a;
    }

    //if we ever needed to have multiple table directories
    public void add(PageTable x){
        directoryTable.add(x);
    }


}
