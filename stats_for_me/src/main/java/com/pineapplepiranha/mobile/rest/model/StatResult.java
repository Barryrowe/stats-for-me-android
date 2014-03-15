package com.pineapplepiranha.mobile.rest.model;

/**
 * Created by barry on 3/13/14.
 */
public class StatResult {
    private String name;
    private int count;

    public StatResult(String name, int count){
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
