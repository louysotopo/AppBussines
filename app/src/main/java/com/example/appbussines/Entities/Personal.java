package com.example.appbussines.Entities;

public class Personal {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String position;
    private String incomingdate;
    private String birthdate;
    private String country;
    private String age;
    private String state;
    public Personal(){
    }
    public Personal(String id, String firstname, String lastname, String email, String position, String incomingdate, String birthdate, String country, String age, String state) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.position = position;
        this.incomingdate = incomingdate;
        this.birthdate = birthdate;
        this.country = country;
        this.age = age;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIncomingdate() {
        return incomingdate;
    }

    public void setIncomingdate(String incomingdate) {
        this.incomingdate = incomingdate;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
