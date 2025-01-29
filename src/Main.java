import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Mi Login");
        frame.setContentPane(new Acceso().accesoPanel);
        frame.setSize(800,700);
        frame.setVisible(true);

    }
}