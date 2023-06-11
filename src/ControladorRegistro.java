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

public class ControladorRegistro {
    PreparedStatement ps;
    ResultSet rs;
    @FXML
    private Button botonRegistrar;

    @FXML
    private Button botonLimpiar;

    @FXML
    private Button botonIniciarSesion;

    @FXML
    private PasswordField cajaContrasena;

    @FXML
    private TextField cajaUsuario;

    @FXML
    private PasswordField cajaConfirmarContrasena;

    private static String Usuario;
    private String Contrasenia;
    private String ConfirmarContrasenia;

    @FXML
    void limpiar(ActionEvent event) {
        // Limpiar cajas de texto
        cajaContrasena.setText("");
        cajaConfirmarContrasena.setText("");
        cajaUsuario.setText("");
    }
    @FXML
    void EnterCajas(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
        // Si se presiona la tecla ENTER, se ejecuta el método ingresar
        registro();
    }
    } 

    @FXML
    private void registro() {
        ControladorInicioSesion.setUsuario(null); //Esto evita que en el saludo de ControlRegistroEstudiantes aparezca el usuario anterior 

        Usuario = cajaUsuario.getText().trim();
        Contrasenia = cajaContrasena.getText().trim();
        ConfirmarContrasenia = cajaConfirmarContrasena.getText().trim();

        if (Contrasenia.isEmpty() | ConfirmarContrasenia.isEmpty() |Usuario.isEmpty()) {
            // Si hay campos vacios, se muestra un mensaje de error
            Alert alertEmpty = new Alert(Alert.AlertType.ERROR, "No deje campos vacios");
            alertEmpty.setHeaderText(null);
            alertEmpty.setTitle("Error");
            alertEmpty.showAndWait();
        } 
        else if (cajaUsuario.getLength() > 20) {
            // Si el usuario tiene mas de 20 caracteres, se muestra un mensaje de error
            Alert alertEmpty = new Alert(Alert.AlertType.ERROR, "El usuario no debe exceder de 20 caracteres");
            alertEmpty.setHeaderText(null);
            alertEmpty.setTitle("Error");
            alertEmpty.showAndWait();
        }
        else if (!Contrasenia.equals(ConfirmarContrasenia)) {
            // Si las contraseñas no coinciden, se muestra un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR, "Las contraseñas no coinciden");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.showAndWait();
            } 
        else {try {
            // Si no hay errores, se procede a insertar el registro en la base de datos
            Connection conexion = null; // creamos la conexion
            Connect onConnect = new Connect(); // conexion
            conexion = onConnect.connect(); // establecemos la conexion
            ps = conexion.prepareStatement(
                "INSERT INTO usuarios (usuario,contrasena) VALUES (?,?)");
            ps.setString(1,Usuario); // ingresamos el usuario
            ps.setString(2, Contrasenia); // ingresamos la contraseña
            int resultado = ps.executeUpdate(); // ejecutamos la insercion de la base de datos

            if (resultado > 0) {
                // Si se inserta correctamente, se muestra un mensaje de confirmacion
                JOptionPane.showMessageDialog(null, "Registro insertado correctamente");
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/Ventanas/registroEstudiantes.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Registro Estudiantes");
                stage.show();
                Stage old = (Stage) botonRegistrar.getScene().getWindow();
                old.close();
            } else {
                // Si no se inserta correctamente, se muestra un mensaje de error
                JOptionPane.showMessageDialog(null, "Error al insertar el Registro");
            }
            // cerramos la conexion
            conexion.close();
        } catch (Exception ex) {
            // Si hay un error, se muestra un mensaje de error
            System.err.println("Error: " + ex);
            if (ex.getMessage().equals("[SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: usuarios.usuario)")) {
                // Si el usuario ya existe, se muestra un mensaje de error
                Alert alertEmpty = new Alert(Alert.AlertType.ERROR, "Este usuario ya existe");
                alertEmpty.setHeaderText(null);
                alertEmpty.setTitle("Error");
                alertEmpty.showAndWait();
            }
            else{
                // Si hay un error desconocido, se muestra un mensaje de error
                Alert alertEmpty = new Alert(Alert.AlertType.ERROR, "Error desconocido: "+ex.getMessage());
                alertEmpty.setHeaderText(null);
                alertEmpty.setTitle("Error");
                alertEmpty.showAndWait();
            }
        }
    }
    }   

    @FXML
    void VolverInicioSesion(ActionEvent event) throws IOException {
        // Volver a la ventana de inicio de sesion
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/Ventanas/inicioSesion.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inicio Sesion");
        stage.show();
        Stage old = (Stage) botonIniciarSesion.getScene().getWindow();
        old.close();
    }

    public static String getUsuario() {
        return Usuario;
    }

    public static void setUsuario(String usuario) {
        Usuario = usuario;
    }
}
