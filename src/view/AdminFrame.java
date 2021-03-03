package view;

import dao.HouseDao;
import model.Admin;
import model.House;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.Year;
import java.util.ArrayList;

public class AdminFrame extends JFrame {

    private JTable table;
    JScrollPane scrollPane;
    Font font=new Font("宋体",Font.PLAIN,12);
    //搜索条件
    private JLabel[] condition =
            {new JLabel("户主"), new JLabel("地址"), new JLabel("面积"),
                    new JLabel("朝向"), new JLabel("价格"), new JLabel("租/卖"),
                    new JLabel("状态")};
    private JTextField[] houseTxt = new JTextField[7];

    ArrayList houses = new ArrayList();
    String[] inputIn = new String[7];
    //表格字段
    String[] heads = {"编号", "户主", "电话", "地址", "面积", "朝向", "价格", "租/卖", "情况", "时间"};

    public boolean isWho() {
        return who;
    }

    public void setWho(boolean who) {
        this.who = who;
    }

    private boolean who = false;
    public AdminFrame(Admin admin) throws Exception {

        setTitle(admin.getName()+"的房源信息");
        setLayout(null);
        setSize(1200, 700);
        setResizable(false);//窗口自主控制大小
        setLocationRelativeTo(null);//屏幕中间
         new Footclass().loadIndyFont();



        int lx = 40;
        for (int i = 0; i < houseTxt.length; i++) {
            houseTxt[i] = new JTextField();
            condition[i].setBounds(lx, 30, 30, 22);
            condition[i].setFont(font);
            houseTxt[i].setBounds(lx + 35, 30, 90, 22);
            lx = lx + 150;
            add(condition[i]);
            add(houseTxt[i]);
        }
        JButton QueryBtn = new JButton("查询");
        JButton UpdateBtn = new JButton("修改");
        JButton InsertBtn = new JButton("插入");
        JButton DeleteBtn = new JButton("删除");
        JButton MonthStaBtn = new JButton("月绩");
        JButton YearStaBtn = new JButton("年绩");
        JButton ContractBtn = new JButton("合同");
        JButton SenseBtn = new JButton("常识");

        QueryBtn.setBounds(1100, 30, 60, 22);
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
        query(admin.getName());

        //查询按钮
        QueryBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
//                    scrollPane.removeAll();
                    remove(scrollPane);
                    query(admin.getName());
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
                String phone = (String) table.getModel().getValueAt(row,2);
                String site = (String) table.getModel().getValueAt(row,3);
                String area= (String) table.getModel().getValueAt(row,4);
                String price =(String)table.getModel().getValueAt(row,6);
                House house = new House();
                house.setId(Integer.parseInt(id));
                house.setName(name);
                house.setPhone(phone);
                house.setSite(site);
                house.setArea(Integer.parseInt(area));
                house.setPrice(Integer.parseInt(price));
                try {
                    UpdateFrame updateFrame = new UpdateFrame(AdminFrame.this, admin.getName(),house);

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
                    new InsertFrame(AdminFrame.this,admin.getName());
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
                int id = Integer.valueOf((String) table.getModel().getValueAt(row,0)) ;
                try {
                    int i = HouseDao.Delete(admin.getName(),id);

                    if (i>=1){
                        JOptionPane.showMessageDialog(null,"删除成功");
                        remove(scrollPane);
                        query(admin.getName());
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"修改失败");
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });

        //月绩
        MonthStaBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new MonthPerformanceFrame(admin.getName());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        //年绩
        YearStaBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new YearPerformanceFrame(admin.getName());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });



        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (who){
                    setVisible(true);
                    dispose();
                }else {
                    dispose();
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });
        setVisible(true);
    }


    //表格内容
    public  void query(String admin) throws Exception {


        for (int i = 0; i < houseTxt.length; i++) {
            inputIn[i] = houseTxt[i].getText();
        }

        houses = HouseDao.queryAllHouses(admin,inputIn);
        String[][] hero = new String[houses.size()][10];
        for (int i = 0; i < hero.length; i++) {
            House house = (House) houses.get(i);
            hero[i][0] = String.valueOf(house.getId());
            hero[i][1] = house.getName();
            hero[i][2] = house.getPhone();
            hero[i][3] = house.getSite();
            hero[i][4] = String.valueOf(house.getArea());
            hero[i][5] = house.getDirect();
            hero[i][6] = String.valueOf(house.getPrice());
            hero[i][7] = house.getRs()==1?"租":"卖";
            hero[i][8] = house.getState()==1?"已"+(house.getRs()==1?"租":"卖"):"未"+(house.getRs()==1?"租":"卖");
            hero[i][9] = house.getTime();
        }
        table = new JTable(hero, heads);
         scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 70, 1175, 520);

        //不可编辑
        table.setModel(new DefaultTableModel(hero, heads){
                public boolean isCellEditable(int rowIndex, int mColIndex) {
                    return false;
            }
        });
        table.setRowHeight(20);
        table.getTableHeader().setReorderingAllowed(false);//表头字段不可移动
       // table.setEditable(false);
        //居中
        DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
        tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, tableCellRenderer);
        add(scrollPane);
    }
    //
}
