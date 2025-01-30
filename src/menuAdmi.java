import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuAdmi {
    public JPanel menuAdmi;
    private JButton usuariosButton;
    private JButton pagosButton;
    private JButton reportesButton;
    private JButton regresarButton;

    public menuAdmi() {
        usuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        pagosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        reportesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginAdministrador login = new loginAdministrador();
                JFrame frame = new JFrame("Mi Login");
                frame.setContentPane(new loginAdministrador().loginAdministrador);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800,700);
                frame.setPreferredSize(new Dimension(800, 700));
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
