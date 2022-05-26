package com.app_architecture;

import java.util.List;

/**
 * Interface to specify the functions to be set to the AppArchitectureBuilder class
 */
interface IAppArchitectureBuilder {

    AppArchitectureBuilder enableDatabaseSubsystem(List<Object> selectedModuleStructure);
    AppArchitectureBuilder enableUserInterfaceSubsystem(List<Object> selectedModuleStructure);
    AppArchitectureBuilder newBuilder();
    AppArchitecture build();

}
