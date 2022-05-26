package com.dbs_subsystem;

import com.dbs_subsystem.database_structure.DatabaseType;
import com.dbs_subsystem.database_structure.IDatabaseStructure;

import java.util.List;

/**
 * Builder class to create and instantiate the Database Connector to establish the connection with the Application architecture
 */
public class DatabaseConnectorBuilder implements IDatabaseConnectorBuilder {

    DatabaseConnector databaseConnector;

    /**
     * Function to build the database connector according to the structures specified by the user
     *
     * @param databaseStructure the list of database structures to be added to the connector
     * @return an instance of the builder with the structures already defined
     */
    @Override
    public DatabaseConnectorBuilder buildModuleStructures(List<DatabaseType> databaseStructure) {
        if (databaseConnector == null) {
            if (databaseStructure != null && !databaseStructure.isEmpty())
                this.databaseConnector = new DatabaseConnector(databaseStructure);
            assert this.databaseConnector != null;  //ensures that .getDatabaseStructureList() is not null to avoid NullPointerException
            for (IDatabaseStructure dbStructure : this.databaseConnector.getDatabaseStructureList()) {
                dbStructure.addPropertyChangeListener(this.databaseConnector);
            }
        }
        return this;
    }

    /**
     * Function to create a new Database Connector Builder instance
     *
     * @return a new database connector builder instance
     */
    @Override
    public DatabaseConnectorBuilder newBuilder() {
        return new DatabaseConnectorBuilder();
    }

    /**
     * Function to return an instance of the Database Connector class with all its properties already initialized
     *
     * @return the Database Connector instance already built with the properties specified in the builder call
     */
    @Override
    public DatabaseConnector build() {
        return this.databaseConnector;
    }
}
