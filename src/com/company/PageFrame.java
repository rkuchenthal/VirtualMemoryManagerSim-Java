package com.company;

public class PageFrame {

    boolean empty = true, modified;
    int address, value;

    public PageFrame(boolean modified,int address, int value,boolean empty){
        this.modified = modified;
        this.address = address;
        this.value = value;
        this.empty = empty;
    }
}
