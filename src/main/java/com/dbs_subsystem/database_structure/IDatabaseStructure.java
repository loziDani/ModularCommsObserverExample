package com.dbs_subsystem.database_structure;

import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Interface to define the functions that describe a database structure
 */
public interface IDatabaseStructure {

    void addPropertyChangeListener(PropertyChangeListener pcl);
    Connection openDatabase();
    void closeDatabase();
    void checkDatabaseConnection();
    DatabaseObject createElement(DatabaseObject databaseObject);
    ArrayList<DatabaseObject> readTable();
    void updateElement(DatabaseObject databaseObject);
    void deleteElement(DatabaseObject databaseObject);
    DatabaseObject getElementWithId(DatabaseObject databaseObject);
    DatabaseObject checkElementExists(DatabaseObject databaseObject);
    void createNewVehicleInDatabase(Vehicle vehicle);

}
