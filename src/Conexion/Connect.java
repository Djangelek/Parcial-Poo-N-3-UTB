package Conexion;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import java.sql.DriverManager;

public class Connect {
    public Connection connect() {
        File file = new File("BaseDeDatos"); // Ruta relativa del archivo en el proyecto
        String url = "jdbc:sqlite:" + file.getAbsolutePath(); //Ubicacion absoluta de la base de datos
        Connection conn = null;
        try {
            // crear una conexion a la base de datos
            Class.forName("org.sqlite.JDBC"); 
            conn = DriverManager.getConnection(url); 
        } catch (SQLException ex) {
            //si no se puede conectar a la base de datos se muestra un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error"+ex);
            alert.setHeaderText(null);
            alert.setContentText("No se pudo conectar a la base de datos");
            alert.showAndWait();
        } catch (ClassNotFoundException e) {
            //Excepcion por si no se encuentra la clase
            e.printStackTrace();
        }
        return conn;
    }
}
