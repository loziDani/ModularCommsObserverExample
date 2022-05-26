package com;

import com.ui_subsystem.ui_structure.IUserInterfaceStructure;
import com.ui_subsystem.ui_structure.ui_components.main_coms_form.controllers.StartActionController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Main form class with all the user interface creation
 */
public class SetupForm implements IUserInterfaceStructure {

    public static void main(String[] args){ new SetupForm(); }

    /**
     * Constructor in charge of the creation of the UI components
     */
    public SetupForm(){
        JFrame mainFrame = new JFrame("MODULAR DATABASE AND USER INTERFACE OBSERVER PATTERN EXAMPLE SETUP WINDOW");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(true);
        mainFrame.setMinimumSize(new Dimension(700, 500));
        mainFrame.setPreferredSize(new Dimension(700, 500));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.add(addSetupPanel());
        mainFrame.setVisible(true);
    }

    /**
     * Function to create the setup panel
     * @return JPanel with the setup panel already created and organized
     */
    private JPanel addSetupPanel() {
        Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(etchedBorder, "SETUP");
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        titledBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        JPanel setupPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        ArrayList<JPanel> setupPanelsList = new ArrayList<>();
        JPanel databaseStructureSelectionPanel = addDatabaseStructureSelectionPanel();
        setupPanelsList.add(databaseStructureSelectionPanel);

        gridBagConstraints.fill = GridBagConstraints.WEST;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new Insets(10,0,0,0);
        setupPanel.add(databaseStructureSelectionPanel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(10,0,10,0);

        setupPanel.add(addStartStopButtons(setupPanelsList), gridBagConstraints);
        setupPanel.setName("setupPanel");
        setupPanel.setBorder(titledBorder);

        return setupPanel;
    }

    /**
     * Function to create the Database structure selection
     * @return JPanel with all the database structures available to be selected via JRadioButtons
     */
    private JPanel addDatabaseStructureSelectionPanel() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder("DATABASE");
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        titledBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        JPanel databaseStructuresPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(15, 10, 0, 0);
        databaseStructuresPanel.add(addSQLiteDatabaseSelection(), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.insets = new Insets(15, 10, 0, 0);
        databaseStructuresPanel.add(addMySQLDatabaseSelection(), gridBagConstraints);
        gridBagConstraints.gridx = 2;
        gridBagConstraints.insets = new Insets(15, 10, 0, 10);
        databaseStructuresPanel.add(addMongoDatabaseSelection(), gridBagConstraints);
        databaseStructuresPanel.setName("databaseStructuresPanel");
        databaseStructuresPanel.setBorder(titledBorder);
        return databaseStructuresPanel;
    }

    /**
     * Function to add the buttons to manage the creation of the structures selected in the JRadioButtons
     * @return JPanel with the buttons already created and organized
     */
    private JPanel addStartStopButtons(ArrayList<JPanel> setupPanelsList) {
        JPanel startStopButtonsPanel = new JPanel(new GridLayout(0,2));
        JButton startButton = new JButton("START");
        JButton stopButton = new JButton("STOP");

        StartActionController startActionController = new StartActionController(setupPanelsList);
        startButton.addActionListener(startActionController);
        stopButton.addActionListener(startActionController);

        startStopButtonsPanel.add(startButton);
        //startStopButtonsPanel.add(stopButton);
        startStopButtonsPanel.setName("startStopButtonsPanel");
        return startStopButtonsPanel;
    }

    /**
     * Function to create and add the SQLite database structure selection
     * @return JPanel with the SQLite database selection in JRadioButtons
     */
    private JPanel addSQLiteDatabaseSelection() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder("SQLITE");
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        titledBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        JPanel sqliteSelectionPanel = new JPanel(new GridLayout(1,0));

        // YES/NO radio button selection
        JRadioButton sqliteYesRadioButton = new JRadioButton("YES");
        sqliteYesRadioButton.setActionCommand("YES");
        JRadioButton sqliteNoRadioButton = new JRadioButton("NO");
        sqliteNoRadioButton.setActionCommand("NO");
        sqliteNoRadioButton.setSelected(true);
        ButtonGroup sqliteSelectionButtonGroup = new ButtonGroup();
        sqliteSelectionButtonGroup.add(sqliteYesRadioButton);
        sqliteSelectionButtonGroup.add(sqliteNoRadioButton);

        JPanel sqliteRadioButtonsPanel = new JPanel(new GridLayout(1,0));
        sqliteRadioButtonsPanel.add(sqliteYesRadioButton);
        sqliteRadioButtonsPanel.add(sqliteNoRadioButton);

        sqliteSelectionPanel.setBorder(titledBorder);
        sqliteSelectionPanel.setName("sqliteSelectionPanel");
        sqliteSelectionPanel.add(sqliteRadioButtonsPanel);
        return sqliteSelectionPanel;
    }

    /**
     * Function to create and add the MySQL database structure selection
     * @return JPanel with the MySQL database selection in JRadioButtons
     */
    private JPanel addMySQLDatabaseSelection() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder("MySQL");
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        titledBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        JPanel mySqlSelectionPanel = new JPanel(new GridLayout(1,0));

        // YES/NO radio button selection
        JRadioButton mySqlYesRadioButton = new JRadioButton("YES");
        mySqlYesRadioButton.setActionCommand("YES");
        JRadioButton mySqlNoRadioButton = new JRadioButton("NO");
        mySqlNoRadioButton.setActionCommand("NO");
        mySqlNoRadioButton.setSelected(true);
        ButtonGroup mySqlSelectionButtonGroup = new ButtonGroup();
        mySqlSelectionButtonGroup.add(mySqlYesRadioButton);
        mySqlSelectionButtonGroup.add(mySqlNoRadioButton);

        JPanel mySqlRadioButtonsPanel = new JPanel(new GridLayout(1,0));
        mySqlRadioButtonsPanel.add(mySqlYesRadioButton);
        mySqlRadioButtonsPanel.add(mySqlNoRadioButton);

        mySqlSelectionPanel.setBorder(titledBorder);
        mySqlSelectionPanel.setName("mySqlSelectionPanel");
        mySqlSelectionPanel.add(mySqlRadioButtonsPanel);
        return mySqlSelectionPanel;
    }

    /**
     * Function to create and add the Mongo database structure selection
     * @return JPanel with the Mongo database selection in JRadioButtons
     */
    private JPanel addMongoDatabaseSelection() {
        TitledBorder titledBorder = BorderFactory.createTitledBorder("MONGODB");
        titledBorder.setTitleJustification(TitledBorder.LEFT);
        titledBorder.setTitlePosition(TitledBorder.DEFAULT_POSITION);

        JPanel mongoDbSelectionPanel = new JPanel(new GridLayout(1,0));

        // YES/NO radio button selection
        JRadioButton mongoDbYesRadioButton = new JRadioButton("YES");
        mongoDbYesRadioButton.setActionCommand("YES");
        JRadioButton mongoDbNoRadioButton = new JRadioButton("NO");
        mongoDbNoRadioButton.setActionCommand("NO");
        mongoDbNoRadioButton.setSelected(true);
        ButtonGroup mongoDbSelectionButtonGroup = new ButtonGroup();
        mongoDbSelectionButtonGroup.add(mongoDbYesRadioButton);
        mongoDbSelectionButtonGroup.add(mongoDbNoRadioButton);

        JPanel mongoDbRadioButtonsPanel = new JPanel(new GridLayout(1,0));
        mongoDbRadioButtonsPanel.add(mongoDbYesRadioButton);
        mongoDbRadioButtonsPanel.add(mongoDbNoRadioButton);

        mongoDbSelectionPanel.setBorder(titledBorder);
        mongoDbSelectionPanel.setName("mongoDbSelectionPanel");
        mongoDbSelectionPanel.add(mongoDbRadioButtonsPanel);
        return mongoDbSelectionPanel;
    }
}
