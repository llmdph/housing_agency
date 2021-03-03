package view;

import dao.HouseDao;
import model.House;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UpdateFrame extends JFrame {

    Font font=new Font("宋体",Font.PLAIN,16);
    private JLabel[] labels =
            {new JLabel("户主"),new JLabel("电话"), new JLabel("地址"), new JLabel("面积"),
                    new JLabel("价格"), new JLabel("朝向"), new JLabel("租/卖"),
                    new JLabel("状态")};
    private JTextField[] houseTxt = new JTextField[5];
    String [] a = {"东","南","西","北","东北","东南","西北","西南"};
    String [] b = {"卖","租"};
    String [] c = {"未卖出","已卖出"};
    private JComboBox[] comboBox={
        new JComboBox(a),new JComboBox(b),new JComboBox(c),
    };
    public JButton UpdateBtn = new JButton("修改");
    House house = new House();
    String[] inputIn = new String[8];

     public UpdateFrame(AdminFrame adminFrame,String admin,House house) throws Exception{

        setTitle("修改");
        setLayout(null);
        setSize(400,500);
        setResizable(false);//窗口自主控制大小
        setLocationRelativeTo(null);

        houseTxt[0] = new JTextField(house.getName());
        houseTxt[1] = new JTextField(house.getPhone());
        houseTxt[2] = new JTextField(house.getSite());
        houseTxt[3] = new JTextField(String.valueOf(house.getArea()));
        houseTxt[4] = new JTextField(String.valueOf(house.getPrice()));

        int lx = 30;
        for (int i = 0; i < 8; i++) {
            labels[i].setBounds(55, lx, 40, 25);
            labels[i].setFont(font);
            if(i<5){
                houseTxt[i].setBounds(115, lx, 210, 25);
                add(houseTxt[i]);}
            lx = lx + 45;
            add(labels[i]);
        }
        UpdateBtn.setFont(font);
        UpdateBtn.setBounds(163,400,70,25);
        add(UpdateBtn);

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

        UpdateBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Update(admin, house.getId());
                    adminFrame.remove(adminFrame.scrollPane);
                    adminFrame.query(admin);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        setVisible(true);
    }

    public void Update(String admin, int id) throws Exception{
         for (int i=0;i<5;i++){
            inputIn[i] = houseTxt[i].getText();
        }
        inputIn[5] = (String) comboBox[0].getSelectedItem();
        inputIn[6] = String.valueOf(comboBox[1].getSelectedIndex());
        inputIn[7] = String.valueOf(comboBox[2].getSelectedIndex());
        int i=HouseDao.Update(admin,id,inputIn);
        if (i==1){
            JOptionPane.showMessageDialog(null,"修改成功");
            setVisible(false);
        }
        else {
            JOptionPane.showMessageDialog(null,"修改失败");
        }
    }

}
