package cfcollection.utils;

import cfcollection.model.Submission;

import java.util.Comparator;

public class TimeComparator implements Comparator<Submission> {
    public int compare(Submission o1, Submission o2) {
        if (o1.getTime() < o2.getTime()) return 1;
        if (o1.getTime() > o2.getTime()) return -1;
        return 0;
    }
}
