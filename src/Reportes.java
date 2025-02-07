import com.mongodb.client.*;
import org.bson.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reportes {

    public JPanel Reportes;
    private JButton salirButton;
    private JTextArea textArea1;
    private MongoDatabase database;

    public Reportes() {
        database = Conexion.getDatabase();
        MongoCollection<Document> coleccionReportes = database.getCollection("reportes_usuarios");
        textArea1.setEditable(false);

        StringBuilder reportesTexto = new StringBuilder();
        FindIterable<Document> reportes = coleccionReportes.find();
        for (Document reporte : reportes) {
            reportesTexto.append("Acción: ").append(reporte.getString("accion"))
                    .append("\nDetalle: ").append(reporte.getString("detalle"))
                    .append("\nFecha: ").append(reporte.getDate("fecha"))
                    .append("\n\n");
        }

        textArea1.setText(reportesTexto.toString());

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("");
                frame.setContentPane(new Acceso().accesoPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 700);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
