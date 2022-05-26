package com.ui_subsystem.ui_structure.ui_components.main_coms_form.controllers;

import com.ui_subsystem.ui_structure.ui_components.main_coms_form.models.ElementsListTableModel;
import com.ui_subsystem.ui_structure.ui_components.main_coms_form.models.Person;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.HashMap;

/**
 * Listener class to retrieve the information of an element selected in the Elements List table
 * and set it to the corresponding JTextFields
 */
public class ElementsListTableSelectionListener implements ListSelectionListener {

    JTable elementsListTable;
    JTextField ipAddressTextField;
    JTextField portTextField;
    JTextField nameTextField;
    JTextField surnameTextField;
    JTextField phoneTextField;

    /**
     * Constructor to instantiate the Selection Listener class
     * @param elementsListTable the Elements List table
     * @param formTextFields the set of UI JTextFields
     */
    public ElementsListTableSelectionListener(JTable elementsListTable, HashMap<String, JTextField> formTextFields){
        this.elementsListTable = elementsListTable;
        this.ipAddressTextField = formTextFields.get("IPTextField");
        this.portTextField = formTextFields.get("portTextField");
        this.nameTextField = formTextFields.get("nameTextField");
        this.surnameTextField = formTextFields.get("surnameTextField");
        this.phoneTextField = formTextFields.get("phoneTextField");
    }

    /**
     * Function to fire the selection event to set the information to the corresponding JTextFields
     * @param e the event fired
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (this.elementsListTable.getSelectedRow() >= 0) {
            Person person = ((ElementsListTableModel) this.elementsListTable.getModel()).getPersonDataAt(this.elementsListTable.getSelectedRow());
            this.ipAddressTextField.setText(person.getDestinationIP());
            this.portTextField.setText(person.getDestinationPort());
            this.nameTextField.setText(person.getName());
            this.surnameTextField.setText(person.getSurname());
            this.phoneTextField.setText(person.getPhoneNumber());
        }
    }
}
