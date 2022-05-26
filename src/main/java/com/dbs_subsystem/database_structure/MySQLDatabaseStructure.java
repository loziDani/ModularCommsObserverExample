package com.dbs_subsystem.database_structure;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * DAO (Data Access Object) class containing the methods needed to communicate the application with a MySQL database
 */
public class MySQLDatabaseStructure implements IDatabaseStructure {

    MySQLDatabaseStructureDescriptor instanceDescriptor;

    //As Observable, this class is observed by the DatabaseConnector class, so this variable throws a notification
    // to the DatabaseConnector when an event occurs in this class, and it has an interest in the DatabaseConnector
    private final PropertyChangeSupport support;

    /**
     * Class constructor
     */
    public MySQLDatabaseStructure() {
        this.instanceDescriptor = new MySQLDatabaseStructureDescriptor();
        support = new PropertyChangeSupport(this);
    }

    /**
     * Attach the Observers that will get notified when a change or request is made in this class
     * @param pcl the Observer' property change listener
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    @Override
    public Connection openDatabase() {
        return null;
    }

    @Override
    public void closeDatabase() {}

    @Override
    public void checkDatabaseConnection() {}

    /**
     * Create a new element into the database
     * @param databaseObject the object to be added to the database
     * @return the object created in the database with its corresponding assigned id
     */
    @Override
    public DatabaseObject createElement(DatabaseObject databaseObject) {
        String sql = "INSERT INTO person VALUES (?,?,?,?,?,?,?,?,?)";
        DatabaseObject elementExists = checkElementExists(databaseObject);

        if (elementExists != null) {
            updateElement(elementExists);
            return elementExists;
        } else {
            try {
                Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/person", "root", "");
                PreparedStatement pst = cn.prepareStatement(sql);
                pst.setInt(1, 0);
                pst.setString(2, databaseObject.getDestinationIP());
                pst.setString(3, databaseObject.getDestinationPort());
                pst.setString(4, databaseObject.getName());
                pst.setString(5, databaseObject.getSurname());
                pst.setString(6, databaseObject.getPhoneNumber());
                SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
                pst.setString(7, sm.format(new Date()));
                pst.setString(8, sm.format(new Date()));
                pst.setBoolean(9, true);
                pst.executeUpdate();

                DatabaseObject databaseObjectWithId = getElementWithId(databaseObject);
                //As Observable, this DAO class notifies to its Observer that a new request or change has been made into the database
                // returning the event name and the result of the action
                support.firePropertyChange("createdElementWithId", this.instanceDescriptor, databaseObjectWithId);
                return databaseObjectWithId;

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        return null;
    }

    /**
     * Read the person table when requested by the user
     * @return the ArrayList with the person registries retrieved from the database
     */
    @Override
    public ArrayList<DatabaseObject> readTable() {
        ArrayList<DatabaseObject> databaseObjects = new ArrayList<>();
        String sql = "SELECT * FROM person WHERE enabled = '1'";

        try {
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/person", "root", "");
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                DatabaseObject databaseObject = new DatabaseObject(rs.getInt("id"), rs.getString("ipAddress"),
                        rs.getString("port"), rs.getString("name"),
                        rs.getString("surname"), rs.getString("phone"));
                databaseObjects.add(databaseObject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //As Observable, this DAO class notifies to its Observer that a new request or change has been made into the database
        // returning the event name and the result of the action
        support.firePropertyChange("readTableResponse", this.instanceDescriptor, databaseObjects);
        return databaseObjects;
    }

    /**
     * Update an existing element into the database
     * @param databaseObject the object to be updated
     */
    @Override
    public void updateElement(DatabaseObject databaseObject) {
        String sql = "UPDATE person SET ipAddress = ?, port = ?, name = ?, surname = ?, phone = ? WHERE id = ? AND enabled = '1'";

        try {
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/person", "root", "");
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, databaseObject.getDestinationIP());
            pst.setString(2, databaseObject.getDestinationPort());
            pst.setString(3, databaseObject.getName());
            pst.setString(4, databaseObject.getSurname());
            pst.setInt(5, Integer.parseInt(databaseObject.getPhoneNumber()));
            pst.setInt(6, databaseObject.getId());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Deletes the element requested by the user from the database by setting its 'enable' parameter to false
     * @param databaseObject the object to be deleted from the database
     */
    @Override
    public void deleteElement(DatabaseObject databaseObject) {
        String sql = "UPDATE person SET enabled = " + false + " WHERE id = ?";

        try {
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/person", "root", "");
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setInt(1, databaseObject.getId());
            pst.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //As Observable, this DAO class notifies to its Observer that a new request or change has been made into the database
        // returning the event name and the result of the action
        support.firePropertyChange("elementRemovedFromDB", this.instanceDescriptor, null);
    }

    /**
     * Retrieves the requested element with its corresponding assigned id by the database
     * @param databaseObject the object which id is requested
     * @return the object completed with its corresponding id
     */
    @Override
    public DatabaseObject getElementWithId(DatabaseObject databaseObject) {
        String sql = "SELECT * FROM person WHERE ipAddress = '" + databaseObject.getDestinationIP() +
                "' AND port = '" + databaseObject.getDestinationPort() + "' AND name = '" +
                databaseObject.getName() + "' AND surname = '" + databaseObject.getSurname() + "' AND phone = '" + databaseObject.getPhoneNumber() + "'";

        DatabaseObject databaseObjectWithId = null;

        try {
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/person", "root", "");
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()){
                databaseObjectWithId = new DatabaseObject(rs.getInt("id"), rs.getString("ipAddress"),
                        rs.getString("port"), rs.getString("name"),
                        rs.getString("surname"), rs.getString("phone"));
            }
            return databaseObjectWithId;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Checks if an element exists in the database
     * @param databaseObject the object to be checked
     * @return the object with its corresponding id if exists. Null otherwise
     */
    @Override
    public DatabaseObject checkElementExists(DatabaseObject databaseObject) {

        String sql = "SELECT * FROM person WHERE ipAddress = '" + databaseObject.getDestinationIP() +
                "' AND port = '" + databaseObject.getDestinationPort() + "' AND name = '" + databaseObject.getName() +
                "' AND surname = '" + databaseObject.getSurname() + "' AND phone = '" + databaseObject.getPhoneNumber() + "' AND enabled = '1'";
        DatabaseObject existingDatabaseObject = null;
        try {
            Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/person", "root", "");
            PreparedStatement pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                existingDatabaseObject = new DatabaseObject(rs.getInt("id"), rs.getString("ipAddress"),
                        rs.getString("port"), rs.getString("name"),
                        rs.getString("surname"), rs.getString("phone"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return existingDatabaseObject;
    }

    @Override
    public void createNewVehicleInDatabase(Vehicle vehicle) {

    }
}
