package view;

import dao.HouseDao;
import model.House;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MonthPerformanceFrame extends JFrame {

    Font font=new Font("宋体",Font.PLAIN,16);
    private JLabel[] text = {new JLabel("本月租房成交量:"),
            new JLabel("本月卖房成交额:"),
            new JLabel("本月卖房成交量:"),
            new JLabel("本月卖房成交额:")
    };
    private JLabel[] resultTxt = {new JLabel(String.valueOf(0)),
            new JLabel(String.valueOf(0)),
            new JLabel(String.valueOf(0)),
            new JLabel(String.valueOf(0)),};
    private int[] result = {0,0,0,0};
    ArrayList houses = new ArrayList();

    public MonthPerformanceFrame(String admin) throws Exception{

        setTitle(admin+"的月绩");
        setLayout(null);
        setSize(350,250);
        setResizable(false);//窗口自主控制大小
        setLocationRelativeTo(null);

        int height = 25;
        queryMonth(admin);
        for(int i = 0; i < 4; i++){
            text[i].setBounds(50,height,150,25);
            text[i].setFont(font);
            add(text[i]);
            if(i==0||i==2){
                resultTxt[i].setText(String.valueOf(result[i])+"套");
            }else {
                resultTxt[i].setText(String.valueOf(result[i])+"元");
            }
            resultTxt[i].setBounds(190,height,100,25);
            resultTxt[i].setFont(font);
            add(resultTxt[i]);
            height+=45;
        }

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }

    private void queryMonth(String admin) {
        try {
            houses = HouseDao.queryMonth(admin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i= 0;i<houses.size();i++){
                House house = (House) houses.get(i);
                if(house.getRs()==1){
                    result[0]++;
                    result[1]+=house.getPrice();
                }else {
                    result[2]++;
                    result[3]+=house.getPrice();
                }
            }
    }
}
