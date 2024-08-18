package com.example.ecommercecineplanet.data.model.request;

public class CompleteRequest {

    private String name;
    private String mail;
    private String dni;
    private String operation_date;

    public CompleteRequest() {
    }

    public CompleteRequest(String name, String mail, String dni, String operation_date) {
        this.name = name;
        this.mail = mail;
        this.dni = dni;
        this.operation_date = operation_date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getOperationDate() {
        return operation_date;
    }

    public void setOperationDate(String operation_date) {
        this.operation_date = operation_date;
    }


}
