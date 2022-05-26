package com.app_architecture;

import com.dbs_subsystem.DatabaseConnector;
import com.ui_subsystem.UserInterfaceConnector;
import java.util.ArrayList;

/**
 * In this example, the AppArchitecture class acts as Observer for the different Connectors listed (only working with the DB and UI Connectors)
 * Class to create and build the main app architecture with the required functionalities. The instances of this class
 * must be immutables, as the Application Architecture must not be modified during runtime. It has only getters to make
 * the instances immutables.
 */
public class AppArchitecture {

    /**
     * Declaration of all possible modules that can be part of the App Architecture
     */
    ArrayList subsystemConnectorList;
    AppArchitectureController appArchitectureController;

    public AppArchitecture(){
    }

    /**
     * Gets the Database Connector instance of the AppArchitecture class
     * @return the Database Connector instance
     */
    public DatabaseConnector getDatabaseConnector() {
        if (this.subsystemConnectorList != null && !this.subsystemConnectorList.isEmpty()){
            for (Object connector : this.subsystemConnectorList)
                if (connector instanceof DatabaseConnector)
                    return (DatabaseConnector) connector;
        }
        return null;
    }

    /**
     * Gets the subsystems connectors list
     * @return anArrayList containing the subsystems connectors list
     */
    public ArrayList getSubsystemConnectorList() {
        return subsystemConnectorList;
    }

    /**
     * Gets the User Interface Connector instance of the AppArchitecture class
     * @return the User Interface Connector instance
     */
    public UserInterfaceConnector getUserInterfaceConnector() {
        if (this.subsystemConnectorList != null && !this.subsystemConnectorList.isEmpty()){
            for (Object connector : this.subsystemConnectorList)
                if (connector instanceof UserInterfaceConnector)
                    return (UserInterfaceConnector) connector;
        }
        return null;
    }

    /**
     * Add the corresponding controller to the AppArchitecture instance
     * @param appArchitectureController the Application Architecture controller to be added
     */
    public void addAppArchitectureController(AppArchitectureController appArchitectureController){
        this.appArchitectureController = appArchitectureController;
    }

    /**
     * Get the Application Architecture controller instance
     * @return the Application Architecture instance
     */
    public AppArchitectureController getAppArchitectureController() {
        return appArchitectureController;
    }
}
