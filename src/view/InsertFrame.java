package view;

import dao.HouseDao;
import model.House;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InsertFrame extends JFrame {
    Font font=new Font("宋体",Font.PLAIN,16);
    private JLabel[] labels =
            {new JLabel("户主"),new JLabel("电话"), new JLabel("地址"), new JLabel("面积"),
                    new JLabel("价格"), new JLabel("朝向"), new JLabel("租/卖"),
                    new JLabel("状态"),new JLabel("时间")};

    private JTextField[] houseTxt = new JTextField[6];
    String [] a = {"东","南","西","北","东北","东南","西北","西南"};
    String [] b = {"卖","租"};
    String [] c = {"未卖出","已卖出"};
    private JComboBox[] comboBox={
            new JComboBox(a),new JComboBox(b),new JComboBox(c),
    };
    public JButton InsertBtn = new JButton("增加");
    private boolean flag = false;

    public boolean isFlag() {
        return flag;
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    String[] inputIn = new String[9];

    public InsertFrame(AdminFrame adminFrame, String admin) throws Exception{

        setTitle("增加");
        setLayout(null);
        setSize(400,500);
        setResizable(false);//窗口自主控制大小
        setLocationRelativeTo(null);

        houseTxt[0] = new JTextField();
        houseTxt[1] = new JTextField();
        houseTxt[2] = new JTextField();
        houseTxt[3] = new JTextField();
        houseTxt[4] = new JTextField();
        houseTxt[5] = new JTextField();



        int lx = 30;
        for (int i = 0; i < 8; i++) {

            labels[i].setBounds(55, lx, 40, 25);
            labels[i].setFont(font);
            if(i<5){
//                houseTxt[i] = new JTextField();
                houseTxt[i].setBounds(115, lx, 210, 25);
                add(houseTxt[i]);}
            lx = lx + 45;
            add(labels[i]);
        }
        labels[8].setBounds(55, lx, 40, 25);
        houseTxt[5].setBounds(115, lx, 210, 25);
        add(labels[8]);
        add(houseTxt[5]);

        InsertBtn.setFont(font);

        InsertBtn.setBounds(163,435,70,25);

        add(InsertBtn);

        int t=lx-135;
        for (int i=0;i<3;i++){
            comboBox[i].setBounds(115,t,210,25);
            add(comboBox[i]);
            t+=45;
        }


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        InsertBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Insert(admin);
                    adminFrame.remove(adminFrame.scrollPane);
                    adminFrame.query(admin);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    public void Insert(String admin) throws Exception{
        for (int i=0;i<5;i++){
            inputIn[i] = houseTxt[i].getText();
        }
        inputIn[5] = (String) comboBox[0].getSelectedItem();

        inputIn[6] = String.valueOf(comboBox[1].getSelectedIndex());
        inputIn[7] = String.valueOf(comboBox[2].getSelectedIndex());
        inputIn[8] = houseTxt[5].getText();
        int i= HouseDao.Insert(admin,inputIn);
        if (i==1){
            JOptionPane.showMessageDialog(null,"插入成功");
            setFlag(true);
            setVisible(false);
        }
        else {
            JOptionPane.showMessageDialog(null,"修改失败");
        }
    }

}
