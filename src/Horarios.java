import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Horarios {
    public JPanel horarios;
    private JComboBox<String> Rutinas;
    private JTextArea textArea1;
    private JButton guardarButton;
    private JButton regresarButton;
    private JComboBox<String> hora;
    private JComboBox<String> dia;
    private ArrayList<String> rutinas;
    private MongoDatabase database;
    private MongoCollection<Document> coleccionRutinas;

    public Horarios() {
        database = Conexion.getDatabase();
        coleccionRutinas = database.getCollection("rutinas");
        rutinas = new ArrayList<>();

        String[] opciones = { "Pecho y Tríceps", "Espalda y Bíceps", "Piernas", "Hombros", "Cardio" };
        String[] dias = { "Lunes", "Martes", "Miércoles", "Jueves", "Viernes" };
        String[] horas = { "08:00 - 10:00", "10:00 - 12:00", "13:00 - 15:00", "15:00 - 17:00", "18:00 - 20:00" };

        Rutinas.setModel(new DefaultComboBoxModel<>(opciones));
        dia.setModel(new DefaultComboBoxModel<>(dias));
        hora.setModel(new DefaultComboBoxModel<>(horas));

        textArea1.setEditable(false);

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rutina = (String) Rutinas.getSelectedItem();
                String diasSeleccionado = (String) dia.getSelectedItem();
                String horasSeleccionado = (String) hora.getSelectedItem();

                Document nuevaRutina = new Document("rutina", rutina)
                        .append("dia", diasSeleccionado)
                        .append("hora", horasSeleccionado);
                coleccionRutinas.insertOne(nuevaRutina);

                rutinas.add("Rutina: " + rutina + " | Día: " + diasSeleccionado + " | Hora: " + horasSeleccionado);
                actualizarRutina();
                JOptionPane.showMessageDialog(null, "Rutina guardada correctamente.");
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Mi Login");
                frame.setContentPane(new Acceso().accesoPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 700);
                frame.setPreferredSize(new Dimension(800, 700));
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    private void actualizarRutina() {
        StringBuilder texto = new StringBuilder("Rutinas Seleccionadas:\n");
        for (String rutina : rutinas) {
            texto.append("- ").append(rutina).append("\n");
        }
        textArea1.setText(texto.toString());
    }
}
