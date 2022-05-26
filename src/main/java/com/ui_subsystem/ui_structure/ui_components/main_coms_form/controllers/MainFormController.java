package com.ui_subsystem.ui_structure.ui_components.main_coms_form.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ui_subsystem.UserInterfaceConnectorDescriptor;
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
 * Class to manage the actions made in the Main Form. It also acts as Observer for the Main Form components
 * and as Observable by the User Interface subsystem connector
 */
public class MainFormController implements PropertyChangeListener {

    JPanel interactivePanel;

    //DatabaseConnector is an Observer for the Databases' structures and an Observable for the AppArchitecture core,
    // so this variable throws a notification to the AppArchitecture core when an event occurs in this class, and it has
    // an interest in the AppArchitecture core
    private final PropertyChangeSupport support;

    public MainFormController(JPanel interactivePanel) {
        this.interactivePanel = interactivePanel;
        support = new PropertyChangeSupport(this);
    }

    /**
     * Attach the Observers that will get notified when a change or request is made in this class
     * @param pcl the Observer' property change listener
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    /**
     * PropertyChangeListener function to listen and manage the changes produced by the different Observables where the
     * MainForm controller is connected whe acting as an Observer
     *
     * @param evt the event thrown
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String evtName = evt.getPropertyName();
        try {
            //The MainFormController, acting as Observer, drives the event to the User Interface connector with the
            // information of the event received from the corresponding Observable that previously throws the event. It
            // also receives events from the User Interface connector Observer so, when needed, it drives the event received to
            // the corresponding component of the Main Form according to the event name received. Also, when acting as Observable,
            // it drives the event received by the User Interface connector to the corresponding component according to the
            // event name received
            switch (evtName) {
                case "createElementIntoDB":{
                    //Send the element created to the app architecture core through the User Interface connector
                    support.firePropertyChange(evt);
                    break;
                }
                case "readTableFromUIQuery":{
                    //Send the query from the user interface components to the app architecture core through te User Interface connector
                    support.firePropertyChange(evt.getPropertyName(), new MainFormDescriptor(), null);
                    break;
                }
                case "readTableResponse": {
                        ObjectMapper objectMapper = new ObjectMapper();
                        List<Person> personList = objectMapper.readValue(evt.getNewValue().toString(), new TypeReference<List<Person>>() { });
                        addPersonListToElementListPanel(personList);
                    break;
                }
                case "removeElementFromDB":{
                    if (evt.getOldValue() instanceof UserInterfaceConnectorDescriptor)
                        support.firePropertyChange(evt);
                    break;
                }
                case "elementRemovedFromDB":{
                    System.out.println("Element removed successfully");
                    break;
                }
                default:{
                    break;
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add the elements list retrieved from the database to the elements list table placed in the Main Form
     * @param personList the persons list to be added
     */
    private void addPersonListToElementListPanel(List<Person> personList) {
        for (Component interactiveAppPanelCmp : this.interactivePanel.getComponents())
            if (interactiveAppPanelCmp instanceof JPanel)
                if (interactiveAppPanelCmp.getName().equals("formAndListPanel")) {
                    JPanel formAndListPanel = (JPanel) interactiveAppPanelCmp;
                    for (Component formAndListPanelCmp : formAndListPanel.getComponents())
                        if (formAndListPanelCmp instanceof JPanel)
                            if (formAndListPanelCmp.getName().equals("elementsListPanel")) {
                                JPanel elementsListPanel = (JPanel) formAndListPanelCmp;
                                for (Component elementsListPanelCmp : elementsListPanel.getComponents())
                                    if (elementsListPanelCmp instanceof JScrollPane) {
                                        JScrollPane elementsListScrollPane = (JScrollPane) elementsListPanelCmp;
                                        JViewport viewport = elementsListScrollPane.getViewport();
                                        JTable elementsListTable = (JTable) viewport.getView();
                                        ((ElementsListTableModel) elementsListTable.getModel()).forceUpdate(personList);
                                        break;
                                    }
                            }
                }
    }

    public void readDataFromDB() {
        if (support != null) support.firePropertyChange("readTableFromUIQuery", new MainFormDescriptor(), null);
    }
}
