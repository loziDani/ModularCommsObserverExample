package com.dbs_subsystem.database_structure;

import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * DAO (Data Access Object) class containing the methods needed to communicate the application with a MongoDB database
 */
public class MongoDBDatabaseStructure implements IDatabaseStructure {

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {

    }

    @Override
    public Connection openDatabase() {return null;}

    @Override
    public void closeDatabase() {}

    @Override
    public void checkDatabaseConnection() {}

    @Override
    public DatabaseObject createElement(DatabaseObject databaseObject) { return null; }

    @Override
    public ArrayList<DatabaseObject> readTable() {
        return null;
    }

    @Override
    public void updateElement(DatabaseObject databaseObject) {}

    @Override
    public void deleteElement(DatabaseObject databaseObject) { }

    @Override
    public DatabaseObject getElementWithId(DatabaseObject databaseObject) {
        return null;
    }

    @Override
    public DatabaseObject checkElementExists(DatabaseObject databaseObject) {
        return null;
    }

    @Override
    public void createNewVehicleInDatabase(Vehicle vehicle) {

    }
}
