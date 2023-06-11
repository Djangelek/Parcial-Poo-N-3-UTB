import Conexion.Connect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/Ventanas/inicioSesion.fxml")); // Carga el archivo FXML desde la ruta especificada
        Parent root = fxmlloader.load();  // Carga el objeto padre (raíz) del FXML
        Scene scene = new Scene(root); // Crea una nueva escena con el objeto padre cargado
        primaryStage.setTitle("Inicio de Sesion");   // Establece el título de la ventana principal
        primaryStage.setScene(scene); // Establece la escena como escena principal de la ventana
        primaryStage.show(); // Muestra la ventana principal
        Connect connect = new Connect();  // Crea una instancia de la clase Connect
        connect.connect(); // Ejecuta el método connect de la instancia de Connect creada anteriormente
    }
}
