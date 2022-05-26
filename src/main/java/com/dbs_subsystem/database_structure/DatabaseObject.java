package com.dbs_subsystem.database_structure;

/**
 * Object class containing the structure applied to any database
 */
public class DatabaseObject {

    int id;
    String destinationIP;
    String destinationPort;
    String name;
    String surname;
    String phoneNumber;

    /**
     * Empty constructor for JSON serialization/deserialization
     */
    public DatabaseObject(){
    }

    /**
     * Constructor to build a new DatabaseObject instance
     * @param id object database id
     * @param destinationIP object IP address
     * @param destinationPort object port
     * @param name object name
     * @param surname object surname
     * @param phoneNumber object phone
     */
    public DatabaseObject(int id, String destinationIP, String destinationPort, String name, String surname, String phoneNumber){
        this.id = id;
        this.destinationIP = destinationIP;
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
