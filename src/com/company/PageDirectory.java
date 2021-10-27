package com.company;

import java.util.ArrayList;

public class PageDirectory {

    public ArrayList<PageTable> directoryTable;

    public PageDirectory(){
        directoryTable = new ArrayList<>();
        directoryTable.add(new PageTable());
    }


}
