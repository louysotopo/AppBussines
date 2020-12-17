package com.example.appbussines.Entities;

import java.util.HashMap;
import java.util.Map;

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
    private int status; // 0,1,2 eliminado, activo, inactivo
    public Personal(){
    }
    public Personal(String id, String firstname, String lastname, String email, String position, String incomingdate, String birthdate, String country, String age, int state) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.position = position;
        this.incomingdate = incomingdate;
        this.birthdate = birthdate;
        this.country = country;
        this.age = age;
        this.status = state;
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

    public int getState() {
        return status;
    }

    public void setState(int state) {
        this.status = state;
    }

    public String getStatusLabel(){
        String result ="";
        switch (this.status){
            case 0: result = "Eliminado"; break;
            case 1: result = "Activo"; break;
            case 2: result = "Inactivo"; break;
            default:
                result = "No consignado";
        }
        return  result;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id ", id );
        result.put("firstname ", firstname );
        result.put("lastname ", lastname );
        result.put("email ", email );
        result.put("position ", position );
        result.put("incomingdate ", incomingdate );
        result.put("birthdate ", birthdate );
        result.put("country ", country );
        result.put("age ", age );
        result.put("status ", status );

        return result;
    }
}
