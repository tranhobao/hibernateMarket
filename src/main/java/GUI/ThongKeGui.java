package GUI;

import BLL.OrderedBLL;
import BLL.ThongKeBLL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ThongKeGui extends JFrame{
    private JPanel jPanel1;
    private JTable table1;
    private JComboBox comboBoxDoanhThu;
    private JComboBox comboBoxSanPham;

    private ThongKeBLL thongKeBLL;

    public ThongKeGui() {
        thongKeBLL = new ThongKeBLL();
        init();
    }

    private void init(){
        setContentPane(jPanel1);
        setSize(1200, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        initCombobox();
    }

    private void initCombobox() {
        setComboBoxDoanhThu();
        setComboBoxSanPham();
    }


    private void setComboBoxDoanhThu() {
        comboBoxDoanhThu.addItem("Days");
        comboBoxDoanhThu.addItem("Months");
        comboBoxDoanhThu.addItem("Years");

        comboBoxDoanhThu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String select = comboBoxDoanhThu.getSelectedItem().toString();
                Object[] title = {"", "Sum total with Date"};
                Object[][] list = null;
                if (select.contains("Days")) {
                    title[0] = "Date (year-month-day)";
                    list = thongKeBLL.thongKeDoanhThu(0);
                }
                else if (select.contains("Months")) {
                    title[0] = "Date (year-month)";
                    list = thongKeBLL.thongKeDoanhThu(1);
                }
                else if (select.contains("Years")) {
                    title[0] = "Date (year)";
                    list = thongKeBLL.thongKeDoanhThu(2);
                }
                setTableModelDoanhThu(title, list);
            }
        });
    }

    private void setTableModelDoanhThu(Object[] select, Object[][] list) {
        if (select != null && list != null) {
            DefaultTableModel model = new DefaultTableModel(list, select);
            table1.setModel(model);
        }
    }

    private void setComboBoxSanPham() {
        comboBoxSanPham.addItem("Vegetable");
        comboBoxSanPham.addItem("Category");

        comboBoxSanPham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String select = comboBoxSanPham.getSelectedItem().toString();
                Object[] title = null;
                Object[][] list = null;
                if (select.contains("Vegetable")) {
                    title = new Object[6];
                    title[0] = "id vegetable";
                    title[1] = "name vegetable";
                    title[2] = "số lượng còn";
                    title[3] = "price";
                    title[4] = "số lượng đã bán";
                    title[5] = "doanh thu";
                    list = thongKeBLL.thongKeSP(0);
                }
                else if (select.contains("Category")) {
                    title = new Object[4];
                    title[0] = "id category";
                    title[1] = "name category";
                    title[2] = "số lượng đã bán";
                    title[3] = "doanh thu";
                    list = thongKeBLL.thongKeSP(1);
                }
                setTableModelDoanhThu(title, list);
            }
        });
    }

}
