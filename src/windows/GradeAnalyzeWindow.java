package windows;


import component.ClassComBox;
import test.Student;
import sql.GroupScoreDB;
import sql.StuDB;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.HashMap;

import static java.awt.EventQueue.invokeLater;

/**
 *
 */
public class GradeAnalyzeWindow extends JFrame {
    private static final StuDB STU_TOOLS = new StuDB();
    private final GradeAnalyzeWindow myself = this;
    private final String[] TITLE = {
            "学号", "姓名", "性别", "出生年月", "班级",
            "语文", "语文班级平均分", "语文全校平均分",
            "数学", "数学班级平均分", "数学全校平均分",
            "英语", "英语班级平均分", "英语全校平均分",
            "物理", "物理班级平均分", "物理全校平均分",
            "化学", "化学班级平均分", "化学全校平均分",
            "生物", "生物班级平均分", "生物全校平均分",
            "政治", "政治班级平均分", "政治全校平均分",
            "历史", "历史班级平均分", "历史全校平均分",
            "地理", "地理班级平均分", "地理全校平均分",
            "总成绩", "班级总成绩平均分", "全校总成绩平均分"
    };
    private HashMap classScore;
    private final GroupScoreDB groupScoreTools = GroupScoreDB.getGroupScoreDBdb();

    private final DefaultTableModel editorTableMode;

    public GradeAnalyzeWindow() {
        // 创建面板
        var sfPanel = new JPanel();
        var searchPanel = new JPanel();
        var functionPanel = new JPanel();
        var dataPanel = new JPanel();
        var selectClassPanel = new JPanel();
        var selectClassRange = new ClassComBox();

        // 查找和功能面板
        searchPanel.setLayout(new GridLayout(1, 2));
        selectClassPanel.setLayout(new GridLayout(1, 3));
        functionPanel.setLayout(new GridLayout(1, 6));
        var findTextFiled = new JTextField();
        var findButton = new JButton("查找");
        var analyzeButton = new AnalyzeButton("个人成绩比例");
        var classAnalyzeButton = new AnalyzeButton("个人成绩分析");
        var exportGradeButton = new JButton("成绩信息导出");

        var selectPanel = new JPanel();
        // selectPanel 的组件设置
        var idButton = new JRadioButton("学号");
        idButton.setFont(new Font("楷体", Font.PLAIN, 20));

        var nameButton = new JRadioButton("姓名");
        nameButton.setFont(new Font("楷体", Font.PLAIN, 20));
        nameButton.setSelected(true);

        var group = new ButtonGroup();
        group.add(idButton);
        group.add(nameButton);
        selectPanel.add(nameButton);
        selectPanel.add(idButton);

        // 控件组合
        selectClassPanel.add(selectPanel);
        selectClassPanel.add(selectClassRange);
        selectClassPanel.add(findButton);
        searchPanel.add(findTextFiled);
        searchPanel.add(selectClassPanel);
        functionPanel.add(analyzeButton);
        functionPanel.add(classAnalyzeButton);
        functionPanel.add(exportGradeButton);

        // 查找和功能面板整合
        sfPanel.setLayout(new GridLayout(2, 1));
        sfPanel.add(searchPanel);
        sfPanel.add(functionPanel);

        // 信息面板
        dataPanel.setLayout(new GridLayout(1, 1));
        String[][] data = {{"正在加载数据中"}, {"请耐心等候"}, {"不要进行其他操作"}};

        // 编辑框
        editorTableMode = new DefaultTableModel(data, new String[]{"提示"}) {
            @Override // 无法直接编辑，必须通过双击或编辑按钮来实现
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        var editorTable = new JTable(editorTableMode);
        var editorLayout = new DefaultTableCellRenderer();
        editorLayout.setHorizontalAlignment(JLabel.CENTER);
        editorTable.setRowHeight(27); // 设置行高
        editorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // 设置只选中一行
        editorTable.setDefaultRenderer(Object.class, editorLayout);
        // 设置选中一行后，允许使用某些功能
        editorTable.getSelectionModel().addListSelectionListener(e -> {
            analyzeButton.setEnabled(true);
            classAnalyzeButton.setEnabled(true);
        });
        new Thread(() -> {
            try {
                updateEditor(groupScoreTools.listStu());
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }).start();


        var editorScroll = new JScrollPane(editorTable);
        dataPanel.add(editorScroll);

        // 窗口添加组件
        add(sfPanel, BorderLayout.NORTH);
        add(dataPanel, BorderLayout.CENTER);

        // 添加事件和属性设置

        // 编辑窗口事件
        editorTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    classAnalyzeButton.doClick();
                }
            }
        });
        // 添加班级选择下拉表事件
        // 按钮属性设置
        analyzeButton.setEnabled(false);
        classAnalyzeButton.setEnabled(false);
        exportGradeButton.setEnabled(false);

        // 获取数据
        invokeLater(new Thread(() -> {
            classScore = groupScoreTools.getScores();
            exportGradeButton.setEnabled(true);
        })::start);

        // 分析按钮事件
        analyzeButton.addActionListener(event -> {

            final int selectLine = editorTable.getSelectedRow();
            var stuData = new String[9];

            for (int i = 0; i < 6; i++) {
                stuData[i] = (String) editorTable.getValueAt(selectLine, i);
            }
            stuData[6] = (String) editorTable.getValueAt(selectLine, 8);
            stuData[7] = (String) editorTable.getValueAt(selectLine, 11);
            stuData[8] = (String) editorTable.getValueAt(selectLine, 14);

            invokeLater(() -> {
                var chart = new AnalyzeFrame(Student.getStudent(stuData), AnalyzeFrame.PIE_CHART);
                chart.setLocationRelativeTo(myself);
            });
        });
        classAnalyzeButton.addActionListener(event -> {
            final int selectLine = editorTable.getSelectedRow();
            var stuData = new String[9];

            for (int i = 0; i < 6; i++) {
                stuData[i] = (String) editorTable.getValueAt(selectLine, i);
            }
            stuData[6] = (String) editorTable.getValueAt(selectLine, 8);
            stuData[7] = (String) editorTable.getValueAt(selectLine, 11);
            stuData[8] = (String) editorTable.getValueAt(selectLine, 14);

            invokeLater(() -> {
                var chart = new AnalyzeFrame(Student.getStudent(stuData), AnalyzeFrame.BAR_CHART);
                chart.setLocationRelativeTo(myself);
            });
        });
        // 查找按钮事件
        findButton.addActionListener(event -> {
            System.out.println(editorTable.getSelectedRow());
            String object = findTextFiled.getText();
            String Class = (String) selectClassRange.getSelectedItem();
            String[][] findResult = null;
            System.out.printf("Find: %s\n", object);

            if (nameButton.isSelected()) {
                assert Class != null;
                try {
                    findResult = groupScoreTools.findStu(object, StuDB.FIND_NAME, Class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (idButton.isSelected()) {
                assert Class != null;
                try {
                    findResult = groupScoreTools.findStu(object, StuDB.FIND_ID, Class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            assert findResult != null;
            final int findNumber = findResult.length;
            // 刷新table
            updateEditor(findResult);
            analyzeButton.setEnabled(false);
            classAnalyzeButton.setEnabled(false);

            if (findNumber != 0) {

                final var msg = String.format("找到了%d条结果", findNumber);
                JOptionPane.showMessageDialog(null,
                        msg, "FINISH",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "未找到任何结果", "FINISH",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        });

        // 导出按钮事件
        exportGradeButton.addActionListener(event -> invokeLater(() -> {
            this.setEnabled(false);
            var frame = new selectExportDialog((String) selectClassRange.getSelectedItem());
            frame.setLocationRelativeTo(null);
            frame.setFatherWindow(this);
        }));

        // 退出按钮事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                final var ensureExit = JOptionPane.showConfirmDialog(null,
                        "你确认退出系统吗",
                        "WARING",
                        JOptionPane.YES_NO_OPTION);
                if (ensureExit == JOptionPane.YES_OPTION) {
                    myself.setEnabled(true);
                    STU_TOOLS.closeAll();
                    dispose();
                }
            }
        });

        // 设置窗口属性
        setSize(1400, 800);
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("成绩综合分析");
        setVisible(true);
    }

    private void updateEditor(String[][] result) {
        editorTableMode.setDataVector(result, myself.TITLE);
    }
}