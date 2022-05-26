package com.dbs_subsystem;

import com.dbs_subsystem.database_structure.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dbs_subsystem.database_structure.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * In this example, this Connector class acts as Observable by the AppArchitecture, and as Observer for the databases structures listed
 * Connector to establish the communication between the Database Module and other modules of the solution to
 * provide the application with database capabilities.
 * The application connected to this module will be able to store all the information needed, so it can be retrieved
 * or exploited when needed
 */
public class DatabaseConnector implements IDatabaseConnector, PropertyChangeListener {

    DatabaseConnectorDescriptor instanceDescriptor;
    final List<IDatabaseStructure> databaseStructureList;

    //DatabaseConnector is an Observer for the Databases' structures and an Observable for the AppArchitecture core,
    // so this variable throws a notification to the AppArchitecture core when an event occurs in this class, and it has
    // an interest in the AppArchitecture core
    private PropertyChangeSupport support;

    /**
     * Constructor to create and instantiate the set of database structures selected by the user
     *
     * @param databaseStructure the list of the database structures to be added to the module
     */
    public DatabaseConnector(List<DatabaseType> databaseStructure) {
        this.instanceDescriptor = new DatabaseConnectorDescriptor();
        this.databaseStructureList = new ArrayList<>();
        if (databaseStructure != null && !databaseStructure.isEmpty()) {
            for (DatabaseType databaseTypeSelected : databaseStructure) {
                switch (databaseTypeSelected) {
                    case SQLITE:
                        SQLiteDatabaseStructure sqLiteDatabaseStructure = new SQLiteDatabaseStructure();
                        this.databaseStructureList.add(sqLiteDatabaseStructure);
                        break;
                    case MONGODB:
                        MongoDBDatabaseStructure mongoDBDatabaseStructure = new MongoDBDatabaseStructure();
                        this.databaseStructureList.add(mongoDBDatabaseStructure);
                        break;
                    case MYSQL:
                        MySQLDatabaseStructure otherDatabaseTypeStructure = new MySQLDatabaseStructure();
                        this.databaseStructureList.add(otherDatabaseTypeStructure);
                        break;
                    default:
                        break;
                }
            }
            support = new PropertyChangeSupport(this);
        }
    }

    public DatabaseConnectorDescriptor getInstanceDescriptor() {
        return instanceDescriptor;
    }

    /**
     * Attach the Observers that will get notified when a change or request is made in this class
     * @param pcl the Observer' property change listener
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public List<IDatabaseStructure> getDatabaseStructureList() {
        return databaseStructureList;
    }

    /**
     * PropertyChangeListener function to listen and manage the changes produced by the databases structures listed
     * or by the AppArchitecture Observer
     *
     * @param evt the event thrown
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String evtName = evt.getPropertyName();
        try {
            //The Database Connector, acting as Observer, drives the event to the AppArchitecture core with the
            // information of the event received from the corresponding Observable that previously throws the event. It
            // also receives events from the AppArchitecture Observer so, when needed, it drives the event received to
            // the corresponding structure of the Database Subsystem according to the event name received
            switch (evtName) {
                case "createElementIntoDB": {
                    //Go to the corresponding database structure to create an element into the database
                    ObjectMapper objectMapper = new ObjectMapper();
                    DatabaseObject databaseObject = objectMapper.readValue(evt.getNewValue().toString(), DatabaseObject.class);
                    for (IDatabaseStructure databaseStructure : this.databaseStructureList) {
                        databaseStructure.createElement(databaseObject);
                    }
                    break;
                }
                case "readTableFromUIQuery":{
                    //Go to the corresponding database structure to read the entities table
                    for (IDatabaseStructure databaseStructure : this.databaseStructureList){
                        databaseStructure.readTable();
                    }
                    break;
                }
                case "readTableResponse":{
                    //Go from the database connector to outside with the list of the entities read from the database already marshalled
                    ObjectMapper objectMapper = new ObjectMapper();
                    String marshalledDatabaseObjectsList = objectMapper.writeValueAsString(evt.getNewValue());
                    support.firePropertyChange(evt.getPropertyName(), this.instanceDescriptor, marshalledDatabaseObjectsList);
                    break;
                }
                case "createdElementWithId":{
                    //Go from the database connector to outside with the recent entity registered in the database with its assigned id to update the entity information in the required subsystems
                    Object obj = evt.getNewValue();
                    if (obj instanceof DatabaseObject) {
                        System.out.println("From DatabaseConnector - Event: + " + evtName + ". Object name: " + ((DatabaseObject) obj).getName() + " " + ((DatabaseObject) obj).getSurname());
                        //Convert the DatabaseObject to JSON as the PropertyChangeEvent sent to the AppArchitecture core must not be a DatabaseObject,
                        // as it is supposed that AppArchitecture has no knowledge about the DatabaseObject class, as this class belongs to the Database Subsystem.
                        // Remember that the DatabaseConnector is an Observable from the AppArchitecture core perspective
                        ObjectMapper objectMapper = new ObjectMapper();
                        String databaseObjectToString = objectMapper.writeValueAsString(evt.getNewValue());
                        support.firePropertyChange(evt.getPropertyName(), this.instanceDescriptor, databaseObjectToString);
                    }
                    break;
                }
                case "removeElementFromDB":{
                    //Go to the corresponding database structure to remove the element requestd from the app architecture core
                    ObjectMapper objectMapper = new ObjectMapper();
                    DatabaseObject databaseObject = objectMapper.readValue(evt.getNewValue().toString(), DatabaseObject.class);
                    for (IDatabaseStructure databaseStructure : this.databaseStructureList)
                        databaseStructure.deleteElement(databaseObject);
                    break;
                }
                case "elementRemovedFromDB":{
                    //Notify the app architecture that an element has been deleted successfully through the database connector
                    support.firePropertyChange(evt.getPropertyName(), this.instanceDescriptor, null);
                }
                default: {
                    break;
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
