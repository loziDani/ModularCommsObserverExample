package com.app_architecture;

import com.dbs_subsystem.DatabaseConnector;
import com.dbs_subsystem.database_structure.DatabaseType;
import com.dbs_subsystem.DatabaseConnectorBuilder;
import com.ui_subsystem.UserInterfaceConnector;
import com.ui_subsystem.UserInterfaceConnectorBuilder;
import com.ui_subsystem.ui_structure.UserInterfaceComponents;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to build the app architecture specified by the builder construction
 */
public class AppArchitectureBuilder implements IAppArchitectureBuilder {

    final AppArchitecture appArchitecture = new AppArchitecture();

    /**
     * Builder constructor
     */
    public AppArchitectureBuilder() {
        this.appArchitecture.subsystemConnectorList = new ArrayList<>();
    }

    /**
     * Function to enable the Database Subsystem
     *
     * @param selectedModuleStructure List containing the selected structures to be added to the subsystem
     * @return the class with the Database subsystem variable already initialized
     */
    @Override
    public AppArchitectureBuilder enableDatabaseSubsystem(List<Object> selectedModuleStructure) {
        if (selectedModuleStructure != null && !selectedModuleStructure.isEmpty()) {
            List<DatabaseType> databaseTypesList = null;
            for (Object modelStructure : selectedModuleStructure)
                if (modelStructure instanceof ArrayList) {
                    ArrayList modelStructureSelected = (ArrayList) modelStructure;
                    if (!modelStructureSelected.isEmpty())
                        if (modelStructureSelected.get(0) instanceof DatabaseType)
                            databaseTypesList = modelStructureSelected;
                }
            DatabaseConnector databaseConnector = new DatabaseConnectorBuilder().newBuilder()
                    .buildModuleStructures(databaseTypesList)
                    .build();
            this.appArchitecture.subsystemConnectorList.add(databaseConnector);
        }
        return this;
    }

    /**
     * Function to enable the User Interface Subsystem
     *
     * @param selectedModuleStructure List containing the selected structures to be added to the subsystem
     * @return the class with the User Interface subsystem variable already initialized
     */
    @Override
    public AppArchitectureBuilder enableUserInterfaceSubsystem(List<Object> selectedModuleStructure) {
        if (selectedModuleStructure != null && !selectedModuleStructure.isEmpty()) {
            List<UserInterfaceComponents> userInterfaceComponentsList = null;
            for (Object modelStructure : selectedModuleStructure)
                if (modelStructure instanceof ArrayList) {
                    ArrayList modelStructureSelected = (ArrayList) modelStructure;
                    if (!modelStructureSelected.isEmpty())
                        if (modelStructureSelected.get(0) instanceof UserInterfaceComponents)
                            userInterfaceComponentsList = modelStructureSelected;
                }
            UserInterfaceConnector userInterfaceConnector = new UserInterfaceConnectorBuilder().newBuilder()
                    .buildModuleStructures(userInterfaceComponentsList)
                    .build();
            this.appArchitecture.subsystemConnectorList.add(userInterfaceConnector);
        }
        return this;
    }

    /**
     * Function to initialize the builder
     *
     * @return the builder already initialized
     */
    @Override
    public AppArchitectureBuilder newBuilder() {
        return new AppArchitectureBuilder();
    }

    /**
     * Function to return an instance of AppArchitecture with all the required subsystems already initialized
     *
     * @return an instance of AppArchitecture with all the required subsystems already initialized
     */
    @Override
    public AppArchitecture build() {
        return appArchitecture;
    }
}
