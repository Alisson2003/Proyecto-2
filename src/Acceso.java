import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Acceso {
    public JPanel accesoPanel;
    private JButton administradorButton;
    private JButton socioButton;
    private JButton registroButton;
    private JButton cancelarButton;

    public Acceso() {
        administradorButton.addActionListener(new ActionListener() {
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
        socioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginAdministrador login = new loginAdministrador();
                JFrame frame = new JFrame("Mi Login");
                frame.setContentPane(new loginSocio().socioLogin);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800,700);
                frame.setPreferredSize(new Dimension(800, 700));
                frame.pack();
                frame.setVisible(true);

            }
        });
        registroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginAdministrador login = new loginAdministrador();
                JFrame frame = new JFrame("Mi Login");
                frame.setContentPane(new loginRegistro().registroLogin);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800,700);
                frame.setPreferredSize(new Dimension(800, 700));
                frame.pack();
                frame.setVisible(true);
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(accesoPanel, "Gracias por acceder al sistema.", "Mensaje", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
        });
    }
}
