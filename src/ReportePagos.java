import com.mongodb.client.*;
import org.bson.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportePagos {
    public JPanel reportePagos;
    private JTextArea textArea1;
    private JButton salirButton;
    private MongoDatabase database;

    public ReportePagos() {
        database = Conexion.getDatabase();
        MongoCollection<org.bson.Document> coleccionPagos = database.getCollection("pagos_gym");

        StringBuilder reporte = new StringBuilder();
        FindIterable<org.bson.Document> pagos = coleccionPagos.find();

        for (Document pago : pagos) {
            reporte.append("ID Usuario: ").append(pago.getString("idUsuario"))
                    .append("\nMonto: $").append(pago.getDouble("monto"))
                    .append("\nDescripción: ").append(pago.getString("descripcion"))
                    .append("\nFecha: ").append(pago.getDate("fecha"))
                    .append("\n\n");
        }
        textArea1.setText(reporte.toString());

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Mi Login");
                frame.setContentPane(new Acceso().accesoPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 700);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
