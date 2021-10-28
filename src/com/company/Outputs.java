package com.company;

public class Outputs {

    public static void OutputToCmd(){
        int totalInputs = Main.totalInputs, totalNumberOfPages = Main.totalNumberOfPages,
        totalNumberOfPageTables = Main.totalNumberOfPageTables, swapIns = Main.swapIns,
                swapOuts = Main.swapOuts,memAccess = Main.numberOfMemAccess;

        int totalMemAccess = ((totalInputs-1) * 10) + (swapIns*5000)+(swapOuts*5000)+((totalInputs/2)*10);

        //* * * Paging Activity Statistics * * *
        System.out.println("* * * Paging Activity Statistics * * * ");

        //number of memory accesses = ? 64376
        // w/r in input file
        System.out.println("Number of memory accesses = " + totalInputs);

        //number of triples (1 + access) = ? 64377
        // above + 1
        System.out.println("Number of triples (1 + access) = " + (totalInputs + 1));

        // Number of swap ins (faults) = ? 0
        // a page that has been in working set prev but not now and is being called into the working set
        System.out.println("Number of swap ins (faults) = " + swapIns);

        // Number of swap outs = ? 2
        // add a new page into working set but working set full  increment by 1
        System.out.println("Number of swap outs = " + swapOuts);

        //total number of pages malloced = 10
        System.out.println("Total number of pages malloced = " + totalNumberOfPages);

        //number of pages for Page Tables = ? 1
        // number of page tables we have created
        System.out.println("Number of pages for Page Tables = " + totalNumberOfPageTables);

        //number of page frames for user = ? 9
        System.out.println("Number of page frames for user = " + (totalNumberOfPages - 1));

        // Total memory cycles = ? 1,287,500
        // below times 2
        System.out.println("Total memory cycles =  " + (totalMemAccess*2));

        // Cycles w/o Vmm = ? 643,750
        // memaccess -1 *10
        System.out.println("Cycles w/o Vmm = " + totalMemAccess);

        //cycles per swap_in = 5000
        System.out.println("Cycles per swap_in = 5000");

        //cycles per swap_out = 5000
        System.out.println("Cycles per swap_out = 5000");

        // Last working set size = ? 8
        System.out.println("Last working set size = " + (totalNumberOfPages-1));

        // Max working set size ever = ? 8
        System.out.println("Max working set size ever = " + (totalNumberOfPages-1));

        // Max physical pages = ? 8
        System.out.println("Max physical pages = " + (totalNumberOfPages-1));

        //page size = 4096
        System.out.println("Page size = 4096");

        //replacement algorithm = random
        System.out.println("Replacement algorithm = random");
    }

}
