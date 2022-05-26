package com;

import com.app_architecture.AppArchitecture;
import com.app_architecture.AppArchitectureBuilder;
import com.app_architecture.AppArchitectureController;
import com.app_architecture.AppArchitectureSubsystems;
import com.dbs_subsystem.DatabaseConnector;
import com.ui_subsystem.UserInterfaceConnector;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Launcher class to start an instance of the Application Architecture according to the parameters specified in the
 * c4ISATArchitectureNeeds class
 */
public class C4ISTARLauncher {

    AppArchitecture appArchitecture;

    public C4ISTARLauncher(){
        run();
    }

    /**
     * Function to run the instantiation of the Application Architecture instance
     */
    private void run() {

        //Get a HashMap with the information retrieved from the modules' definition properties file
        HashMap<String, Boolean> subsystems_definition = getSubsystemsArchitecture();

        //Applying the Builder Design Pattern
        AppArchitectureBuilder appArchitectureBuilder = new AppArchitectureBuilder();

        //Instance with all the needs to be compiled to define the Application Architecture
        C4ISTARArchitectureNeeds c4ISTARArchitectureNeeds = new C4ISTARArchitectureNeeds();

        //Set the HashMap to be used to add to the Application Architecture the specified modules
        HashMap<AppArchitectureSubsystems, List<Object>> connectorsStructuresForAppArchitecture = null;
        if (subsystems_definition != null) {
            connectorsStructuresForAppArchitecture = createConnectorsStructures(c4ISTARArchitectureNeeds,
                    subsystems_definition.get("module_database"),
                    subsystems_definition.get("module_userInterface"));
        }

        //Check if there are connectors to be added to the Application Architecture
        if (connectorsStructuresForAppArchitecture != null) {

            //For each connector enabled, manage the structures to be added to it
            for (AppArchitectureSubsystems key : connectorsStructuresForAppArchitecture.keySet()) {
                switch (key) {
                    case DATABASE_SUBSYSTEM:
                        List<Object> databaseSubsystemPropertiesLists = connectorsStructuresForAppArchitecture.get(key);
                        appArchitectureBuilder.enableDatabaseSubsystem(databaseSubsystemPropertiesLists);
                        break;
                    case USER_INTERFACE_SUBSYSTEM:
                        List<Object> userInterfaceSubsystemPropertiesLists = connectorsStructuresForAppArchitecture.get(key);
                        appArchitectureBuilder.enableUserInterfaceSubsystem(userInterfaceSubsystemPropertiesLists);
                        break;
                    default:
                        break;
                }
            }

            //Build the Application Architecture using the Builder Pattern
            this.appArchitecture = appArchitectureBuilder.build();
            AppArchitectureController appArchitectureController = new AppArchitectureController(this.appArchitecture);
            this.appArchitecture.addAppArchitectureController(appArchitectureController);
            setObservablesForAppArchitecture(this.appArchitecture);
            System.out.println("Application Architecture Built");
        } else
            //If the developer don't select any subsystem to be added to the Application Architecture, the following message is displayed
            System.out.println("There are no modules selected to be added to the Application Architecture");
        System.out.println("Running main from instance");

    }

    /**
     * Function to set the Observables modules to the App Architecture core
     * @param appArchitecture the App Architecture
     */
    private void setObservablesForAppArchitecture(AppArchitecture appArchitecture) {
        for (Object appArchitectureConnector : appArchitecture.getSubsystemConnectorList()){
            if (appArchitectureConnector instanceof DatabaseConnector)
                ((DatabaseConnector) appArchitectureConnector).addPropertyChangeListener(appArchitecture.getAppArchitectureController());
            else if (appArchitectureConnector instanceof UserInterfaceConnector)
                ((UserInterfaceConnector) appArchitectureConnector).addPropertyChangeListener(appArchitecture.getAppArchitectureController());
        }
    }

    /**
     * Function to get the subsystems' architecture to be compiled defined by the developer
     *
     * @return a HashMap with a pair String-Boolean, where the String refers to the subsystem name, and the boolean
     * defines if the subsystem will be included or not
     */
    private HashMap<String, Boolean> getSubsystemsArchitecture() {
        try {
            HashMap<String, Boolean> subsystemArchitectureDefinition = new HashMap<>();
            Properties propertiesFile = readPropertiesFile("src/main/java/resources/modules_definition.properties");
            if ((propertiesFile.getProperty("module_communications") != null) && (propertiesFile.getProperty("module_communications").equals("true")))
                subsystemArchitectureDefinition.put("module_communications", true);
            else subsystemArchitectureDefinition.put("module_communications", false);
            if ((propertiesFile.getProperty("module_dataModel") != null) && (propertiesFile.getProperty("module_dataModel").equals("true")))
                subsystemArchitectureDefinition.put("module_dataModel", true);
            else subsystemArchitectureDefinition.put("module_dataModel", false);
            if ((propertiesFile.getProperty("module_dataProcesses") != null) && (propertiesFile.getProperty("module_dataProcesses").equals("true")))
                subsystemArchitectureDefinition.put("module_dataProcesses", true);
            else subsystemArchitectureDefinition.put("module_dataProcesses", false);
            if ((propertiesFile.getProperty("module_database") != null) && (propertiesFile.getProperty("module_database").equals("true")))
                subsystemArchitectureDefinition.put("module_database", true);
            else subsystemArchitectureDefinition.put("module_database", false);
            if ((propertiesFile.getProperty("module_externalInterface") != null) && (propertiesFile.getProperty("module_externalInterface").equals("true")))
                subsystemArchitectureDefinition.put("module_externalInterface", true);
            else subsystemArchitectureDefinition.put("module_externalInterface", false);
            if ((propertiesFile.getProperty("module_maintenanceAndTest") != null) && (propertiesFile.getProperty("module_maintenanceAndTest").equals("true")))
                subsystemArchitectureDefinition.put("module_maintenanceAndTest", true);
            else subsystemArchitectureDefinition.put("module_maintenanceAndTest", false);
            if ((propertiesFile.getProperty("module_messaging") != null) && (propertiesFile.getProperty("module_messaging").equals("true")))
                subsystemArchitectureDefinition.put("module_messaging", true);
            else subsystemArchitectureDefinition.put("module_messaging", false);
            if ((propertiesFile.getProperty("module_thirdPartyInterface") != null) && (propertiesFile.getProperty("module_thirdPartyInterface").equals("true")))
                subsystemArchitectureDefinition.put("module_thirdPartyInterface", true);
            else subsystemArchitectureDefinition.put("module_thirdPartyInterface", false);
            if ((propertiesFile.getProperty("module_userInterface") != null) && (propertiesFile.getProperty("module_userInterface").equals("true")))
                subsystemArchitectureDefinition.put("module_userInterface", true);
            else subsystemArchitectureDefinition.put("module_userInterface", false);

            return subsystemArchitectureDefinition;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Function to create and add the selected subsystem structures to the Application Architecture
     *
     * @param c4ISTARArchitectureNeeds           set of attributes specified by the developer to be integrated in the Application Architecture solution
     * @param enableDatabaseConnector            enables the Database Connector structure. True if structures for this module will be added. False otherwise
     * @param enableUserInterfaceConnector       enables the User Interface Connector structure. True if structures for this module will be added. False otherwise
     * @return a HashMap with all the structures selected for each module the user enables according to the parameters set to this function
     */
    private HashMap<AppArchitectureSubsystems, List<Object>> createConnectorsStructures(C4ISTARArchitectureNeeds c4ISTARArchitectureNeeds,
                                                                                        boolean enableDatabaseConnector,
                                                                                        boolean enableUserInterfaceConnector) {

        //List of structures to be returned to the Builder Pattern to build the Application Architecture
        HashMap<AppArchitectureSubsystems, List<Object>> connectorsStructures = new HashMap<>();

        if (enableDatabaseConnector) {
            List<Object> databaseSubsystemProperties = c4ISTARArchitectureNeeds.getDatabaseSubsystemProperties();
            if (databaseSubsystemProperties != null)
                connectorsStructures.put(AppArchitectureSubsystems.DATABASE_SUBSYSTEM, databaseSubsystemProperties);
        }

        if (enableUserInterfaceConnector) {

            List<Object> userInterfaceSubsystemProperties = c4ISTARArchitectureNeeds.getUserInterfaceStructuresList();
            if (userInterfaceSubsystemProperties != null)
                connectorsStructures.put(AppArchitectureSubsystems.USER_INTERFACE_SUBSYSTEM, userInterfaceSubsystemProperties);
        }

        //If there are structures available to create the Application Architecture, then return them. Return null otherwise
        if (connectorsStructures.size() > 0)
            return connectorsStructures;
        else
            return null;
    }

    /**
     * Function to retrieve the Properties file to access its information
     *
     * @param fileName the relative path where the system can find the Properties file
     * @return a Properties object to access the properties specified in the file
     * @throws IOException when the Properties file is not found
     */
    public static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch (IOException fnfe) {
            fnfe.printStackTrace();
        } finally {
            if (fis != null)
                fis.close();
        }
        return prop;
    }

    public AppArchitecture getAppArchitecture() {
        return this.appArchitecture;
    }
}
