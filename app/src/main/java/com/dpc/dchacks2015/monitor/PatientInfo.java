package com.dpc.dchacks2015.monitor;

/**
 * Created by Daniel on 8/1/2015.
 */
public class PatientInfo {
    private String name;
    private String history;
    private String doctorName;
    private String doctorPhoneNumber;
    private int age;

    public PatientInfo(String name, String history, int age, String doctorPhoneNumber, String doctorName) {
        this.name = name;
        this.age = age;
        this.history = history;
        this.doctorName = doctorName;
        this.doctorPhoneNumber = doctorPhoneNumber;
    }

    public String getDoctorPhoneNumber() {
        return doctorPhoneNumber;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setDoctorPhoneNumber(String doctorPhoneNumber) {
        this.doctorPhoneNumber = doctorPhoneNumber;
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
