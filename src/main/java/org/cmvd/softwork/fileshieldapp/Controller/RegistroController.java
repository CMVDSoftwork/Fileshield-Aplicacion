package org.cmvd.softwork.fileshieldapp.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.cmvd.softwork.fileshieldapp.Service.AuthService;

public class RegistroController {
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellidoP;
    @FXML private TextField txtApellidoM;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private PasswordField txtConfirmar;

    private final AuthService authService = new AuthService();

    @FXML
    private void handleRegistro() {
        String nombre = txtNombre.getText();
        String apellidoP = txtApellidoP.getText();
        String apellidoM = txtApellidoM.getText();
        String correo = txtCorreo.getText();
        String contrasena = txtContrasena.getText();
        String confirmar = txtConfirmar.getText();

        if (!contrasena.equals(confirmar)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Las contraseñas no coinciden.");
            return;
        }

        boolean exito = authService.registrar(nombre, apellidoP, apellidoM, correo, contrasena);
        if (exito) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso. Ahora puedes iniciar sesión.");
            // TODO: Cambiar de escena a Login
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error al registrar. Intenta nuevamente.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}