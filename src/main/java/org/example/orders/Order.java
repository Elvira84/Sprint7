package org.example.orders;

import io.restassured.response.ValidatableResponse;

import java.util.List;

public class Order {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> colour;

    public Order(List<String> colour) {
        this.firstName = "Naruto";
        this.lastName = "Uchiha";
        this.address = "Konoha, 142 apt.";
        this.metroStation = "4";
        this.phone = "+7 800 355 35 35";
        this.rentTime = "5";
        this.deliveryDate = "2020-06-06";
        this.comment = "Saske, come back to Konoha";
        this.colour = colour;

    }

    public Order() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(String metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getColour() {
        return colour;
    }

    public void setColour(List<String> color) {
        this.colour = color;
    }


    public ValidatableResponse getOrdersList() {
        return null;
    }
}