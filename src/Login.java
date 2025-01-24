import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

public class Login {
    public JPanel mainPanel;
    private JButton cancelarButton;
    private JButton accederButton;
    private JTextField usuario;
    private JPasswordField passwordField1;

    public Login() {
        accederButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usu = usuario.getText();
                String pass = new String(passwordField1.getPassword());
                // Valida que los campos no estén vacíos
                if (usu.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(mainPanel, "Por favor, completa todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // Llama al método para validar usuario y contraseña
                boolean validado = validarUsuario(usu, pass);
                if (validado) {
                    JOptionPane.showMessageDialog(mainPanel, "¡Acceso concedido!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    // Lógica para redirigir a otra ventana o funcionalidad
                } else {
                    JOptionPane.showMessageDialog(mainPanel, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    public boolean validarUsuario(String usuario, String password) {
        try {
            // Conectar a la base de datos
            MongoDatabase database = Conexion.getDatabase();
            MongoCollection<Document> collection = database.getCollection("Usuarios");
            // Crear la consulta
            Document query = new Document("user", usuario).append("passwor", password);
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
