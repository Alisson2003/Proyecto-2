import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginSocio {
    public JPanel socioLogin;
    private JPasswordField passwordField1;
    private JTextField textField1;
    private JButton accederButton;
    private JButton regresarButton;

    public loginSocio() {
        accederButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usu = textField1.getText();
                String pass = new String(passwordField1.getPassword());
                // Valida que los campos no estén vacíos
                if (usu.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(socioLogin, "Por favor, completa todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Llama al método para validar usuario y contraseña
                boolean validado = validarUsuario(usu, pass);
                if (validado) {
                    //JOptionPane.showMessageDialog(socioLogin, "¡Acceso concedido!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    // Lógica para redirigir a otra ventana o funcionalidad
                    JFrame frame = new JFrame("");
                    frame.setContentPane(new menuSocio().menuSocio);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(800,700);
                    frame.setPreferredSize(new Dimension(800, 700));
                    frame.pack();
                    frame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(socioLogin, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Mi Login");
                frame.setContentPane(new Acceso().accesoPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800,700);
                frame.setPreferredSize(new Dimension(800, 700));
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
    public boolean validarUsuario(String usuario, String password) {
        try {
            // Conectar a la base de datos
            MongoDatabase database = Conexion.getDatabase();
            MongoCollection<Document> collection = database.getCollection("usu_socios");
            // Crear la consulta
            Document query = new Document("nombre", usuario).append("password", password);
            // Verificar si existe un documento que cumpla con la consulta
            Document result = collection.find(query).first();
            // Si el resultado no es null, las credenciales son válidas
            return result != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // En caso de error, devuelve falso
        }
    }
}
