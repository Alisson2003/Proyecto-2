import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class Pagos {
    public JPanel Pagos;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton registrarButton;
    private JButton salirButton;

    private MongoDatabase database;
    private MongoCollection<Document> coleccionPagos;

    public Pagos() {
        database = Conexion.getDatabase();
        coleccionPagos = database.getCollection("pagos_gym");

        registrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarPagos();
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

    private void registrarPagos() {
        String idUsuario = textField1.getText();
        String cantidad = textField2.getText();
        String descripcion = textField3.getText();

        if(idUsuario.isEmpty() || cantidad.isEmpty() || descripcion.isEmpty()){
            JOptionPane.showMessageDialog(null, "Ingrese todos los campos");
            return;
        }
        try{
            double cantidadDouble = Double.parseDouble(cantidad);

            Document pago = new Document("_id",idUsuario)
                    .append("cantidad",cantidad)
                    .append("descripcion",descripcion)
                    .append("fecha",new Date());
            coleccionPagos.insertOne(pago);

            JOptionPane.showMessageDialog(null, "Registro agregado correctamente");
            limpiar();
        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Ingrese un numero valido");
        }
    }

    private void limpiar() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
    }
}
