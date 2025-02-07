import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;

public class CRUD {
    public JPanel CRUD;
    private JTextField textNombre;
    private JTextField textEmail;
    private JTextField textMembresia;
    private JTextField textEstado;
    private JButton agregarButton;
    private JButton salirButton;
    private JTable table1;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JPasswordField passwordField1;

    private MongoDatabase database;
    private MongoCollection<Document> coleccionSocios;
    private MongoCollection<Document> coleccionReportes;
    private DefaultTableModel modeloTabla;


    public CRUD() {
        // Usar la conexión existente
        database = Conexion.getDatabase();
        coleccionSocios = database.getCollection("usu_socios");
        coleccionReportes = database.getCollection("reportes_usuarios");


        // Configurar la tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Email", "Membresía", "Estado", "Password"}, 0);
        table1.setModel(modeloTabla);

        cargarDatos(); // Cargar datos al iniciar

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarSocio();
            }
        });

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarSocio();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarSocio();
            }
        });
        salirButton.addActionListener(new ActionListener() {
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

    // Método para cargar los socios en la tabla
    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        FindIterable<Document> socios = coleccionSocios.find();

        for (Document doc : socios) {
            modeloTabla.addRow(new Object[]{
                    doc.getObjectId("_id"),
                    doc.getString("nombre"),
                    doc.getString("email"),
                    doc.getString("membresia"),
                    doc.getString("estado"),
                    doc.getString("password"),
            });
        }
    }

    // Método para agregar un socio
    private void agregarSocio() {
        String nombre = textNombre.getText();
        String email = textEmail.getText();
        String membresia = textMembresia.getText();
        String estado = textEstado.getText();
        String password = new String(passwordField1.getPassword());

        // que todos los campos esten completos
        if (nombre.isEmpty() || email.isEmpty() || membresia.isEmpty() || estado.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos.");
            return;
        }

        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

        Document nuevoSocio = new Document("nombre", nombre)
                .append("email", email)
                .append("membresia", membresia)
                .append("estado", estado)
                .append("password", passwordHash);

        coleccionSocios.insertOne(nuevoSocio);

        Document reporte = new Document("accion", "Agregar")
                .append("detalle", "Socio agregado: " + nombre)
                .append("fecha", new Date());
        coleccionReportes.insertOne(reporte);

        JOptionPane.showMessageDialog(null, "Socio agregado correctamente");
        cargarDatos();
    }

    // Método para modificar un socio seleccionado
    private void modificarSocio() {
        int filaSeleccionada = table1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona un socio para modificar");
            return;
        }

        String id = table1.getValueAt(filaSeleccionada, 0).toString();
        String nuevoNombre = textNombre.getText();
        String nuevoEmail = textEmail.getText();
        String nuevaMembresia = textMembresia.getText();
        String nuevoEstado = textEstado.getText();
        String nuevoPassword = Arrays.toString(passwordField1.getPassword());

        Document filtro = new Document("_id", new ObjectId(id));
        Document actualizacion = new Document("$set", new Document("nombre", nuevoNombre)
                .append("email", nuevoEmail)
                .append("membresia", nuevaMembresia)
                .append("estado", nuevoEstado)
                .append("password", nuevoPassword));

        coleccionSocios.updateOne(filtro, actualizacion);

        Document reporte = new Document("accion", "Modificar")
                .append("detalle", "Socio modificado: " + nuevoNombre)
                .append("fecha", new Date());
        coleccionReportes.insertOne(reporte);

        JOptionPane.showMessageDialog(null, "Socio modificado correctamente");
        cargarDatos();
    }

    // Método para eliminar un socio
    private void eliminarSocio() {
        int filaSeleccionada = table1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona un socio para eliminar");
            return;
        }

        String id = table1.getValueAt(filaSeleccionada, 0).toString();
        coleccionSocios.deleteOne(Filters.eq("_id", new ObjectId(id)));

        Document reporte = new Document("accion", "Eliminar")
                .append("detalle", "Socio eliminado: ID " + id)
                .append("fecha", new Date());
        coleccionReportes.insertOne(reporte);

        JOptionPane.showMessageDialog(null, "Socio eliminado correctamente");
        cargarDatos();
    }
}
