import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*
public class Conexion {

    private static final String host = "localhost";
    private static final String port = "27017";
    private static final String bdd = "FitTrakHub";

    private static MongoClient mongoClient;

    public static MongoDatabase getDatabase() {
        try {
            MongoDatabase conexion = (MongoDatabase) DriverManager.getConnection(host, port,bdd);

            String mongo =
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}*/


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Conexion {

    private static final String host = "localhost";
    private static final String port = "27017";
    private static final String nombre_database = "FitTrackHub";

    private static MongoClient mongoClient;

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            // Conecta al servidor MongoDB
            String connectionString = "mongodb://" + host + ":" + port;
            mongoClient = MongoClients.create(connectionString);
        }
        // Devuelve la conexión a la base de datos
        return mongoClient.getDatabase(nombre_database);
    }

    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
