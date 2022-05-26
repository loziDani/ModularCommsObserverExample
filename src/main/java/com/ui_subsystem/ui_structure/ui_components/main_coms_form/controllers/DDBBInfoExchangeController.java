package com.ui_subsystem.ui_structure.ui_components.main_coms_form.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ui_subsystem.UserInterfaceConnectorDescriptor;
import com.ui_subsystem.ui_structure.ui_components.main_coms_form.models.ElementsListTableModel;
import com.ui_subsystem.ui_structure.ui_components.main_coms_form.models.Person;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

/**
 * Controller class to manage the performed by the buttons of the main form interface
 */
public class DDBBInfoExchangeController implements ActionListener {

    JTable elementsListTable;
    JTextField ipAddressTextField;
    JTextField portTextField;
    JTextField nameTextField;
    JTextField surnameTextField;
    JTextField phoneTextField;
    JButton elementsListButton;

    //This support variable allows to add and remove observers
    private PropertyChangeSupport support;

    /**
     * Constructor used by Send Data button in the Entry Form panel
     *
     * @param ipAddress             IP address JTextField
     * @param port                  port JTextField
     * @param name                  name JTextField
     * @param surname               surname JTextField
     * @param phone                 phone JTextField
     */
    public DDBBInfoExchangeController(JTextField ipAddress,
                                      JTextField port, JTextField name, JTextField surname,
                                      JTextField phone) {
        this.ipAddressTextField = ipAddress;
        this.portTextField = port;
        this.nameTextField = name;
        this.surnameTextField = surname;
        this.phoneTextField = phone;
        this.support = new PropertyChangeSupport(this);
    }

    /**
     * Constructor used by the Elements List panel buttons
     *
     * @param elementsListTable     elements list JTable
     * @param elementListButton     the corresponding Elements List panel button
     * @param formTextFields        HashMap with the JTextFields available in the Entry Form panel
     */
    public DDBBInfoExchangeController(JTable elementsListTable,
                                      JButton elementListButton, HashMap<String, JTextField> formTextFields) {
        this.elementsListTable = elementsListTable;
        this.elementsListButton = elementListButton;
        this.ipAddressTextField = formTextFields.get("IPTextField");
        this.portTextField = formTextFields.get("portTextField");
        this.nameTextField = formTextFields.get("nameTextField");
        this.surnameTextField = formTextFields.get("surnameTextField");
        this.phoneTextField = formTextFields.get("phoneTextField");
        this.support = new PropertyChangeSupport(this);
    }

    /**
     * Attach the Observers that will get notified when a change or request is made in this class
     * @param pcl the Observer' property change listener
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    /**
     * Function to fire the event corresponding to the button clicked
     *
     * @param e the event fired
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton)
            switch (((JButton) e.getSource()).getName()) {
                case "sendDataButton": {
                    createElement();
                    break;
                }
                case "refreshButton": {
                    updateElementsListTable();
                    break;
                }
                case "deleteButton":
                    deleteSelectedElementFromList();
            }
    }

    /**
     * Function to create a new element in the database and add it to the Elements List table. It checks
     * if the fields are properly filled and if the element exists, so the element can be only updated in
     * case it is already registered in the system
     */
    private void createElement() {
        if (this.ipAddressTextField.getText().equals("") || this.portTextField.getText().equals("") ||
                this.nameTextField.getText().equals("") || this.surnameTextField.getText().equals("") ||
                this.phoneTextField.getText().equals("")) {
            String messageDialog = "Fill all text boxes to send and save the data";
            JOptionPane.showMessageDialog(null, messageDialog, "WARNING",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
                //Actions to perform when using the Observer pattern
                Person person = new Person(0, this.ipAddressTextField.getText(), this.portTextField.getText(),
                        this.nameTextField.getText(), this.surnameTextField.getText(), this.phoneTextField.getText());
                //Converts the new Person created to JSON to be sent within the event notification to its corresponding observer
                ObjectMapper objectMapper = new ObjectMapper();
                String personToJson = objectMapper.writeValueAsString(person);
                support.firePropertyChange("createElementIntoDB", new UserInterfaceConnectorDescriptor(), personToJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        this.ipAddressTextField.setText("");
        this.portTextField.setText("");
        this.nameTextField.setText("");
        this.surnameTextField.setText("");
        this.phoneTextField.setText("");
    }

    /**
     * Function to refresh the full Elements List table with the information existing in the database
     */
    private void updateElementsListTable() {
        //Action when the Observer Pattern is used. It sends a read database table query to the database subsystem through
        // its corresponding observer
        support.firePropertyChange("readTableFromUIQuery", new UserInterfaceConnectorDescriptor(), null);
    }

    /**
     * Function to delete the selected element form the Elements List table. It checks also if there is
     * an element selected, and popups a message in case there is no selection
     */
    private void deleteSelectedElementFromList() {
        if (this.elementsListTable.getSelectedRow() == -1) {
            String messageDialog = "No element selected to be deleted";
            JOptionPane.showMessageDialog(null, messageDialog, "WARNING",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            Person person = ((ElementsListTableModel) this.elementsListTable.getModel()).getPersonDataAt(this.elementsListTable.getSelectedRow());
            //Converts the element to be deleted in a JSON package so the event with the information of the element to be deleted can be sent
            // through its corresponding observer to the database subsystem
            ObjectMapper objectMapper = new ObjectMapper();
            String personToJson = objectMapper.writeValueAsString(person);
            support.firePropertyChange("removeElementFromDB", new UserInterfaceConnectorDescriptor(), personToJson);
            updateElementsListTable();
            clearTextFields();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to clear all the JTextFields of the view
     */
    private void clearTextFields() {
        this.ipAddressTextField.setText("");
        this.portTextField.setText("");
        this.nameTextField.setText("");
        this.surnameTextField.setText("");
        this.phoneTextField.setText("");
        this.elementsListTable.clearSelection();
    }
}
