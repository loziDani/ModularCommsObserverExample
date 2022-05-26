package com.ui_subsystem.ui_structure.ui_components.main_coms_form.models;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model class to specify the model structure for the entities JTable
 */
public class ElementsListTableModel extends DefaultTableModel {

    static final String[] COLUMN_NAMES = {"FROM/TO", "IP", "PORT", "NAME", "SURNAME", "PHONE"};

    private List<Person> rowData = new ArrayList<>();

    public void add(Person person){
        for (Person rowDatum : this.rowData)
            if (rowDatum.getId() == person.getId())
                return;
        add(Collections.singletonList(person));
    }

    public void add(List<Person> personList) {
        this.rowData.addAll(personList);
        fireTableDataChanged();
    }

    public void forceUpdate(List<Person> personList){
        this.rowData.clear();
        this.rowData.addAll(personList);
        fireTableDataChanged();
    }

    public Person getPersonDataAt(int row){
        return rowData.get(row);
    }

    @Override
    public int getRowCount() {
        if (this.rowData == null) this.rowData = new ArrayList<>();
        return this.rowData.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(int column){
        return COLUMN_NAMES[column];
    }

    @Override
    public boolean isCellEditable(int row ,int col)
    {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Person person = getPersonDataAt(rowIndex);
        Object value = null;
        switch(columnIndex){
            case 0:
                value = "TO";
                break;
            case 1:
                value = person.getDestinationIP();
                break;
            case 2:
                value = person.getDestinationPort();
                break;
            case 3:
                value = person.getName();
                break;
            case 4:
                value = person.getSurname();
                break;
            case 5:
                value = person.getPhoneNumber();
                break;
        }
        return value;
    }
}
