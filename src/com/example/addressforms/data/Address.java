package com.example.addressforms.data;

public class Address implements Cloneable {

    private String street;
    private Integer zip;
    private String city;
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public Address clone() throws CloneNotSupportedException {
        Address address = new Address();
        address.setStreet(getStreet());
        address.setZip(getZip());
        address.setCity(getCity());
        address.setCountry(getCountry());
        return address;
    }

}
