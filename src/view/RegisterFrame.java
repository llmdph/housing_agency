package view;

import dao.UserDao;
import model.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JFrame jFrame = new JFrame("注册");
    private Container c = jFrame.getContentPane();
    private JLabel a1 = new JLabel("用户名");
    private JTextField username = new JTextField();
    private JLabel a2 = new JLabel("密   码");
    private JPasswordField password = new JPasswordField();
    private  JButton okbtn = new JButton("注册");


    public RegisterFrame() {

        // 设置窗体的位置及大小
        jFrame.setBounds(600, 200, 300, 220);
        // 设置一层相当于桌布的东西
        c.setLayout(new BorderLayout());// 布局管理器
        // 设置按下右上角X号后关闭
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 初始化--往窗体里放其他控件
        jFrame.setLocationRelativeTo(null);// 窗
        // 标题部分
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new JLabel("房屋中介管理系统"));
        c.add(titlePanel, "North");
        // 输入部分-
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(null);
        a1.setBounds(50, 20, 50, 20);
        a2.setBounds(50, 60, 50, 20);
        fieldPanel.add(a1);
        fieldPanel.add(a2);
        username.setBounds(110, 20, 120, 20);
        password.setBounds(110, 60, 120, 20);
        a1.setBackground(Color.WHITE);// 设置背景色
        fieldPanel.add(username);
        fieldPanel.add(password);
        c.add(fieldPanel, "Center");

        // 按钮部分
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(okbtn);
        c.add(buttonPanel, "South");
        // 设置窗体可见
        jFrame.setVisible(true);
        a1.setForeground(Color.white);
        a2.setForeground(Color.white);
        fieldPanel.setBackground(Color.DARK_GRAY);
        // 确认按下去获取

        okbtn.addActionListener  (new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = username.getText();
                    String pwd = String.valueOf(password.getPassword());
                    UserDao userDao = new UserDao();
                    Admin admin = null;
                    int i= 0;
                    try {
                        i = userDao.register(name, pwd);
                        System.out.println(i);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    if ( i==1) {

                        jFrame.setVisible(false);
                        JOptionPane.showMessageDialog(null, "注册成功");
                        try {
                            new LoginFrame();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        username.setText("");
                        password.setText("");
                        JOptionPane.showMessageDialog(null, "用户名已经被注册！");
                    }
                } catch (HeadlessException e1) {
                    username.setText("");
                    password.setText("");
                    JOptionPane.showMessageDialog(null, "登录失败");
                }

            }
        });

    }

}
