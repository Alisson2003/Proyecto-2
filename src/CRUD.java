import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CRUD {
    private JPanel CRUD;
    private JTextField textNombre;
    private JTextField textEmail;
    private JTextField textMembresia;
    private JTextField textEstado;
    private JButton agregarButton;
    private JButton salirButton;
    private JTable table1;
    private JButton modificarButton;
    private JButton eliminarButton;

    private MongoDatabase database;
    private MongoCollection<Document> coleccionSocios;
    private DefaultTableModel modeloTabla;

    public CRUD() {
        // Usar la conexión existente
        database = Conexion.getDatabase();
        coleccionSocios = database.getCollection("usu_socios");

        // Configurar la tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Email", "Membresía", "Estado"}, 0);
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
    }

    // Método para agregar un socio
    private void agregarSocio() {
        String nombre = textNombre.getText();
        String email = textEmail.getText();
        String membresia = textMembresia.getText();
        String estado = textEstado.getText();

        Document nuevoSocio = new Document("nombre", nombre)
                .append("email", email)
                .append("membresia", membresia)
                .append("estado", estado);

        coleccionSocios.insertOne(nuevoSocio);
        JOptionPane.showMessageDialog(null, "Socio agregado correctamente");
        cargarDatos();
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
                    doc.getString("estado")
            });
        }
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

        Document filtro = new Document("_id", new ObjectId(id));
        Document actualizacion = new Document("$set", new Document("nombre", nuevoNombre)
                .append("email", nuevoEmail)
                .append("membresia", nuevaMembresia)
                .append("estado", nuevoEstado));

        coleccionSocios.updateOne(filtro, actualizacion);
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

        JOptionPane.showMessageDialog(null, "Socio eliminado correctamente");
        cargarDatos();
    }

    // Método principal para probar la GUI
    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestión de Socios");
        frame.setContentPane(new CRUD().CRUD);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
