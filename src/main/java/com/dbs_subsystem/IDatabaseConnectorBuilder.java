package com.dbs_subsystem;


import com.dbs_subsystem.database_structure.DatabaseType;

import java.util.List;

/**
 * Interface to define the functions needed by the Database Connector Builder
 */
public interface IDatabaseConnectorBuilder {

    DatabaseConnectorBuilder buildModuleStructures(List<DatabaseType> databaseStructure);
    DatabaseConnectorBuilder newBuilder();
    DatabaseConnector build();

}
