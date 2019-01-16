package com.cfcollection.model;

public class Submission {
    private int id;
    private String contestId;
    private char problemIndex;
    private int time;
    private int memory;
    private String language;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    public char getProblemIndex() {
        return problemIndex;
    }

    public void setProblemIndex(char problemIndex) {
        this.problemIndex = problemIndex;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
