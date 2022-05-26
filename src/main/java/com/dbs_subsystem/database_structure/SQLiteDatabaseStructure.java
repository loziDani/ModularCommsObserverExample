package com.dbs_subsystem.database_structure;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * DAO (Data Access Object) class containing the methods needed to communicate the application with a SQLite database
 */
public class SQLiteDatabaseStructure implements IDatabaseStructure {

    SQLiteDatabaseStructureDescriptor instanceDescriptor;

    //As Observable, this class is observed by the DatabaseConnector class, so this variable throws a notification
    // to the DatabaseConnector when an event occurs in this class, and it has an interest in the DatabaseConnector
    private final PropertyChangeSupport support;

    public SQLiteDatabaseStructure (){
        this.instanceDescriptor = new SQLiteDatabaseStructureDescriptor();
        support = new PropertyChangeSupport(this);
    }

    public SQLiteDatabaseStructureDescriptor getInstanceDescriptor() {
        return instanceDescriptor;
    }

    /**
     * Attach the Observers that will get notified when a change or request is made in this class
     * @param pcl the Observer' property change listener
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl){
        support.addPropertyChangeListener(pcl);
    }

    /**
     * Function to open the SQLite database of the Application. It creates a new one (empty,
     * with no tables, in case it doesn't exist)
     * @return the Connection object to the database
     */
    @Override
    public Connection openDatabase() {
        try {
            Connection conn;
            String url = System.getProperty("user.dir");
            String sqlitePath = "\\sysdb.db";
            url = "jdbc:sqlite:" + url + sqlitePath;
            conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Function to close the database connection
     */
    @Override
    public void closeDatabase() {

    }

    /**
     * Function to check if exists a database connection
     */
    @Override
    public void checkDatabaseConnection() {

    }

    /**
     * Function to create a new element in the database. If the element already exists,
     * it is updated.
     * @param databaseObject the object to be added / updated to the database
     * @return the object already added with the id registered in the database
     */
    @Override
    public DatabaseObject createElement(DatabaseObject databaseObject) {

        DatabaseObject elementExists = checkElementExists(databaseObject);

        if (elementExists != null){
            updateElement(elementExists);
            return elementExists;
        }
        else {
            String sql = "INSERT INTO Person (ipAddress, port, name, surname, phone, creationDate, modificationDate, enabled)" +
                    " VALUES (?,?,?,?,?,?,?,?)";

            try (Connection conn = this.openDatabase(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, (databaseObject.getDestinationIP() == null) ? "" : databaseObject.getDestinationIP());
                pstmt.setString(2, (databaseObject.getDestinationPort() == null) ? "" : databaseObject.getDestinationPort());
                pstmt.setString(3, (databaseObject.getName() == null) ? "" : databaseObject.getName());
                pstmt.setString(4, (databaseObject.getSurname() == null) ? "" : databaseObject.getSurname());
                pstmt.setString(5, (databaseObject.getPhoneNumber() == null) ? "" : databaseObject.getPhoneNumber());
                pstmt.setString(6, new Date().toString());
                pstmt.setString(7, new Date().toString());
                pstmt.setBoolean(8, true);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            DatabaseObject databaseObjectWithId = getElementWithId(databaseObject);
            //As Observable, this DAO class notifies to its Observer that a new request or change has been made into the database
            // returning the event name and the result of the action
            support.firePropertyChange("createdElementWithId", this.instanceDescriptor, databaseObjectWithId);
            return databaseObjectWithId;
        }
    }

    /**
     * Function to read from the database table all the enabled (not previously deleted) elements
     * @return the list of the elements registered and enabled in the database
     */
    @Override
    public ArrayList<DatabaseObject> readTable() {
        ArrayList<DatabaseObject> databaseObjects = new ArrayList<>();

        String sql = "SELECT * FROM Person WHERE enabled = '1'";
        try (Connection conn = this.openDatabase(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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
     * Function to update an existing element to the database
     * @param databaseObject the object to be updated
     */
    @Override
    public void updateElement(DatabaseObject databaseObject) {
        String sql = "UPDATE Person SET ipAddress = ?, port = ?, name = ?, surname = ?, phone = ? WHERE id = ? AND enabled = '1'";

        try (Connection conn = this.openDatabase(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, databaseObject.getDestinationIP());
            pstmt.setString(2, databaseObject.getDestinationPort());
            pstmt.setString(3, databaseObject.getName());
            pstmt.setString(4, databaseObject.getSurname());
            pstmt.setString(5, databaseObject.getPhoneNumber());
            pstmt.setInt(6, databaseObject.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to delete (disable) an element from the database
     * @param databaseObject the object to be deleted (disabled)
     */
    @Override
    public void deleteElement(DatabaseObject databaseObject) {
        String sql = "UPDATE Person SET enabled = " + false + " WHERE id = ?";

        try (Connection conn = this.openDatabase(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, databaseObject.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        support.firePropertyChange("elementRemovedFromDB", this.instanceDescriptor, null);
    }

    /**
     * Function to get the element passed by parameter with its corresponding id retrieved from the database
     * @param databaseObject the object which id will be retrieved
     * @return the object with its corresponding database id
     */
    @Override
    public DatabaseObject getElementWithId(DatabaseObject databaseObject) {
        String sql = "SELECT * FROM Person WHERE ipAddress = '" + databaseObject.getDestinationIP() +
                "' AND port = '" + databaseObject.getDestinationPort() + "' AND name = '" +
                databaseObject.getName() + "' AND surname = '" + databaseObject.getSurname() + "' AND phone = '" + databaseObject.getPhoneNumber() + "'";

        DatabaseObject databaseObjectWithId = null;
        try (Connection conn = this.openDatabase(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()){
                databaseObjectWithId = new DatabaseObject(rs.getInt("id"), rs.getString("ipAddress"),
                        rs.getString("port"), rs.getString("name"),
                        rs.getString("surname"), rs.getString("phone"));
            }
            return databaseObjectWithId;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Function to check if an element already exists in the database
     * @param databaseObject the object to be checked
     * @return the existing database object if any.
     */
    @Override
    public DatabaseObject checkElementExists(DatabaseObject databaseObject) {
        String sql = "SELECT * FROM Person WHERE ipAddress = '" + databaseObject.getDestinationIP() +
                "' AND port = '" + databaseObject.getDestinationPort() + "' AND name = '" + databaseObject.getName() +
                "' AND surname = '" + databaseObject.getSurname() + "' AND phone = '" + databaseObject.getPhoneNumber() + "' AND enabled = '1'";
        DatabaseObject existingDatabaseObject = null;
        try (Connection conn = this.openDatabase(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()){
                existingDatabaseObject = new DatabaseObject(rs.getInt("id"), rs.getString("ipAddress"),
                        rs.getString("port"), rs.getString("name"),
                        rs.getString("surname"), rs.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existingDatabaseObject;
    }

    @Override
    public void createNewVehicleInDatabase(Vehicle vehicle) {
        //TODO: Set the corresponding call to the database to add the new object.
    }
}
