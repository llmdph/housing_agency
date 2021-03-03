package view;

import dao.HouseDao;
import dao.UserDao;
import model.Admin;
import model.House;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Year;
import java.util.ArrayList;

public class SuperAdminFrame extends JFrame {
    private JTable table;
    JScrollPane scrollPane;
    Font font=new Font("宋体",Font.PLAIN,12);
    //搜索条件
    private JLabel adminLabel = new JLabel("管理员");

    private JTextField adminTxt = new JTextField();

    ArrayList admins = new ArrayList();
    String inputIn = null;
    //表格字段
    String[] heads = {"编号", "管理员", "密码"};
    int Bt_x = 50;
    int Bt_y = 380;//底部按钮坐标

    public SuperAdminFrame(Admin admin) throws Exception {

        setTitle(admin.getName()+"-超级管理员");
        setLayout(null);
        setSize(740, 700);
        setResizable(false);//窗口自主控制大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);//屏幕中间
        new Footclass().loadIndyFont();



        int lx = 40;
        adminLabel.setBounds(375,30,40,22);
        adminTxt.setBounds(435,30,90,22);
        add(adminLabel);
        add(adminTxt);

        JButton QueryBtn = new JButton("查询");
        JButton UpdateBtn = new JButton("修改");
        JButton InsertBtn = new JButton("插入");
        JButton DeleteBtn = new JButton("删除");
        JButton MonthStaBtn = new JButton("月绩");
        JButton YearStaBtn = new JButton("年绩");
        JButton ContractBtn = new JButton("合同");
        JButton SenseBtn = new JButton("房源");

        QueryBtn.setBounds(600, 30, 60, 22);
        UpdateBtn.setBounds(40,600,60,22);
        InsertBtn.setBounds(140,600,60,22);
        DeleteBtn.setBounds(240,600,60,22);
        MonthStaBtn.setBounds(340,600,60,22);
        YearStaBtn.setBounds(440,600,60,22);
        ContractBtn.setBounds(540,600,60,22);
        SenseBtn.setBounds(640,600,60,22);

        add(QueryBtn);
        add(UpdateBtn);
        add(InsertBtn);
        add(DeleteBtn);
        add(MonthStaBtn);
        add(YearStaBtn);
        add(ContractBtn);
        add(SenseBtn);
        query();

        //查询按钮
        QueryBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    remove(scrollPane);
                    query();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        //更新按钮
        UpdateBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row==-1){
                    JOptionPane.showMessageDialog(null,"请选择一行");
                    return;
                }
                String id = (String)table.getModel().getValueAt(row,0);
                String name = (String) table.getModel().getValueAt(row,1);
                String pw= (String) table.getModel().getValueAt(row,2);
                Admin admin = new Admin();
                admin.setId(Integer.parseInt(id));
                admin.setName(name);
                admin.setPassword(pw);
                try {
                    new SuperUpdateFrame(SuperAdminFrame.this,admin);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        //插入按钮
        InsertBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new SuperInsertFrame(SuperAdminFrame.this);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        //删除按钮
        DeleteBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row==-1){
                    JOptionPane.showMessageDialog(null,"请选择一行");
                    return;
                }
                int id = Integer.valueOf((String)table.getModel().getValueAt(row,0));
                String name = (String) table.getModel().getValueAt(row,1);
                try {
                    int i = UserDao.Delete(name,id);
                    if (i>=1){
                        JOptionPane.showMessageDialog(null,"删除成功");
                        remove(scrollPane);
                        query();
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"修改失败");
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });


        //房源按钮
        SenseBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row==-1){
                    JOptionPane.showMessageDialog(null,"请选择一行");
                    return;
                }
                String id = (String)table.getModel().getValueAt(row,0);
                String name = (String) table.getModel().getValueAt(row,1);
                String pw= (String) table.getModel().getValueAt(row,2);
                Admin admin = new Admin();
                admin.setId(Integer.parseInt(id));
                admin.setName(name);
                admin.setPassword(pw);
                try {
                   AdminFrame who =  new AdminFrame(admin);
                   who.setWho(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        //月绩
        MonthStaBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row==-1){
                    JOptionPane.showMessageDialog(null,"请选择一行");
                    return;
                }
                String name = (String) table.getModel().getValueAt(row,1);
                try {
                    new MonthPerformanceFrame(name);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        //年绩
        YearStaBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row==-1){
                    JOptionPane.showMessageDialog(null,"请选择一行");
                    return;
                }
                String name = (String) table.getModel().getValueAt(row,1);
                try {
                    new YearPerformanceFrame(name);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }


    //表格内容
    public  void query() throws Exception {
            inputIn =adminTxt.getText();
            admins = UserDao.queryAllAdmin(inputIn);
        String[][] hero = new String[admins.size()][3];
        for (int i = 0; i < hero.length; i++) {
            Admin admin = (Admin) admins.get(i);
            hero[i][0] = String.valueOf(admin.getId());
            hero[i][1] = admin.getName();
            hero[i][2] = admin.getPassword();
        }
            table = new JTable(hero, heads);
            scrollPane = new JScrollPane(table);
            scrollPane.setBounds(10, 70, 715, 520);
            table.setModel(new DefaultTableModel(hero, heads){
                public boolean isCellEditable(int rowIndex, int mColIndex) {
                    return false;
                }
            });
            table.setRowHeight(20);
            table.getTableHeader().setReorderingAllowed(false);
            // table.setEditable(false);
            //居中
            DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
            tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            table.setDefaultRenderer(Object.class, tableCellRenderer);
            add(scrollPane);
        }

}
