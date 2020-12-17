package com.example.appbussines.Entities;

import java.util.HashMap;
import java.util.Map;

public class Pais {
    private String code;
    private String name;
    private int status;

    public Pais(String code, String name, int status) {
        this.code = code;
        this.name = name;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        result.put("code", code);
        result.put("name", name);
        result.put("status", status);
        return result;
    }

    @Override
    public String toString() {
        return "Pais{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
