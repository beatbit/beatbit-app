package com.dpc.dchacks2015.monitor;

/**
 * Created by Daniel on 8/1/2015.
 */
public class PatientInfo {
    private String name;
    private String history;
    private int age;

    public PatientInfo(String name, String history, int age) {
        this.name = name;
        this.age = age;
        this.history = history;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHistory() {
        return history;
    }

    public String getName() {
        return name;
    }
}
