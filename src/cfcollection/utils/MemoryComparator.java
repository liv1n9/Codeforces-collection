package com.cfcollection.utils;

import com.cfcollection.model.Submission;

import java.util.Comparator;

public class MemoryComparator implements Comparator<Submission> {
    public int compare(Submission o1, Submission o2) {
        if (o1.getMemory() < o2.getMemory()) return 1;
        if (o1.getMemory() > o2.getMemory()) return -1;
        return 0;
    }
}
