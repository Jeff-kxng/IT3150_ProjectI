package Project1_ASCII;

import javax.swing.*;

public class ASCII_Converter {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ASCII converter = new ASCII();
                converter.setVisible(true);
            }
        });
    }
}
