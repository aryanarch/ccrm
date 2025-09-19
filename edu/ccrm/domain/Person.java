package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Abstract base class representing a person in the system.
 * Demonstrates abstraction and inheritance in OOP.
 */
public abstract class Person {
    protected int id;
    protected String fullName;
    protected String email;
    protected LocalDate dateOfBirth;
    protected String phoneNumber;

    public Person(int id, String fullName, String email, LocalDate dateOfBirth, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    // Abstract methods - must be implemented by subclasses
    public abstract String getRole();
    public abstract void performDuty();

    // Concrete methods
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
