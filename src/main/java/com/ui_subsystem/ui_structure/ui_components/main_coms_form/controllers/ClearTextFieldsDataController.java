package com.ui_subsystem.ui_structure.ui_components.main_coms_form.controllers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class to clean the JTextFields of the UI
 */
public class ClearTextFieldsDataController implements ActionListener {

    JTextField ipAddressTextField;
    JTextField portTextField;
    JTextField nameTextField;
    JTextField surnameTextField;
    JTextField phoneTextField;

    /**
     * Constructor to create an instance of the controller class with all the JTextFields to be managed
     * @param ipAddressTextField IP address JTextField
     * @param portTextField port JTextField
     * @param nameTextField name JTextField
     * @param surnameTextField surname JTextField
     * @param phoneTextField phone JTextField
     */
    public ClearTextFieldsDataController(JTextField ipAddressTextField, JTextField portTextField,
                                         JTextField nameTextField, JTextField surnameTextField,
                                         JTextField phoneTextField){
        this.ipAddressTextField = ipAddressTextField;
        this.portTextField = portTextField;
        this.nameTextField = nameTextField;
        this.surnameTextField = surnameTextField;
        this.phoneTextField = phoneTextField;
    }

    /**
     * Function that fires the event to lean all the JTextFields
     * @param e the event fired
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.ipAddressTextField.setText("");
        this.portTextField.setText("");
        this.nameTextField.setText("");
        this.surnameTextField.setText("");
        this.phoneTextField.setText("");
    }
}
