package com;

import com.dbs_subsystem.database_structure.DatabaseType;
import com.ui_subsystem.ui_structure.UserInterfaceComponents;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to specify the modules to be integrated in each one of the subsystems that build the Application Architecture.
 * As the Application Architecture follows a strongly defined modules hierarchy, if structure A is defined, and its
 * parent structure is not defined, then the structure A will not be considered
 */
public class C4ISTARArchitectureNeeds {

    /**
     * Global variables to store the attributes selected for the Database Subsystem
     */
    private final List<DatabaseType> databaseStructuresList = new ArrayList<>();

    /**
     * Global variables to store the attributes selected for the User Interface Subsystem
     */
    private final List<UserInterfaceComponents> userInterfaceStructuresList = new ArrayList<>();

    /**
     * Constructor to define which structures of the different modules and subsystems will be included in the Application
     * Architecture
     */
    public C4ISTARArchitectureNeeds() {

        //Reference about how to read, update and save values from a properties' configuration file:
        // https://commons.apache.org/proper/commons-configuration/userguide/quick_start.html
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName("src/main/java/resources/modules_definition.properties"));
        try {
            Configuration config = builder.getConfiguration();

            //Database Subsystem
            setDatabaseSubsystemAttributes(config);

            //User Interface Subsystem
            setUserInterfaceSubsystemAttributes(config);

        } catch (ConfigurationException cex) {
            // loading of the configuration file failed
        }
    }

    /**
     * Function to set the Database Subsystem attributes that will be included in the Application Architecture.
     * Comment the lines which attributes do not need to be integrated.
     */
    private void setDatabaseSubsystemAttributes(Configuration config) {
        //Database structure. Retrieve the information from the configuration file
        String sqliteProperty = (String) config.getProperty("module_database_structure_SQLITE");
        String mySqlProperty = (String) config.getProperty("module_database_structure_MySQL");
        String mongoDBProperty = (String) config.getProperty("module_database_structure_MongoDB");

        if (Boolean.parseBoolean(sqliteProperty))
            this.databaseStructuresList.add(DatabaseType.SQLITE);
        if (Boolean.parseBoolean(mySqlProperty))
            this.databaseStructuresList.add(DatabaseType.MYSQL);
        if (Boolean.parseBoolean(mongoDBProperty))
            this.databaseStructuresList.add(DatabaseType.MONGODB);
    }

    /**
     * Function to set the User Interface Subsystem attributes that will be included in the Application Architecture.
     * Comment the lines which attributes do not need to be integrated.
     */
    private void setUserInterfaceSubsystemAttributes(Configuration config) {
        String mainFormProperty = (String) config.getProperty("module_userInterface");

        if (Boolean.parseBoolean(mainFormProperty))
            this.userInterfaceStructuresList.add(UserInterfaceComponents.MAIN_FORM);
    }

    /**
     * Function to retrieve the set of Database Subsystem properties to be included in the Application Architecture
     *
     * @return List with the Database Subsystem properties set if the developer has set properties to add. Null otherwise
     */
    public List<Object> getDatabaseSubsystemProperties() {
        if (this.databaseStructuresList.size() == 0) return null;
        List<Object> databaseSubsystemProperties = new ArrayList<>();
        databaseSubsystemProperties.add(this.databaseStructuresList);
        return databaseSubsystemProperties;
    }

    /**
     * Function to retrieve the set of User Interface Subsystem properties to be included in the Application Architecture
     *
     * @return List with the User Interface Subsystem properties set if the developer has set properties to add. Null otherwise
     */
    public List<Object> getUserInterfaceStructuresList() {
        if (this.userInterfaceStructuresList.size() == 0) return null;
        List<Object> userInterfaceSubsystemProperties = new ArrayList<>();
        userInterfaceSubsystemProperties.add(this.userInterfaceStructuresList);
        return userInterfaceSubsystemProperties;
    }
}
