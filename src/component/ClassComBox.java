package component;


import sql.StuDB;

import javax.swing.*;

public class ClassComBox extends JComboBox<String> {
    private static final StuDB STU_TOOL = new StuDB();

    public ClassComBox() {
        setBorder(BorderFactory.createTitledBorder("??ѡ???༶"));
        setModel(new DefaultComboBoxModel<>(STU_TOOL.listClass()));
        setSelectedItem("stu");
    }

    public void update() {
        setModel(new DefaultComboBoxModel<>(STU_TOOL.listClass()));
    }
}
