import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Mi Login");
        frame.setContentPane(new Acceso().accesoPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,700);
        frame.setPreferredSize(new Dimension(800, 700));
        frame.pack();
        frame.setVisible(true);

    }
}