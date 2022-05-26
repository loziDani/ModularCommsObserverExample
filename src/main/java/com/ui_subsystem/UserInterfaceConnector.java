package com.ui_subsystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ui_subsystem.ui_structure.IUserInterfaceStructure;
import com.ui_subsystem.ui_structure.ui_components.IScreenResolution;
import com.ui_subsystem.ui_structure.ui_components.main_coms_form.MainForm;
import com.ui_subsystem.ui_structure.ui_components.main_coms_form.MainFormDescriptor;
import com.ui_subsystem.ui_structure.ui_components.main_coms_form.models.ElementsListTableModel;
import com.ui_subsystem.ui_structure.ui_components.main_coms_form.models.Person;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * In this example, this Connector class acts as Observable by the AppArchitecture, and as Observer for the User Interface components listed
 * Connector to establish the communication between the User Interface Module and other modules of the solution to
 * provide the application with User Interface capabilities.
 * The application connected to this module will be able to display information according to the graphical components added
 */
public class UserInterfaceConnector implements IUserInterfaceConnector, PropertyChangeListener {

    UserInterfaceConnectorDescriptor instanceDescriptor;
    IScreenResolution screenResolution;
    List<IUserInterfaceStructure> userInterfaceStructures;

    //UserInterfaceConnector is an Observer for the User Interfaces' structures and an Observable for the AppArchitecture core,
    // so this variable throws a notification to the AppArchitecture core when an event occurs in this class, and it has
    // an interest in the AppArchitecture core
    private final PropertyChangeSupport support;

    public UserInterfaceConnector() {
        this.instanceDescriptor = new UserInterfaceConnectorDescriptor();
        this.support = new PropertyChangeSupport(this);
    }

    public UserInterfaceConnectorDescriptor getInstanceDescriptor() {
        return instanceDescriptor;
    }

    public List<IUserInterfaceStructure> getUserInterfaceStructures() {
        return userInterfaceStructures;
    }

    /**
     * Attach the Observers that will get notified when a change or request is made in this class
     * @param pcl the Observer' property change listener
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    /**
     * PropertyChangeListener function to listen and manage the changes produced by the user interface structures listed
     * or by the AppArchitecture Observer
     *
     * @param evt the event thrown
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String evtName = evt.getPropertyName();
        try {
            //The User Interface Connector, acting as Observer, drives the event to the AppArchitecture core with the
            // information of the event received from the corresponding Observable that previously throws the event. It
            // also receives events from the AppArchitecture Observer so, when needed, it drives the event received to
            // the corresponding structure of the User Interface Subsystem according to the event name received
            switch (evtName) {
                case "createElementIntoDB":{
                    //Send the element created by the user interface component to the app architecture core
                    support.firePropertyChange(evt);
                    break;
                }
                case "createdElementWithId": {
                    //Go to the corresponding User Interface component to update the entity received from the app architecture core when an entity is recently created in the system
                    ObjectMapper objectMapper = new ObjectMapper();
                    Person person = objectMapper.readValue(evt.getNewValue().toString(), Person.class);
                    for (IUserInterfaceStructure userInterfaceStructure : this.userInterfaceStructures) {
                        if (userInterfaceStructure instanceof MainForm) {
                            updateElementInUIElementsList((MainForm) userInterfaceStructure, person);
                        }
                    }
                    break;
                }
                case "readTableFromUIQuery":{
                    //Go to the app architecture core to ask for the entities' information in the database
                    if (evt.getOldValue() instanceof MainFormDescriptor)
                        support.firePropertyChange(evtName, this.instanceDescriptor, null);
                    break;
                }
                case "readTableResponse":{
                    //Go to the corresponding User Interface component to manage the list of entities read from the database
                    for (IUserInterfaceStructure userInterfaceStructure : this.userInterfaceStructures) {
                        if (userInterfaceStructure instanceof MainForm) {
                            ((MainForm) userInterfaceStructure).getMainFormController().propertyChange(evt);
                        }
                    }
                    break;
                }
                case "removeElementFromDB":{
                    //Go to the app architecture core to ask for the deletion of an entity
                    if (evt.getOldValue() instanceof UserInterfaceConnectorDescriptor)
                        support.firePropertyChange(evt);
                    break;
                }
                case "elementRemovedFromDB":{
                    //Notify the corresponding user interface component that the deletion requested was performed successfully
                    for (IUserInterfaceStructure userInterfaceStructure : this.userInterfaceStructures){
                        if (userInterfaceStructure instanceof MainForm)
                            ((MainForm) userInterfaceStructure).getMainFormController().propertyChange(evt);
                    }
                    break;
                }
                default: {
                    break;
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the element received from the AppApplication core into the elements list table of the User Interface window
     * @param mainForm the form where the elements list table is placed
     * @param person the element to be updated
     */
    private void updateElementInUIElementsList(MainForm mainForm, Person person) {
        ElementsListTableModel elementsListTableModel;
        for (Component component : mainForm.getMainInteractiveAppPanel().getComponents()){
            if (component instanceof JPanel)
                if (component.getName().equals("formAndListPanel")){
                    JPanel formAndListPanel = (JPanel) component;
                    for (Component formAndListPanelComponent : formAndListPanel.getComponents()) {
                        if (formAndListPanelComponent instanceof JPanel)
                            if (formAndListPanelComponent.getName().equals("elementsListPanel")) {
                                JPanel elementsListPanel = (JPanel) formAndListPanelComponent;
                                for (Component elementsListPanelComponent : elementsListPanel.getComponents()) {
                                    if (elementsListPanelComponent instanceof JScrollPane) {
                                        JScrollPane elementsListScrollPane = (JScrollPane) elementsListPanelComponent;
                                        JViewport viewport = elementsListScrollPane.getViewport();
                                        JTable elementsListTable = (JTable) viewport.getView();
                                        elementsListTableModel = (ElementsListTableModel) elementsListTable.getModel();
                                        elementsListTableModel.add(person);
                                        break;
                                    }
                                }
                            }
                    }
                }
        }
    }
}
