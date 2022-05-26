package com.ui_subsystem.ui_structure.ui_components;

import java.awt.*;

public class ScreenResolution implements IScreenResolution {

    final Dimension currentScreenResolution;

    public ScreenResolution(Dimension screenResolution){
        this.currentScreenResolution = screenResolution;
    }
}
