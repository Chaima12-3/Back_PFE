package org.model;

import java.time.LocalDate;


public class Filter {
    private String id;
    private int age;
    private String gender;
    private String city;
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Filter(String id, int age, String gender, String city, LocalDate date) {
        this.id = id;
        this.age = age;
        this.gender = gender;
        this.city = city;
        this.date = date;
    }

    public Filter(){}
}
