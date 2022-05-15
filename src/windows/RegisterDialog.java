package windows;

import security.AES;
import security.MD5;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class RegisterDialog extends JFrame {
    public final static int DEFAULT_WIDTH=400;
    public final static int DEFAULT_HEIGHT=400;
    public final RegisterDialog myself=this;
    public RegisterDialog(LoginWindow loginWindow){

        JButton enterButton=new JButton("确定");
        JButton cancelButton=new JButton("取消");
        final JLabel enterUsernameLabel=new JLabel("输入账户名：");
        final JLabel enterPsdLabel=new JLabel("请输入密码：");
        final JLabel repeatPsdLabel=new JLabel("请重复密码");
        JTextField enterUsernameText=new JTextField();

        enterUsernameText.setPreferredSize(LoginWindow.BOX_SIZE);
        enterUsernameText.setSize(LoginWindow.BOX_SIZE);

        JPasswordField enterPsdText=new JPasswordField();
        JPasswordField repeatPsdText=new JPasswordField();
        enterPsdText.setPreferredSize(LoginWindow.BOX_SIZE);
        enterPsdText.setSize(LoginWindow.BOX_SIZE);
        repeatPsdText.setPreferredSize(LoginWindow.BOX_SIZE);
        repeatPsdText.setSize(LoginWindow.BOX_SIZE);

        JPanel enterUsernamePanel=new JPanel();
        enterUsernamePanel.add(enterUsernameLabel);
        enterUsernamePanel.add(enterUsernameText);
        JPanel enterPsdPanel=new JPanel();
        enterPsdPanel.add(enterPsdLabel);
        enterPsdPanel.add(enterPsdText);
        JPanel repeatPsdPanel = new JPanel();
        repeatPsdPanel.add(repeatPsdLabel);
        repeatPsdPanel.add(repeatPsdText);
        JPanel enterCancel = new JPanel();
        enterCancel.add(enterButton);
        enterCancel.add(cancelButton);

        Box frame = Box.createVerticalBox();
        frame.add(enterUsernamePanel);
        frame.add(enterPsdPanel);
        frame.add(repeatPsdPanel);
        frame.add(enterCancel);

        setContentPane(frame);
        setTitle("注册账号");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Register Dialog Closed");
                loginWindow.setEnabled(true);
                dispose();
            }
        });

        cancelButton.addActionListener(event -> {
            System.out.println("Cancel Register");
            dispatchEvent(new WindowEvent(myself, WindowEvent.WINDOW_CLOSING));
        });

        enterButton.addActionListener(event->{
            final File userData=new File("admin/admin.info");
            final boolean isSame=String.valueOf(enterPsdText.getPassword()).intern()
                    .equals(String.valueOf(repeatPsdText.getPassword()).intern());
            final boolean isEmpty=enterUsernameText.getText().length()==0||
                    enterPsdText.getPassword().length==0||
                    enterPsdText.getPassword().length==0;
            if(!userData.exists()){
                try {
                    boolean createFile=userData.createNewFile();
                    if(!createFile){
                        throw new IOException("无法创建用户配置文件!");
                    }
                }catch (IOException exception){
                    JOptionPane.showMessageDialog(null
                    ,"无读写权限，程序无法正常运行!","ERROR!",
                            JOptionPane.ERROR_MESSAGE);
                    exception.initCause(new IOException("Failed to create file: userData.info"));
                    exception.printStackTrace();
                    return;
                }
            } else if(isEmpty){
                JOptionPane.showMessageDialog(null,
                        "用户名或密码为空", "Error",
                        JOptionPane.ERROR_MESSAGE);
                System.err.println("Empty Username or PassWord");
                return;
            }else if(!isSame){
                JOptionPane.showMessageDialog(null,
                        "两次输入的密码不相同", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            final String userName = MD5.encode(enterUsernameText.getText().intern()),
                    passWord = MD5.encode(
                            String.valueOf(enterPsdText.getPassword()).intern());
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(userData)))) {
                String read;
                while ((read = reader.readLine()) != null) {
                    var readText = AES.decode(read).split("&");

                    if (readText.length != 2) {
                        System.err.println("解码的长度不对");
                        throw new IOException();
                    }

                    final boolean isMatch = readText[0].equals(userName);

                    if (isMatch) { // 检查用户名是否存在
                        throw new Exception("Have existed the same user " + enterUsernameText.getText());
                    }
                }
                // 写入新用户
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(userData, true)))) {
                    final String newUser = String.format("%s&%s",
                            userName, passWord);
                    writer.write(AES.encode(newUser));
                    writer.newLine();
                    writer.flush();
                    JOptionPane.showMessageDialog(null,
                            "新用户添加成功", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispatchEvent(new WindowEvent(myself, WindowEvent.WINDOW_CLOSING));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "读取用户数据时发生错误", "Error",
                        JOptionPane.WARNING_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "账户已存在", "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}

