import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String host = "localhost";
    private static final String port = "27017";
    private static final String bdd = "usuarios";

    private static MongoClient mongoClient;

    public static MongoDatabase getDatabase() {
        try {
            return DriverManager.getConnection(host, port,bdd);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
