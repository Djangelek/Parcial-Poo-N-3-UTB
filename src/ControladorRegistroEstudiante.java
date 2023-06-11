import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import Conexion.Connect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControladorRegistroEstudiante {
    PreparedStatement ps;
    ResultSet rs;
    @FXML
    private TextArea areaResultados;

    @FXML
    private Button botonAgregar;

    @FXML
    private Button botonLimpiar;

    @FXML
    private Button botonMostrar;

    @FXML
    private Button botonSalir;

    @FXML
    private TextField cajaFisica;

    @FXML
    private TextField cajaGenero;

    @FXML
    private TextField cajaID;

    @FXML
    private TextField cajaInformatica;

    @FXML
    private TextField cajaNombre;

    @FXML
    private TextField cajaQuimica;

    @FXML
    private Text Saludo;

    @FXML
    void limpiar(ActionEvent event) {
        // Limpiar cajas de texto
        cajaGenero.setText("");
        cajaNombre.setText("");
        cajaID.setText("");
        cajaFisica.setText("");
        cajaInformatica.setText("");
        cajaQuimica.setText("");
        areaResultados.setText("");
    }

    @FXML
    void EnterCajas(KeyEvent event) throws IOException {
        // Si se presiona la tecla ENTER, se ejecuta el método agregar
        if (event.getCode() == KeyCode.ENTER) {
            agregar(null);
    }
    } 

    @FXML
    void initialize() {
        // Se muestra el saludo al usuario que inició sesión o se registró correctamente
        if(ControladorInicioSesion.getUsuario()!=null)
            Saludo.setText("Hola, "+ControladorInicioSesion.getUsuario());
        if(ControladorRegistro.getUsuario()!=null){
            Saludo.setText("Hola, "+ControladorRegistro.getUsuario());
        }
    }

    @FXML
    void agregar(ActionEvent event) {
        if(cajaID.getText().trim().isEmpty() || cajaNombre.getText().trim().isEmpty() || cajaGenero.getText().trim().isEmpty() || cajaFisica.getText().trim().isEmpty() || cajaInformatica.getText().trim().isEmpty() || cajaQuimica.getText().trim().isEmpty()){
            // Si hay campos vacíos, se muestra un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR, "Debe llenar todos los campos");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.showAndWait();
        }
        else if(!cajaID.getText().trim().matches("\\d+(\\.\\d+)?")){
            // Si el ID no es un número, se muestra un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR, "En el ID deben ir solo numeros");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.showAndWait();
        }
        else if(cajaID.getLength()>3){
            // Si el ID tiene más de 3 dígitos, se muestra un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR, "El ID debe contener máximo de 3 digitos");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.showAndWait();
        }
        else if(!cajaGenero.getText().matches("[FMfm]")){
            // Si el género no es F o M, se muestra un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR, "El genero debe ser F o M");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.showAndWait();
        }
        else if(!cajaFisica.getText().matches("\\d+(\\.\\d+)?") || !cajaInformatica.getText().matches("\\d+(\\.\\d+)?") || !cajaQuimica.getText().matches("\\d+(\\.\\d+)?")){
            // Si las notas no son números, se muestra un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR, "Las notas deben ser números (0-100)");
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.showAndWait();
        }
        else if (Float.parseFloat(cajaFisica.getText()) > 100 | Float.parseFloat(cajaInformatica.getText()) > 100 | Float.parseFloat(cajaQuimica.getText()) > 100 | Float.parseFloat(cajaFisica.getText())<0 | Float.parseFloat(cajaInformatica.getText())<0 | Float.parseFloat(cajaQuimica.getText())<0  ) {
                // Si las notas no están entre 0 y 100, se muestra un mensaje de error
                Alert alert = new Alert(Alert.AlertType.ERROR, "la calificacion debe estar entre 0 y 100");
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.showAndWait();
                }
        else{
            // Si no hay errores, se agrega el estudiante a la base de datos
        Connection conexion = null; // establecemos la conexion
        Connect onConnect = new Connect(); // creamos un objeto de la clase Connect
        try {
            conexion = onConnect.connect(); // llamamos al metodo connect de la clase Connect
            ps = conexion.prepareStatement(
            "INSERT INTO estudiantes (id,nombre,genero,nota_fisica,nota_informatica,nota_quimica) VALUES (?,?,?,?,?,?)");// preparamos la insercion de datos
            ps.setInt(1, Integer.parseInt(cajaID.getText())); // obtenemos el valor de la caja de texto y lo convertimos a entero y lo introducimos en la posicion 1 del insert
            ps.setString(2, cajaNombre.getText().trim()); // obtenemos el valor de la caja de texto y lo convertimos a String y lo introducimos en la posicion 2 del insert
            ps.setString(3, cajaGenero.getText().trim()); // obtenemos el valor de la caja de texto y lo convertimos a String y lo introducimos en la posicion 3 del insert
            ps.setDouble(4, Math.round(Double.parseDouble(cajaFisica.getText()))); // obtenemos el valor de la caja de texto y lo convertimos a Double, lo redondeamos y lo introducimos en la posicion 4 del insert
            ps.setDouble(5, Math.round(Double.parseDouble(cajaInformatica.getText()))); // obtenemos el valor de la caja de texto y lo convertimos a Double, lo redondeamos y lo introducimos en la posicion 5 del insert
            ps.setDouble(6, Math.round(Double.parseDouble(cajaQuimica.getText()))); // obtenemos el valor de la caja de texto y lo convertimos a Double, lo redondeamos y lo introducimos en la posicion 6 del insert

            int resultado = ps.executeUpdate(); // ejecutamos la insercion de la base de datos
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Estudiante agregado correctamente"); // si se agrega correctamente, se muestra un mensaje
            } else {
                JOptionPane.showMessageDialog(null, "Error al insertar el Registro"); // si no se agrega correctamente, se muestra un mensaje de error
            }
            conexion.close();
        } catch (SQLException ex) {
            //Si el ID ya existe, se muestra un mensaje de error
            if(ex.getMessage().equals("[SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed: estudiantes.id)")){
                Alert alert = new Alert(Alert.AlertType.ERROR, "El ID ya existe");
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.showAndWait();
            }
            else{
                //Si hay otro error, se muestra un mensaje de error
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error al insertar el Registro: "+ex.getMessage());
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.showAndWait();
            }
        }
    }
    }

    @FXML
    void mostrar(ActionEvent event) {
        Connection conexion = null; // establecemos la conexion
        Connect onConnect = new Connect();// creamos un objeto de la clase Connect
        try {
            conexion = onConnect.connect(); // llamamos al metodo connect de la clase Connect
            ps = conexion.prepareStatement("SELECT * FROM estudiantes");// preparamos la consulta
            rs = ps.executeQuery();// ejecutamos la consulta
            float sumaNotas = 0; 
            float sumaNotasQuim = 0;
            float sumaNotasInfor = 0;
            int notasExcelentesInformatica = 0;
            int notasRegularesFisica = 0;
            int notasDeficientesQuimica = 0;
            int NumeroDeEstudiantes = 0;
            while (rs.next()) {
                //Mientras rs tenga datos, se suman las notas y se cuentan las notas excelentes, regulares y deficientes
                sumaNotas += rs.getFloat("nota_fisica");
                sumaNotasQuim += rs.getFloat("nota_quimica");
                sumaNotasInfor += rs.getFloat("nota_informatica");
                notasExcelentesInformatica += (rs.getFloat("nota_informatica") > 90 && rs.getFloat("nota_informatica") <= 100) ? 1 : 0;
                notasRegularesFisica += (rs.getFloat("nota_fisica") > 60 && rs.getFloat("nota_fisica") <= 80) ? 1 : 0;
                notasDeficientesQuimica += (rs.getFloat("nota_quimica") > 0 && rs.getFloat("nota_quimica") <= 30) ? 1: 0;
                NumeroDeEstudiantes++;
            }
            areaResultados.setText("El promedio de Informatica es " + Math.round(sumaNotasInfor / NumeroDeEstudiantes* 100.0) /100.0
                            + "\nEl promedio de Fisica es " + Math.round(sumaNotas / NumeroDeEstudiantes * 100.0) / 100.0
                            + "\nEl promedio de Quimica es " + Math.round(sumaNotasQuim / NumeroDeEstudiantes * 100.0) / 100.0
                            + "\n\nPorcentaje notas excelentes en informatica: "
                            + notasExcelentesInformatica * 100 / NumeroDeEstudiantes + "%"
                            + "\nPorcentaje notas regulares en fisica: " + notasRegularesFisica * 100 / NumeroDeEstudiantes + "%"
                            + "\nPorcentaje notas deficientes en quimica: " + notasDeficientesQuimica * 100 / NumeroDeEstudiantes
                            + "%");
            conexion.close(); // cerramos la conexion
        } catch (Exception ex) {
            //Si hay un error, se muestra un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al mostrar los datos: "+ex.getMessage());
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }

    @FXML
    void salir(ActionEvent event) throws IOException {
        //Se cierra la ventana actual y se abre la ventana de inicio de sesion
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/Ventanas/inicioSesion.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inicio Sesion");
        stage.show();
        Stage old = (Stage) botonSalir.getScene().getWindow();
        old.close();
    }
}