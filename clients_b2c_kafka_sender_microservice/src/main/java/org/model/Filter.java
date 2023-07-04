package org.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Filter {
    private String id;
    private String age;
    private String gender;
    private String city;
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Filter(String id, String age, String gender, String city, LocalDate date) {
        this.id = id;
        this.age = age;
        this.gender = gender;
        this.city = city;
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public Filter(){}
}
