package com.ui_subsystem.ui_structure.ui_components.main_coms_form.controllers;

import com.app_architecture.AppArchitecture;
import com.C4ISTARLauncher;
import com.ui_subsystem.UserInterfaceConnector;
import com.ui_subsystem.ui_structure.IUserInterfaceStructure;
import com.ui_subsystem.ui_structure.ui_components.main_coms_form.MainForm;
import com.ui_subsystem.ui_structure.ui_components.main_coms_form.models.ElementsListTableModel;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

/**
 * Controller class with the START and STOP buttons behavior
 */
public class StartActionController implements ActionListener {

    /**
     * Instances of the radio buttons panels of the communications structures and formats
     */
    JPanel tcpipRadioButtonsPanel;
    JPanel datalink16RadioButtonsPanel;
    JPanel uhfRadioButtonsPanel;
    JPanel xmlRadioButtonsPanel;
    JPanel jsonRadioButtonsPanel;

    /**
     * Instances of the radio buttons panels of the database structures
     */
    JPanel sqliteRadioButtonsPanel;
    JPanel mySqlRadioButtonsPanel;
    JPanel mongoDBRadioButtonsPanel;

    /**
     * Constructor
     *
     * @param selectionRadioButtonPanels list with the selection panels available in the UI
     */
    public StartActionController(List<JPanel> selectionRadioButtonPanels) {
        if (selectionRadioButtonPanels != null && !selectionRadioButtonPanels.isEmpty())
            for (JPanel jPanel : selectionRadioButtonPanels) {
                if (jPanel.getName().equals("commsSelectionPanel"))
                    retrieveCommsSelectionPanels(jPanel);
                else if (jPanel.getName().equals("databaseStructuresPanel"))
                    retrieveDatabaseStructureSelectionPanels(jPanel);
            }
    }

    /**
     * Function to set value of the properties in the configuration file according to the selection made in the UI
     * radio buttons
     */
    private void updatePropertiesFileWithRadioButtonValues() {
        //Reference about how to read, update and save values from a properties' configuration file:
        // https://commons.apache.org/proper/commons-configuration/userguide/quick_start.html
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName("src/main/java/resources/modules_definition.properties"));
        try {
            Configuration config = builder.getConfiguration();
            setCommunicationsTypesAndFormatsEnabledByUser(config);
            setDatabaseModuleStructureEnabledByUser(config);
            builder.save();
        } catch (ConfigurationException cex) {
            // loading of the configuration file failed
        }
    }

    /**
     * Function to set the Communications properties to the configuration file according to the selection made in the UI radio buttons
     *
     * @param config The configuration object in charge of perform the changes needed in the configuration properties file
     */
    private void setCommunicationsTypesAndFormatsEnabledByUser(Configuration config) {
        setTcpipProperty(config);
        setDatalink16Property(config);
        setUHFProperty(config);
        setXMLProperty(config);
        setJsonProperty(config);

    }

    /**
     * Function to set the TCP/IP property to the configuration file according to the selection made in the UI radio buttons
     *
     * @param config The configuration object in charge of perform the changes needed in the configuration properties file
     */
    private void setTcpipProperty(Configuration config) {
        if (this.tcpipRadioButtonsPanel != null && this.tcpipRadioButtonsPanel.getComponentCount() > 0)
            for (Component component : this.tcpipRadioButtonsPanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel radioButtonsJPanel = (JPanel) component;
                    if (radioButtonsJPanel.getComponentCount() > 0)
                        for (Component component1 : radioButtonsJPanel.getComponents())
                            if (component1 instanceof JRadioButton) {
                                JRadioButton tcpipSelectionRadioButton = (JRadioButton) component1;
                                if (tcpipSelectionRadioButton.getActionCommand().equals("YES")) {
                                    if (tcpipSelectionRadioButton.isSelected())
                                        config.setProperty("module_communications_type_TCPIP", "true");
                                    else config.setProperty("module_communications_type_TCPIP", "false");
                                    break;
                                }
                            }
                }
            }
    }

    /**
     * Function to set the DataLink16 property to the configuration file according to the selection made in the UI radio buttons
     *
     * @param config The configuration object in charge of perform the changes needed in the configuration properties file
     */
    private void setDatalink16Property(Configuration config) {
        if (this.datalink16RadioButtonsPanel != null && this.datalink16RadioButtonsPanel.getComponentCount() > 0)
            for (Component component : this.datalink16RadioButtonsPanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel radioButtonsJPanel = (JPanel) component;
                    if (radioButtonsJPanel.getComponentCount() > 0)
                        for (Component component1 : radioButtonsJPanel.getComponents())
                            if (component1 instanceof JRadioButton) {
                                JRadioButton datalink16SelectionRadioButton = (JRadioButton) component1;
                                if (datalink16SelectionRadioButton.getActionCommand().equals("YES"))
                                    if (datalink16SelectionRadioButton.isSelected())
                                        config.setProperty("module_communications_type_Datalink16", "true");
                                    else config.setProperty("module_communications_type_Datalink16", "false");
                            }
                }
            }
    }

    /**
     * Function to set the UHF property to the configuration file according to the selection made in the UI radio buttons
     *
     * @param config The configuration object in charge of perform the changes needed in the configuration properties file
     */
    private void setUHFProperty(Configuration config) {
        if (this.uhfRadioButtonsPanel != null && this.uhfRadioButtonsPanel.getComponentCount() > 0)
            for (Component component : this.uhfRadioButtonsPanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel radioButtonsJPanel = (JPanel) component;
                    if (radioButtonsJPanel.getComponentCount() > 0)
                        for (Component component1 : radioButtonsJPanel.getComponents())
                            if (component1 instanceof JRadioButton) {
                                JRadioButton uhfSelectionRadioButton = (JRadioButton) component1;
                                if (uhfSelectionRadioButton.getActionCommand().equals("YES"))
                                    if (uhfSelectionRadioButton.isSelected())
                                        config.setProperty("module_communications_type_UHF", "true");
                                    else config.setProperty("module_communications_type_UHF", "false");
                            }
                }
            }
    }

    /**
     * Function to set the XML property to the configuration file according to the selection made in the UI radio buttons
     *
     * @param config The configuration object in charge of perform the changes needed in the configuration properties file
     */
    private void setXMLProperty(Configuration config) {
        if (this.xmlRadioButtonsPanel != null && this.xmlRadioButtonsPanel.getComponentCount() > 0)
            for (Component component : this.xmlRadioButtonsPanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel radioButtonsJPanel = (JPanel) component;
                    if (radioButtonsJPanel.getComponentCount() > 0)
                        for (Component component1 : radioButtonsJPanel.getComponents())
                            if (component1 instanceof JRadioButton) {
                                JRadioButton xmlSelectionRadioButton = (JRadioButton) component1;
                                if (xmlSelectionRadioButton.getActionCommand().equals("YES"))
                                    if (xmlSelectionRadioButton.isSelected())
                                        config.setProperty("module_communications_format_XML", "true");
                                    else config.setProperty("module_communications_format_XML", "false");
                            }
                }
            }
    }

    /**
     * Function to set the JSON property to the configuration file according to the selection made in the UI radio buttons
     *
     * @param config The configuration object in charge of perform the changes needed in the configuration properties file
     */
    private void setJsonProperty(Configuration config) {
        if (this.jsonRadioButtonsPanel != null && this.jsonRadioButtonsPanel.getComponentCount() > 0)
            for (Component component : this.jsonRadioButtonsPanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel radioButtonsJPanel = (JPanel) component;
                    if (radioButtonsJPanel.getComponentCount() > 0)
                        for (Component component1 : radioButtonsJPanel.getComponents())
                            if (component1 instanceof JRadioButton) {
                                JRadioButton jsonSelectionRadioButton = (JRadioButton) component1;
                                if (jsonSelectionRadioButton.getActionCommand().equals("YES"))
                                    if (jsonSelectionRadioButton.isSelected())
                                        config.setProperty("module_communications_format_JSON", "true");
                                    else config.setProperty("module_communications_format_JSON", "false");
                            }
                }
            }
    }

    /**
     * Function to set the Database properties to the configuration file according to the selection made in the UI radio buttons
     *
     * @param config The configuration object in charge of perform the changes needed in the configuration properties file
     */
    private void setDatabaseModuleStructureEnabledByUser(Configuration config) {
        setSqliteProperty(config);
        setMySqlProperty(config);
        setMongoDBProperty(config);
    }

    /**
     * Function to set the SQLite property to the configuration file according to the selection made in the UI radio buttons
     *
     * @param config The configuration object in charge of perform the changes needed in the configuration properties file
     */
    private void setSqliteProperty(Configuration config) {
        if (this.sqliteRadioButtonsPanel != null && this.sqliteRadioButtonsPanel.getComponentCount() > 0)
            for (Component component : this.sqliteRadioButtonsPanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel radioButtonsJPanel = (JPanel) component;
                    if (radioButtonsJPanel.getComponentCount() > 0)
                        for (Component component1 : radioButtonsJPanel.getComponents())
                            if (component1 instanceof JRadioButton) {
                                JRadioButton sqliteSelectionRadioButton = (JRadioButton) component1;
                                if (sqliteSelectionRadioButton.getActionCommand().equals("YES"))
                                    if (sqliteSelectionRadioButton.isSelected())
                                        config.setProperty("module_database_structure_SQLITE", "true");
                                    else config.setProperty("module_database_structure_SQLITE", "false");
                            }
                }
            }
    }

    /**
     * Function to set the MySQL property to the configuration file according to the selection made in the UI radio buttons
     *
     * @param config The configuration object in charge of perform the changes needed in the configuration properties file
     */
    private void setMySqlProperty(Configuration config) {
        if (this.mySqlRadioButtonsPanel != null && this.mySqlRadioButtonsPanel.getComponentCount() > 0)
            for (Component component : this.mySqlRadioButtonsPanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel radioButtonsJPanel = (JPanel) component;
                    if (radioButtonsJPanel.getComponentCount() > 0)
                        for (Component component1 : radioButtonsJPanel.getComponents())
                            if (component1 instanceof JRadioButton) {
                                JRadioButton mySqlSelectionRadioButton = (JRadioButton) component1;
                                if (mySqlSelectionRadioButton.getActionCommand().equals("YES"))
                                    if (mySqlSelectionRadioButton.isSelected())
                                        config.setProperty("module_database_structure_MySQL", "true");
                                    else config.setProperty("module_database_structure_MySQL", "false");
                            }
                }
            }
    }

    /**
     * Function to set the MongoDB property to the configuration file according to the selection made in the UI radio buttons
     *
     * @param config The configuration object in charge of perform the changes needed in the configuration properties file
     */
    private void setMongoDBProperty(Configuration config) {
        if (this.mongoDBRadioButtonsPanel != null && this.mongoDBRadioButtonsPanel.getComponentCount() > 0)
            for (Component component : this.mongoDBRadioButtonsPanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel radioButtonsJPanel = (JPanel) component;
                    if (radioButtonsJPanel.getComponentCount() > 0)
                        for (Component component1 : radioButtonsJPanel.getComponents())
                            if (component1 instanceof JRadioButton) {
                                JRadioButton mongoDBSelectionRadioButton = (JRadioButton) component1;
                                if (mongoDBSelectionRadioButton.getActionCommand().equals("YES"))
                                    if (mongoDBSelectionRadioButton.isSelected())
                                        config.setProperty("module_database_structure_MongoDB", "true");
                                    else config.setProperty("module_database_structure_MongoDB", "false");
                            }
                }
            }
    }

    /**
     * Function to retrieve the JPanels containing the Communications types and formats subpanels
     *
     * @param commsSelectionPanel the panel from where the subpanels will be retrieved
     */
    private void retrieveCommsSelectionPanels(JPanel commsSelectionPanel) {
        if (commsSelectionPanel.getComponentCount() > 0)
            for (Component jPanelComponent : commsSelectionPanel.getComponents()) {
                if (jPanelComponent.getName().equals("commsTypeSelectionPanel"))
                    retrieveCommsStructureSelectionPanels((JPanel) jPanelComponent);
                else if (jPanelComponent.getName().equals("commsFormatSelectionPanel"))
                    retrieveCommsFormatSelectionPanels((JPanel) jPanelComponent);
            }
    }

    /**
     * Function to retrieve the JPanels containing the Database structures radio buttons
     *
     * @param databaseStructurePanel the panel from where the radio buttons will be retrieved
     */
    private void retrieveDatabaseStructureSelectionPanels(JPanel databaseStructurePanel) {
        if (databaseStructurePanel.getComponentCount() > 0)
            for (Component jPanelComponent : databaseStructurePanel.getComponents()) {
                switch (jPanelComponent.getName()) {
                    case "sqliteSelectionPanel":
                        this.sqliteRadioButtonsPanel = (JPanel) jPanelComponent;
                        break;
                    case "mySqlSelectionPanel":
                        this.mySqlRadioButtonsPanel = (JPanel) jPanelComponent;
                        break;
                    case "mongoDbSelectionPanel":
                        this.mongoDBRadioButtonsPanel = (JPanel) jPanelComponent;
                        break;
                    default:
                        break;
                }
            }
    }

    /**
     * Function to retrieve the JPanels containing the Communications structures radio buttons
     *
     * @param commsStructuresSelectionPanels the panel from where the radio buttons will be retrieved
     */
    private void retrieveCommsStructureSelectionPanels(JPanel commsStructuresSelectionPanels) {
        if (commsStructuresSelectionPanels != null)
            if (commsStructuresSelectionPanels.getComponentCount() > 0)
                for (Component jPanelComponent : commsStructuresSelectionPanels.getComponents())
                    if (jPanelComponent instanceof JPanel) {
                        JPanel commsStructureTypeJPanel = (JPanel) jPanelComponent;
                        if (commsStructureTypeJPanel.getComponentCount() > 0)
                            for (Component commsStructureJPanel : commsStructureTypeJPanel.getComponents()) {
                                JPanel commsStructureTypeJPanel1 = (JPanel) commsStructureJPanel;
                                switch (commsStructureTypeJPanel1.getName()) {
                                    case "tcpipSelectionPanel":
                                        this.tcpipRadioButtonsPanel = commsStructureTypeJPanel1;
                                        break;
                                    case "datalink16SelectionPanel":
                                        this.datalink16RadioButtonsPanel = commsStructureTypeJPanel1;
                                        break;
                                    case "uhfSelectionPanel":
                                        this.uhfRadioButtonsPanel = commsStructureTypeJPanel1;
                                        break;
                                    default:
                                        break;

                                }
                            }
                    }
    }

    /**
     * Function to retrieve the JPanels containing the Communications formats radio buttons
     *
     * @param commsFormatSelectionJPanels the panel from where the radio buttons will be retrieved
     */
    private void retrieveCommsFormatSelectionPanels(JPanel commsFormatSelectionJPanels) {
        if (commsFormatSelectionJPanels != null)
            if (commsFormatSelectionJPanels.getComponentCount() > 0)
                for (Component jPanelComponent : commsFormatSelectionJPanels.getComponents())
                    if (jPanelComponent instanceof JPanel) {
                        JPanel commsStructureFormatJPanel = (JPanel) jPanelComponent;
                        if (commsStructureFormatJPanel.getComponentCount() > 0)
                            for (Component commsFormatJPanel : commsStructureFormatJPanel.getComponents()) {
                                JPanel commsStructureFormatJPanel1 = (JPanel) commsFormatJPanel;
                                switch (commsStructureFormatJPanel1.getName()) {
                                    case "xmlSelectionPanel":
                                        this.xmlRadioButtonsPanel = commsStructureFormatJPanel1;
                                        break;
                                    case "jsonSelectionPanel":
                                        this.jsonRadioButtonsPanel = commsStructureFormatJPanel1;
                                        break;
                                    default:
                                        break;
                                }
                            }
                    }
    }

    /**
     * Event thrown when the user clicks the start button to create the App architecture according to the selection
     * made in the UI radio buttons
     *
     * @param e the event thrown
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        closeSetupFrame((Component) e.getSource());
        updatePropertiesFileWithRadioButtonValues();
        C4ISTARLauncher c4ISTARLauncher = new C4ISTARLauncher();
        if (c4ISTARLauncher.getAppArchitecture() == null) {
            String messageDialog = "There is not architecture selected to build. Please choose" +
                    " at least one of the structures provided";
            JOptionPane.showMessageDialog(null, messageDialog, "WARNING",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JButton buttonPressed = (JButton) e.getSource();
            buttonPressed.setBackground(Color.GREEN);
            List<IUserInterfaceStructure> userInterfaceStructureList = c4ISTARLauncher.getAppArchitecture().getUserInterfaceConnector().getUserInterfaceStructures();
            JPanel interactiveAppPanel = new JPanel();
            for (IUserInterfaceStructure userInterfaceStructure : userInterfaceStructureList)
                if (userInterfaceStructure instanceof MainForm) {
                    interactiveAppPanel = ((MainForm) userInterfaceStructure).getMainInteractiveAppPanel();
                    break;
                }
            setUIControllers(c4ISTARLauncher.getAppArchitecture(), interactiveAppPanel);
            loadSystemData(c4ISTARLauncher.getAppArchitecture().getUserInterfaceConnector());
            //loadDatabaseDataToElementsListTable(interactiveAppPanel, c4ISTARLauncher.getAppArchitecture().getDatabaseConnector().getDatabaseStructureList());
        }
    }

    /**
     * Function to call the data loading instruction from the User Interface Subsystem to the Observer subscribed to it (the app architecture core in this case)
     * @param userInterfaceConnector the User Interface connector with the user interface components where the data will be loaded
     */
    private void loadSystemData(UserInterfaceConnector userInterfaceConnector) {
        for (IUserInterfaceStructure userInterfaceStructure : userInterfaceConnector.getUserInterfaceStructures())
            if (userInterfaceStructure instanceof MainForm)
                ((MainForm) userInterfaceStructure).getMainFormController().readDataFromDB();
    }

    private void closeSetupFrame(Component component) {
        if (component instanceof JFrame)
            component.setVisible(false);
        else closeSetupFrame(component.getParent());
    }

    /**
     * Function to set the corresponding controllers to the UI components after the user has specified
     * which structures wants to use when START button is clicked
     *
     * @param appArchitecture       the App Architecture selected through the corresponding radio buttons
     * @param interactiveAppPanel   panel with the components where the controllers will be set
     */
    private void setUIControllers(AppArchitecture appArchitecture, JPanel interactiveAppPanel) {
        HashMap<String, JTextField> ipAddressPanelTextFields = new HashMap<>();
        for (Component interactiveAppPanelCmp : interactiveAppPanel.getComponents())
            if (interactiveAppPanelCmp instanceof JPanel)
                if (interactiveAppPanelCmp.getName().equals("ipAddressPanel")) {
                    ipAddressPanelTextFields = getIpAndPortTextFields((JPanel) interactiveAppPanelCmp);
                } else if (interactiveAppPanelCmp.getName().equals("formAndListPanel")) {
                    JPanel formAndListPanel = (JPanel) interactiveAppPanelCmp;
                    for (Component formAndListPanelCmp : formAndListPanel.getComponents()) {
                        if (formAndListPanelCmp instanceof JPanel) {
                            if (formAndListPanelCmp.getName().equals("entryFormPanel")) {
                                setEntryFormPanelControllers((JPanel) formAndListPanelCmp,
                                        appArchitecture,
                                        ipAddressPanelTextFields);
                            } else if (formAndListPanelCmp.getName().equals("elementsListPanel"))
                                setElementsListPanelControllers((JPanel) formAndListPanelCmp,
                                        appArchitecture);
                        }
                    }
                }

    }

    /**
     * Function to set the controllers to the Entry Form UI components needed
     *
     * @param entryFormPanel           the Entry Form panel here all the UI components are placed
     * @param appArchitecture          the app architecture
     * @param ipAddressPanelTextFields the set of JTextFields where the IP and port information is placed
     */
    private void setEntryFormPanelControllers(JPanel entryFormPanel, AppArchitecture appArchitecture,
                                              HashMap<String, JTextField> ipAddressPanelTextFields) {
        HashMap<String, JTextField> entryFormPanelTextBoxes = new HashMap<>();
        JButton sendDataButton = new JButton();
        JButton clearDataButton = new JButton();

        for (Component entryFormPanelCmp : entryFormPanel.getComponents()) {
            if (entryFormPanelCmp instanceof JButton) {
                if (entryFormPanelCmp.getName().equals("sendDataButton"))
                    sendDataButton = (JButton) entryFormPanelCmp;
                else if (entryFormPanelCmp.getName().equals("clearDataButton"))
                    clearDataButton = (JButton) entryFormPanelCmp;
            } else if (entryFormPanelCmp instanceof JTextField) {
                entryFormPanelTextBoxes.put(entryFormPanelCmp.getName(), (JTextField) entryFormPanelCmp);
            }
        }
        DDBBInfoExchangeController ddbbInfoExchangeController = new DDBBInfoExchangeController(
                ipAddressPanelTextFields.get("IPTextField"), ipAddressPanelTextFields.get("portTextField"),
                entryFormPanelTextBoxes.get("nameTextField"), entryFormPanelTextBoxes.get("surnameTextField"),
                entryFormPanelTextBoxes.get("phoneTextField"));
        for (IUserInterfaceStructure userInterfaceStructure : appArchitecture.getUserInterfaceConnector().getUserInterfaceStructures())
            if (userInterfaceStructure instanceof MainForm)
                ddbbInfoExchangeController.addPropertyChangeListener(((MainForm) userInterfaceStructure).getMainFormController());
        sendDataButton.addActionListener(ddbbInfoExchangeController);
        clearDataButton.addActionListener(new ClearTextFieldsDataController(ipAddressPanelTextFields.get("IPTextField"), ipAddressPanelTextFields.get("portTextField"),
                entryFormPanelTextBoxes.get("nameTextField"), entryFormPanelTextBoxes.get("surnameTextField"),
                entryFormPanelTextBoxes.get("phoneTextField")));
    }

    /**
     * Function to set the controllers to the Elements List panel UI components needed
     *
     * @param elementsListPanel the Elements List panel where the UI components are placed
     * @param appArchitecture   the app architecture
     */
    private void setElementsListPanelControllers(JPanel elementsListPanel, AppArchitecture appArchitecture) {
        JTable elementsListTable = new JTable();
        HashMap<String, JTextField> formTextFields = getFormTextFields(elementsListPanel);
        for (Component formAndListPanelCmp : elementsListPanel.getComponents()) {
            if (formAndListPanelCmp instanceof JScrollPane) {
                JScrollPane elementsListScrollPane = (JScrollPane) formAndListPanelCmp;
                JViewport viewport = elementsListScrollPane.getViewport();
                elementsListTable = (JTable) viewport.getView();
                elementsListTable.setModel(new ElementsListTableModel());
                elementsListTable.getSelectionModel().addListSelectionListener(
                        new ElementsListTableSelectionListener(elementsListTable, formTextFields));
            } else if (formAndListPanelCmp instanceof JPanel) {
                JPanel elementsListButtonsPanel = (JPanel) formAndListPanelCmp;
                for (Component elementsListButton : elementsListButtonsPanel.getComponents()) {
                    if (elementsListButton instanceof JButton) {
                        DDBBInfoExchangeController ddbbInfoExchangeController = new DDBBInfoExchangeController(
                                elementsListTable, (JButton) elementsListButton, formTextFields);
                        for (IUserInterfaceStructure userInterfaceStructure : appArchitecture.getUserInterfaceConnector().getUserInterfaceStructures())
                            if (userInterfaceStructure instanceof MainForm)
                                ddbbInfoExchangeController.addPropertyChangeListener(((MainForm) userInterfaceStructure).getMainFormController());
                        ((JButton) elementsListButton).addActionListener(ddbbInfoExchangeController);
                    }
                }

            }
        }
    }

    /**
     * Function to get the UI JTextFields so they can be added to the corresponding controller needed
     *
     * @param elementsListPanel the Elements List panel from where the function will start to search the components
     * @return a HashMap with the JTextFields of the User Interface
     */
    private HashMap<String, JTextField> getFormTextFields(JPanel elementsListPanel) {
        HashMap<String, JTextField> formTextFields = new HashMap<>();
        for (Component formAndListPanelCmp : elementsListPanel.getParent().getComponents()) {
            if (formAndListPanelCmp instanceof JPanel)
                if (formAndListPanelCmp.getName().equals("entryFormPanel")) {
                    JPanel entryFormPanel = (JPanel) formAndListPanelCmp;
                    for (Component entryFormPanelCmp : entryFormPanel.getComponents())
                        if (entryFormPanelCmp instanceof JTextField)
                            formTextFields.put(entryFormPanelCmp.getName(), (JTextField) entryFormPanelCmp);
                }
        }

        for (Component interactiveAppPanelCmp : elementsListPanel.getParent().getParent().getComponents())
            if (interactiveAppPanelCmp instanceof JPanel)
                if (interactiveAppPanelCmp.getName().equals("ipAddressPanel")) {
                    JPanel ipAddressPanel = (JPanel) interactiveAppPanelCmp;
                    for (Component ipAddressPanelCmp : ipAddressPanel.getComponents()) {
                        if (ipAddressPanelCmp instanceof JTextField)
                            formTextFields.put(ipAddressPanelCmp.getName(), (JTextField) ipAddressPanelCmp);
                    }
                }
        return formTextFields;
    }

    /**
     * Function to get the JTextFields corresponding to the IP and port information
     *
     * @param ipAddressPanel the IP address panel where the components are placed
     * @return a HashMap with the IP and port JTextFields
     */
    private HashMap<String, JTextField> getIpAndPortTextFields(JPanel ipAddressPanel) {
        HashMap<String, JTextField> ipAndPortTextFields = new HashMap<>();
        for (Component component : ipAddressPanel.getComponents()) {
            if (component instanceof JTextField)
                if (component.getName().equals("IPTextField"))
                    ipAndPortTextFields.put("IPTextField", (JTextField) component);
                else if (component.getName().equals("portTextField"))
                    ipAndPortTextFields.put("portTextField", (JTextField) component);
        }
        return ipAndPortTextFields;
    }
}
