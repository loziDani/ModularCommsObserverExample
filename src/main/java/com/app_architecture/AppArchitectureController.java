package com.app_architecture;

import com.dbs_subsystem.DatabaseConnectorDescriptor;
import com.ui_subsystem.UserInterfaceConnectorDescriptor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Class to manage the Observer pattern. the AppArchitecture instance acts as the main Observer of the system. This
 * controller allows the AppArchitecture instance to manage the events thrown by the different subsystems registered
 * to exchange information between subsystems that are not connected directly
 */
public class AppArchitectureController implements PropertyChangeListener {

    /**
     * The AppArchitecture instance
     */
    private final AppArchitecture appArchitecture;


    /**
     * AppArchitecture controller constructor
     * @param appArchitecture the AppArchitecture instance where this controller is added
     */
    public AppArchitectureController(AppArchitecture appArchitecture) {
        this.appArchitecture = appArchitecture;
    }

    /**
     * This function is thrown when an event occurs in any of the Observables the App Architecture core is looking at
     *
     * @param evt the event thrown
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String evtName = evt.getPropertyName();

        //The AppArchitecture controller, acting as Observer, drives the event to the corresponding subsystem according
        // to the evtName received from the Observable that previously throws the event
        switch (evtName) {
            case "createElementIntoDB":
            case "removeElementFromDB": {
                if (evt.getOldValue() instanceof UserInterfaceConnectorDescriptor)
                    appArchitecture.getDatabaseConnector().propertyChange(evt);
                break;
            }
            case "createdElementWithId":
            case "readTableResponse":
            case "elementRemovedFromDB": {
                if (evt.getOldValue() instanceof DatabaseConnectorDescriptor) {
                    appArchitecture.getUserInterfaceConnector().propertyChange(evt);
                }
                break;
            }
            case "readTableFromUIQuery": {
                if (evt.getOldValue() instanceof UserInterfaceConnectorDescriptor) {
                    appArchitecture.getDatabaseConnector().propertyChange(evt);
                }
                break;
            }
            default: {
                break;
            }
        }
    }
}
