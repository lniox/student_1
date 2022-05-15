package sql;


import test.EventLogger;
import test.Student;
import component.datechoose;
import javax.swing.*;
import java.awt.*;

public class EditorDialog extends JDialog {
    private static final EventLogger EVENT_LOGGER = EventLogger.getEventLogger();
    private static final StuDB STU_TOOL = new StuDB();
    private final String[] res = null;
    private JTable table = null;
    private JButton revokeButton = null;
    private String classID = null;

    public EditorDialog(int row, String[] data) {
        setLayout(new FlowLayout());
        JPanel editPanel = new JPanel(new GridLayout(4,8 ));
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));

        JLabel idLabel = new JLabel("学号");
        JLabel nameLabel = new JLabel("姓名");
        JLabel sexualLabel = new JLabel("性别");
        JLabel birthdayLabel = new JLabel("生日");
        JLabel classLabel = new JLabel("班级");
        JLabel chinLabel = new JLabel("语文");
        JLabel mathLabel = new JLabel("数学");
        JLabel engLabel = new JLabel("英语");
        JLabel physLabel = new JLabel("物理");
        JLabel chemLabel = new JLabel("化学");
        JLabel bioLabel = new JLabel("生物");
        JLabel polLabel = new JLabel("政治");
        JLabel hisLabel = new JLabel("历史");
        JLabel geoLabel = new JLabel("地理");

        JTextField[] textFields = new JTextField[14];
        for (int i = 0; i <14; i++) {
            textFields[i] = new editTextField(data[i]);
            textFields[i].setText("");
        }
        textFields[0].setEnabled(false);

        JButton saveButton = new JButton("保存");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        editPanel.add(idLabel);
        editPanel.add(nameLabel);
        editPanel.add(sexualLabel);
        editPanel.add(birthdayLabel);
        editPanel.add(classLabel);
        editPanel.add(chinLabel);
        editPanel.add(mathLabel);
        editPanel.add(engLabel );
        editPanel.add(physLabel);
        editPanel.add(chemLabel);
        editPanel.add(bioLabel);
        editPanel.add(polLabel);
        editPanel.add(hisLabel);
        editPanel.add(geoLabel);
        for (var e : textFields) {
            editPanel.add(e);
        }

        add(editPanel);
        add(buttonPanel);

        pack();
        setVisible(true);

        cancelButton.addActionListener(event -> dispose());
        saveButton.addActionListener(event -> {
            setVisible(false);
            String[] upData = new String[data.length];
            for (int i = 0; i < data.length - 1; i++) {
                upData[i] = textFields[i].getText();
                table.setValueAt(textFields[i].getText(), row, i);
            }
            final var newStu = Student.getStudent(upData);
            table.setValueAt(newStu.getSumScore().toString(), row, 8);

            STU_TOOL.updateStu(newStu);
            EVENT_LOGGER.update(Student.getStudent(data));
            classID = upData[4];

            JOptionPane.showMessageDialog(null,
                    "修改成功", "SUCCESS",
                    JOptionPane.INFORMATION_MESSAGE);
            revokeButton.setEnabled(true);
            STU_TOOL.closeAll();
            dispose();
        });
    }

    public EditorDialog(String[] data) {
        setLayout(new FlowLayout());
        JPanel editPanel = new JPanel(new GridLayout(4, 3));
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));

        JLabel idLabel = new JLabel("学号");
        JLabel nameLabel = new JLabel("姓名");
        JLabel sexualLabel = new JLabel("性别");
        JLabel birthdayLabel = new JLabel("生日");
        JLabel classLabel = new JLabel("班级");
        JLabel chinLabel = new JLabel("语文");
        JLabel mathLabel = new JLabel("数学");
        JLabel engLabel = new JLabel("英语");
        JLabel physLabel = new JLabel("物理");
        JLabel chemLabel = new JLabel("化学");
        JLabel bioLabel = new JLabel("生物");
        JLabel polLabel = new JLabel("政治");
        JLabel hisLabel = new JLabel("历史");
        JLabel geoLabel = new JLabel("地理");



        JTextField[] textFields = new JTextField[14];
        for (int i = 0; i <14 ; i++) {
            textFields[i] = new editTextField(data[14]);
            textFields[i].setText("");
        }

        JComboBox<String> choiceSex=new JComboBox<String>();
        choiceSex.addItem("请选择:");
        choiceSex.addItem("男");
        choiceSex.addItem("女");
        datechoose birthday = datechoose.getInstance();
        birthday.register(textFields[3]);
        //textFields[3].setEditable(false);


        textFields[0].setText("学号自动分配");
        textFields[0].setEditable(false);
        JButton saveButton = new JButton("保存");
        JButton cancelButton = new JButton("取消");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        editPanel.add(idLabel);
        //editPanel.add(textFields[0]);
        editPanel.add(nameLabel);
        //editPanel.add(textFields[1]);
        editPanel.add(sexualLabel);
        //editPanel.add(textFields[2]);
        editPanel.add(birthdayLabel);
        //editPanel.add(textFields[3]);
        editPanel.add(classLabel);
        //editPanel.add(textFields[4]);
        editPanel.add(chinLabel);
        //editPanel.add(textFields[5]);
        editPanel.add(mathLabel);
        //editPanel.add(textFields[6]);
        editPanel.add(engLabel);
        //editPanel.add(textFields[7]);
        editPanel.add(physLabel);
        //editPanel.add(textFields[8]);
        editPanel.add(chemLabel);
        //editPanel.add(textFields[9]);
        editPanel.add(bioLabel);
        //editPanel.add(textFields[10]);
        editPanel.add(polLabel);
        //editPanel.add(textFields[11]);
        editPanel.add(hisLabel);
        //editPanel.add(textFields[12]);
        editPanel.add(geoLabel);
        // editPanel.add(textFields[13]);
        for (var e : textFields) {
            editPanel.add(e);
        }

        add(editPanel);
        add(buttonPanel);
        pack();
        setVisible(true);

        cancelButton.addActionListener(event -> dispose());
        saveButton.addActionListener(event -> {
            setVisible(false);
            var upData = new String[data.length];
            upData[0] = "111";

            for (int i = 1; i < data.length - 1; i++) {
                upData[i] = textFields[i].getText();
            }
            final Student newStu = Student.getStudent(upData);
            newStu.setSumScore();

            Long id = STU_TOOL.addStu(newStu);
            EVENT_LOGGER.add(id.toString());

            JOptionPane.showMessageDialog(null,
                    "添加成功", "SUCCESS！",
                    JOptionPane.INFORMATION_MESSAGE);

            STU_TOOL.closeAll();
            dispose();
        });
    }

    public void setTable(JTable table) {
        this.table = table;
    }
    public void setRevokeButton(JButton revokeButton) {
        this.revokeButton = revokeButton;
    }
}

class editTextField extends JTextField {
    public editTextField(String s) {
        setText(s);
        setColumns(12);
        setVisible(true);
    }
}