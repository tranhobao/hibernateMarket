package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame{
    private JPanel jPanel1;
    private JButton thongke;
    private JButton khachhang;
    private JButton nhaphang;

    public Menu() {
        initMenu();
    }

    private void initMenu() {
        setContentPane(jPanel1);
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setKhachhang();
        setNhaphang();
        setThongke();


    }

    private void setKhachhang() {
        khachhang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomersGui customersGui = new CustomersGui();
            }
        });
    }

    private void setNhaphang() {
        nhaphang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NhapHangGui nhapHangGui = new NhapHangGui();
            }
        });
    }

    private void setThongke() {
        thongke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThongKeGui thongKeGui = new ThongKeGui();
            }
        });
    }



    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.setVisible(true);
    }
}
