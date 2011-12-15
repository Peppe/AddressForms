package com.example.addressforms.data;

import java.util.Date;

public class Person implements Cloneable {

    private int id = -1;
    private String firstName;
    private String lastName;
    private Address address;
    private String phoneNumber;
    private String email;
    private Date dateOfBirth;
    private String comments;

    public Person() {
        address = new Address();
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Person clone() throws CloneNotSupportedException {
        Person person = new Person();
        person.setId(getId());
        person.setFirstName(getFirstName());
        person.setLastName(getLastName());
        person.setPhoneNumber(getPhoneNumber());
        person.setEmail(getEmail());
        person.setDateOfBirth(getDateOfBirth());
        person.setComments(getComments());
        person.setAddress(getAddress().clone());
        return person;
    }

    @Override
    public boolean equals(Object otherPerson) {
        if (this == otherPerson) {
            return true;
        }
        if (!(otherPerson instanceof Person)) {
            return false;
        }
        return id == ((Person) otherPerson).getId();
    }

    @Override
    public int hashCode() {
        return new Integer(id).hashCode();
    }
}
