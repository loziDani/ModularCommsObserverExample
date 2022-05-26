package com.ui_subsystem;

import com.ui_subsystem.ui_structure.UserInterfaceComponents;
import com.ui_subsystem.ui_structure.ui_components.ScreenResolution;
import com.ui_subsystem.ui_structure.ui_components.main_coms_form.MainForm;
import com.ui_subsystem.ui_structure.ui_components.main_coms_form.controllers.MainFormController;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder class to create and instantiate the User Interface Connector to establish the connection with the Application architecture
 */
public class UserInterfaceConnectorBuilder implements IUserInterfaceConnectorBuilder {

    UserInterfaceConnector userInterfaceConnector;

    public UserInterfaceConnectorBuilder(){
    }

    /**
     * Set the graphical components sizes according to the screen resolution to be applied
     * @param currentScreenResolution the screen resolution to be applied
     * @return an instance of the builder with the resolution already set
     */
    @Override
    public UserInterfaceConnectorBuilder applyDeviceScreenResolution(Dimension currentScreenResolution) {
        if (this.userInterfaceConnector == null) this.userInterfaceConnector = new UserInterfaceConnector();
        if (currentScreenResolution == null) {
            //In case of multi-monitor environment, use lines below to get the screen resolution of the default screen
            //GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            //currentScreenResolution = new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());

            //In case of single monitor environment, use the line below
            currentScreenResolution = Toolkit.getDefaultToolkit().getScreenSize();

        }
        this.userInterfaceConnector.screenResolution = new ScreenResolution(currentScreenResolution);
        return this;
    }

    /**
     * Function to build the user interface connector according to the components specified by the user
     *
     * @param userInterfaceComponents the list of graphical components to be added to the connector
     * @return an instance of the builder with the components already defined
     */
    @Override
    public UserInterfaceConnectorBuilder buildModuleStructures(List<UserInterfaceComponents> userInterfaceComponents) {
        if (this.userInterfaceConnector == null) this.userInterfaceConnector = new UserInterfaceConnector();
        if (userInterfaceComponents != null && !userInterfaceComponents.isEmpty()) {
            if (this.userInterfaceConnector.userInterfaceStructures == null)
                this.userInterfaceConnector.userInterfaceStructures = new ArrayList<>();
            for (UserInterfaceComponents userInterfaceComponent : userInterfaceComponents)
                switch (userInterfaceComponent){
                    case MAIN_FORM:
                        MainForm mainForm = new MainForm();
                        MainFormController mainFormController = new MainFormController(mainForm.getMainInteractiveAppPanel());
                        mainFormController.addPropertyChangeListener(this.userInterfaceConnector);
                        mainForm.setMainFormController(mainFormController);
                        this.userInterfaceConnector.userInterfaceStructures.add(mainForm);
                    default: break;
                }
        }
        return this;
    }

    /**
     * Function to create a new User Interface Connector Builder instance
     *
     * @return a new user interface connector builder instance
     */
    @Override
    public UserInterfaceConnectorBuilder newBuilder() {
        return new UserInterfaceConnectorBuilder();
    }

    /**
     * Function to return an instance of the User Interface Connector class with all its properties already initialized
     *
     * @return the User Interface Connector instance already built with the properties specified in the builder call
     */
    @Override
    public UserInterfaceConnector build() {
        return this.userInterfaceConnector;
    }
}
