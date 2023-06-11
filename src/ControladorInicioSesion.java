import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import Conexion.Connect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;



public class ControladorInicioSesion {
    PreparedStatement ps;
    ResultSet rs;

    private int numeroIntentos = 1;

    @FXML
    private Button botonRegistar;

    @FXML
    private Button botonIngresar;

    @FXML
    private Button botonLimpiar;

    @FXML
    private PasswordField cajaContrasena;

    @FXML
    private TextField cajaUsuario;

    private static String Usuario;

    

    @FXML
    void limpiar(ActionEvent event) {
        // Limpiar cajas de texto
        cajaContrasena.setText("");
        cajaUsuario.setText("");
    }

    @FXML
    void EnterCajaUsuario(KeyEvent event) throws IOException {
        // Si se presiona la tecla ENTER, se ejecuta el método ingresar
        if (event.getCode() == KeyCode.ENTER) {
        ingresar(null);
    }
    } 
    @FXML
    void EnterCajaContrasena(KeyEvent event) throws IOException {
        // Si se presiona la tecla ENTER, se ejecuta el método ingresar
        if (event.getCode() == KeyCode.ENTER) {
        ingresar(null);
    }
    } 

    private boolean inicioSesion() {
        Connection conexion = null; // Objeto para la conexión
        Connect onConnect = new Connect(); // Objeto para establecer la conexión
        boolean encontrado = false; // Variable para saber si se encontró el usuario
        try {
            conexion = onConnect.connect(); // Se establece la conexión
            ps = conexion.prepareStatement("SELECT * FROM usuarios WHERE usuario=? AND contrasena = ?"); // Se prepara la consulta
            ps.setString(1, cajaUsuario.getText().trim()); // Se asigna el usuario
            ps.setString(2, cajaContrasena.getText().trim()); // Se asigna la contraseña
            rs = ps.executeQuery(); // Se ejecuta la consulta
            if (rs.next()) {
                // Si se encontró el usuario y contraseña, se asigna el nombre de usuario a la variable y se muestra un mensaje de bienvenida
                JOptionPane.showMessageDialog(null, "WELCOME ");
                encontrado = true;
            } else {
                // Si no se encontró el usuario y contraseña, se muestra un mensaje de error
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Usuario o Contraseña incorrect@\nQueda " + (3 - numeroIntentos)
                                + (((3 - numeroIntentos) > 1) ? " intentos" : " intento"));
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.showAndWait();
            }
            // Se cierra la conexión
            conexion.close();
        } catch (Exception ex) {
            // Si ocurre un error, se muestra un mensaje de error
            System.err.println("Error: " + ex);
        }
        // Se aumenta el número de intentos
        this.numeroIntentos++;
        return encontrado;
    }

    @FXML
    void ingresar(ActionEvent event) throws IOException {
        boolean inicioValido = false;// Variable para saber si se puede iniciar sesión

        // Se valida que no se dejen campos vacios
        if (cajaUsuario.getText().isEmpty() || cajaContrasena.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "No deje campos vacios");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.showAndWait();
        } else {
            // Si no hay campos vacios, se valida que el usuario exista
            inicioValido = true;
        }
        if (inicioValido == true) {
            // Si el usuario existe, se valida que la contraseña sea correcta
            boolean iniciarSesion = inicioSesion(); // Variable para saber si el inicio de sesión fue correcto
            if (iniciarSesion == false && numeroIntentos > 3) {
                // Si el inicio de sesión fue incorrecto y se superaron los 3 intentos, se muestra un mensaje de error y se redirige a la ventana de registro
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "El usuario que ha estado ingresando no existe \nSe redigirá a la ventana de registro");
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.showAndWait();
                registrar(event);// Se redirige a la ventana de registro
            }
            if (iniciarSesion == true) {
                // Si el inicio de sesión fue correcto, se redirige a la ventana de registro de estudiantes
                ControladorRegistro.setUsuario(null); // Esto es para que no exista interferencia en el saludo de la ventana de registro de estudiantes
                Usuario=cajaUsuario.getText().trim(); // Se asigna el usuario a la variable para mostarlo en la ventana de registro de estudiantes
                // Para crear una ventana necesitas un nuevo Stage (Escenario)
                Stage stage = new Stage(); 
                // Cargas el FXML que queres que abra en un Parent
                Parent root = FXMLLoader.load(getClass().getResource("/Ventanas/registroEstudiantes.fxml"));
                // Se declara una Scene y se le asigna el FXML (Una Scene es la ventana)
                Scene scene = new Scene(root);
                // Establecemos la scena en el Stage
                stage.setScene(scene);
                // titulo para la ventana
                stage.setTitle("Registro Estudiantes");
                // El formulario se maximiza y se muestra
                stage.show();
                // Cerramos la ventana anterior de Login.
                Stage old = (Stage) botonIngresar.getScene().getWindow();
                // Se cierra la conexión
                old.close();
            }
        }

    }

    @FXML
    // Método para redirigir a la ventana de registro
    void registrar(ActionEvent e) throws IOException {
        Stage stage = new Stage(); //
        Parent root = FXMLLoader.load(getClass().getResource("/Ventanas/registro.fxml"));//
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Registro");
        stage.show();
        Stage old = (Stage) botonIngresar.getScene().getWindow();
        old.close();
    }

    //getUsuario
    public static String getUsuario(){
        return Usuario;
    }

    public static void setUsuario(String usuario){
        Usuario=usuario;
    }
}
