package com.ui_subsystem.ui_structure.ui_components.main_coms_form;

import com.ui_subsystem.ui_structure.IUserInterfaceStructure;
import com.ui_subsystem.ui_structure.ui_components.main_coms_form.controllers.MainFormController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Main form class with all the user interface creation
 */
public class MainForm implements IUserInterfaceStructure {

    JPanel mainInteractiveAppPanel = new JPanel();
    MainFormController mainFormController;
    MainFormDescriptor instanceDescriptor;

    /**
     * Constructor in charge of the creation of the UI components
     */
    public MainForm(){
        this.instanceDescriptor = new MainFormDescriptor();
        JFrame mainFrame = new JFrame("MODULAR DATABASE AND USER INTERFACE OBSERVER PATTERN EXAMPLE MAIN FORM");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(true);
        mainFrame.setMinimumSize(new Dimension(900, 750));
        mainFrame.setPreferredSize(new Dimension(900, 750));
        mainFrame.setLocationRelativeTo(null);

        this.mainInteractiveAppPanel = addInteractiveAppPanel();
        mainFrame.add(mainInteractiveAppPanel);
        mainFrame.setVisible(true);
    }

    public MainFormDescriptor getInstanceDescriptor() {
        return instanceDescriptor;
    }

    public JPanel getMainInteractiveAppPanel() {
        return mainInteractiveAppPanel;
    }

    /**
     * Function to create the interactive panel
     * @return JPanel with the interactive panel already created and organized
     */
    private JPanel addInteractiveAppPanel() {
        Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(etchedBorder, "INTERACTIVE APP");
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        titledBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        JPanel interactiveAppPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10,0,0,0);
        interactiveAppPanel.add(addIPAddressPanel(), gridBagConstraints);

        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        interactiveAppPanel.add(addFormAndListPanel(), gridBagConstraints);
        interactiveAppPanel.setName("interactiveAppPanel");
        interactiveAppPanel.setBorder(titledBorder);
        return interactiveAppPanel;
    }

    /**
     * Function to create and add the IP and PORT JTextFields
     * @return JPanel with the IP and PORT input already created and organized
     */
    private JPanel addIPAddressPanel() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder("DESTINATION IP ADDRESS");
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        titledBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        JPanel ipAddressPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10,10,10,0);
        JLabel ipLabel = new JLabel("IP");
        ipAddressPanel.add(ipLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.insets = new Insets(10,5,10,10);
        JTextField ipTextField = new JTextField();
        ipTextField.setPreferredSize(new Dimension(150, 30));
        ipTextField.setMinimumSize(new Dimension(150, 30));
        ipTextField.setName("IPTextField");
        ipAddressPanel.add(ipTextField, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.insets = new Insets(10, 25, 10, 0);
        JLabel portLabel = new JLabel("PORT");
        ipAddressPanel.add(portLabel, gridBagConstraints);

        gridBagConstraints.gridx = 3;
        gridBagConstraints.insets = new Insets(10,5,10,10);
        JTextField portTextField = new JTextField();
        portTextField.setPreferredSize(new Dimension(150, 30));
        portTextField.setMinimumSize(new Dimension(150, 30));
        portTextField.setName("portTextField");
        ipAddressPanel.add(portTextField, gridBagConstraints);

        ipAddressPanel.setName("ipAddressPanel");
        ipAddressPanel.setBorder(titledBorder);
        return ipAddressPanel;
    }

    /**
     * Function to create and add the panels with the entry form and the entities list
     * @return JPanel with the components already created and organized
     */
    private JPanel addFormAndListPanel() {

        JPanel formAndListPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.NORTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        formAndListPanel.add(addEntryFormPanel(), gridBagConstraints);

        gridBagConstraints.gridx = 1;
        formAndListPanel.setName("formAndListPanel");
        formAndListPanel.add(addElementsListPanel(), gridBagConstraints);

        return formAndListPanel;
    }

    /**
     * Function to create and add the entry form panel
     * @return JPanel with the components already created and organized
     */
    private JPanel addEntryFormPanel() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder("DATA FORM");
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        titledBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        JPanel entryFormPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10,10,0,0);
        JLabel nameLabel = new JLabel("NAME");
        entryFormPanel.add(nameLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new Insets(10,5,0,0);
        JTextField nameTextField = new JTextField();
        nameTextField.setPreferredSize(new Dimension(150, 30));
        nameTextField.setMinimumSize(new Dimension(150, 30));
        nameTextField.setName("nameTextField");
        entryFormPanel.add(nameTextField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        JLabel surnameLabel = new JLabel("SURNAME");
        gridBagConstraints.insets = new Insets(10,10,0,0);
        entryFormPanel.add(surnameLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new Insets(10,5,0,0);
        JTextField surnameTextField = new JTextField();
        surnameTextField.setPreferredSize(new Dimension(150, 30));
        surnameTextField.setMinimumSize(new Dimension(150, 30));
        surnameTextField.setName("surnameTextField");
        entryFormPanel.add(surnameTextField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        JLabel phoneLabel = new JLabel("PHONE");
        gridBagConstraints.insets = new Insets(10,10,0,0);
        entryFormPanel.add(phoneLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new Insets(10,5,0,0);
        JTextField phoneTextField = new JTextField();
        phoneTextField.setPreferredSize(new Dimension(150, 30));
        phoneTextField.setMinimumSize(new Dimension(150, 30));
        phoneTextField.setName("phoneTextField");
        entryFormPanel.add(phoneTextField, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.insets = new Insets(10,0,10,0);
        JButton sendDataButton = new JButton("SEND DATA");
        sendDataButton.setName("sendDataButton");
        entryFormPanel.add(sendDataButton, gridBagConstraints);
        JButton clearDataButton = new JButton("CLEAR");
        clearDataButton.setName("clearDataButton");
        gridBagConstraints.gridy = 4;
        entryFormPanel.add(clearDataButton, gridBagConstraints);
        entryFormPanel.setName("entryFormPanel");
        entryFormPanel.setBorder(titledBorder);
        return entryFormPanel;
    }

    /**
     * Function to create and add the entities list panel
     * @return JPanel with the components already created and organized
     */
    private JPanel addElementsListPanel() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder("ELEMENTS LIST");
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        titledBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        JPanel elementsListPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 10,10,0);
        JTable elementsListTable = new JTable();
        elementsListTable.setFillsViewportHeight(true);
        elementsListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        elementsListTable.setName("elementsListTable");
        elementsListPanel.add(new JScrollPane(elementsListTable), gridBagConstraints);

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        JButton refreshButton = new JButton("REFRESH");
        refreshButton.setName("refreshButton");
        JButton deleteButton = new JButton("DELETE");
        deleteButton.setName("deleteButton");

        gridBagConstraints.insets = new Insets(0, 10,10,10);
        buttonsPanel.add(refreshButton, gridBagConstraints);

        gridBagConstraints.gridy = 1;
        buttonsPanel.add(deleteButton, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 10,0,10);
        elementsListPanel.add(buttonsPanel);
        elementsListPanel.setName("elementsListPanel");
        elementsListPanel.setBorder(titledBorder);
        return elementsListPanel;
    }

    /**
     * Retrieve the controller attached to the Main Form component
     * @return the controller attached
     */
    public MainFormController getMainFormController() {
        return mainFormController;
    }

    /**
     * Set the controller the Main Form component
     * @param mainFormController the controller to be attached
     */
    public void setMainFormController(MainFormController mainFormController) {
        this.mainFormController = mainFormController;
    }
}
