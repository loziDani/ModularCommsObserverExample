package com.ui_subsystem;

import com.ui_subsystem.ui_structure.UserInterfaceComponents;

import java.awt.*;
import java.util.List;

public interface IUserInterfaceConnectorBuilder {

    UserInterfaceConnectorBuilder applyDeviceScreenResolution(Dimension currentScreenResolution);
    UserInterfaceConnectorBuilder buildModuleStructures(List<UserInterfaceComponents> userInterfaceComponents);
    UserInterfaceConnectorBuilder newBuilder();
    UserInterfaceConnector build();

}
