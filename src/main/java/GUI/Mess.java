package GUI;

import javax.swing.*;

public class Mess {
    public JFrame frame = null;

    public Mess() {
        this.frame = new JFrame();
    }

    public void message(String title, String note){
        JOptionPane.showMessageDialog(frame, note, title, JOptionPane.ERROR_MESSAGE);
    }
}
