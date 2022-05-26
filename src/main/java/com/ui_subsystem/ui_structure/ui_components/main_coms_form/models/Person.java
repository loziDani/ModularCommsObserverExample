package com.ui_subsystem.ui_structure.ui_components.main_coms_form.models;

/**
 * Class which defines the Person entity structure
 */
public class Person {

    private final int id;
    private final String destinationIP;
    private final String destinationPort;
    private final String name;
    private final String surname;
    private final String phoneNumber;

    /**
     * Empty constructor for JSON serialization/deserialization
     */
    public Person(){
        this.id = 0;
        this.destinationIP = "";
        this.destinationPort = "";
        this.name = "";
        this.surname = "";
        this.phoneNumber = "";
    }

    /**
     * Constructor to instantiate a Person object
     * @param id the id of the object
     * @param destinationId IP address destination
     * @param destinationPort port destination
     * @param name object's name
     * @param surname object's surname
     * @param phoneNumber object's phone number
     */
    public Person(int id, String destinationId, String destinationPort,
                  String name, String surname, String phoneNumber){
        this.id = id;
        this.destinationIP = destinationId;
        this.destinationPort = destinationPort;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getDestinationIP() {
        return destinationIP;
    }

    public String getDestinationPort() {
        return destinationPort;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
