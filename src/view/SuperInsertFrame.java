package view;

import dao.UserDao;
import model.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SuperInsertFrame extends JFrame {
    Font font=new Font("宋体",Font.PLAIN,16);
    private JLabel[] labels =
            {new JLabel("管理员"),new JLabel("密码")};
    private JTextField[] adminTxt = new JTextField[2];
    public JButton InsertBtn = new JButton("插入");
    Admin admin = new Admin();
    String[] inputIn = new String[2];

    public SuperInsertFrame(SuperAdminFrame mainFrame) throws Exception{

        setTitle("插入");
        setLayout(null);
        setSize(400,500);
        setResizable(false);//窗口自主控制大小
        setLocationRelativeTo(null);

        adminTxt[0] = new JTextField();
        adminTxt[1] = new JTextField();


        int lx = 150;
        for (int i = 0; i < 2; i++) {
            labels[i].setBounds(55, lx, 50, 25);
            labels[i].setFont(font);
            adminTxt[i].setBounds(125, lx, 210, 25);
            lx = lx + 45;
            add(adminTxt[i]);
            add(labels[i]);
        }
        InsertBtn.setFont(font);
        InsertBtn.setBounds(163,lx+50,70,25);
        add(InsertBtn);



        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        InsertBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Insert();
                    mainFrame.remove(mainFrame.scrollPane);
                    mainFrame.query();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        setVisible(true);
    }

    public void Insert() throws Exception{
        for (int i=0;i<2;i++){
            inputIn[i] = adminTxt[i].getText();
        }

        int i = UserDao.register(inputIn[0],inputIn[1]);
        if (i==1){
            JOptionPane.showMessageDialog(null,"插入成功");
            setVisible(false);
        }
        else {
            JOptionPane.showMessageDialog(null,"插入失败");
        }
    }
}
