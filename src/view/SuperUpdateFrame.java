package view;

import dao.HouseDao;
import dao.UserDao;
import model.Admin;
import model.House;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SuperUpdateFrame extends JFrame {
    Font font=new Font("宋体",Font.PLAIN,16);
    private JLabel[] labels =
            {new JLabel("管理员"),new JLabel("密码")};
    private JTextField[] adminTxt = new JTextField[2];
    public JButton UpdateBtn = new JButton("修改");
    Admin admin = new Admin();
    String[] inputIn = new String[2];

    public SuperUpdateFrame(SuperAdminFrame mainFrame,Admin admin) throws Exception{

        setTitle("修改");
        setLayout(null);
        setSize(400,500);
        setResizable(false);//窗口自主控制大小
        setLocationRelativeTo(null);

        adminTxt[0] = new JTextField(admin.getName());
        adminTxt[1] = new JTextField(admin.getPassword());


        int lx = 150;
        for (int i = 0; i < 2; i++) {
            labels[i].setBounds(55, lx, 50, 25);
            labels[i].setFont(font);
            adminTxt[i].setBounds(125, lx, 210, 25);
            lx = lx + 45;
            add(adminTxt[i]);
            add(labels[i]);
        }
        UpdateBtn.setFont(font);
        UpdateBtn.setBounds(163,lx+50,70,25);
        add(UpdateBtn);



        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        UpdateBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Update(admin.getName());
                    mainFrame.remove(mainFrame.scrollPane);
                    mainFrame.query();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        setVisible(true);
    }

    public void Update( String adminName) throws Exception{
        for (int i=0;i<2;i++){
            inputIn[i] = adminTxt[i].getText();
        }

        int i = UserDao.Update(adminName,inputIn);
        if (i==1){
            JOptionPane.showMessageDialog(null,"修改成功");
            setVisible(false);
        }
        else {
            JOptionPane.showMessageDialog(null,"修改失败");
        }
    }
}
