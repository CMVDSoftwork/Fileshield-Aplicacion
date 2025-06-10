package org.cmvd.softwork.fileshieldapp.Controller.Usuario;

import javafx.application.Platform;
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

        if (nombre.isEmpty() || apellidoP.isEmpty() || apellidoM.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Todos los campos son obligatorios.");
            return;
        }

        if (!correo.matches("^\\S+@\\S+\\.\\S+$")) {
            mostrarAlerta(Alert.AlertType.ERROR, "Ingresa un correo válido.");
            return;
        }

        if (!contrasena.equals(confirmar)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Las contraseñas no coinciden.");
            return;
        }

        authService.registrar(nombre, apellidoP, apellidoM, correo, contrasena)
                .thenAccept(exito -> Platform.runLater(() -> {
                    if (exito) {
                        mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso.");
                        limpiarCampos();
                    } else {
                        mostrarAlerta(Alert.AlertType.ERROR, "El correo ya está registrado.");
                    }
                }))
                .exceptionally(e -> {
                    Platform.runLater(() -> mostrarAlerta(Alert.AlertType.ERROR, "Error en el servidor"));
                    return null;
                });
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellidoP.clear();
        txtApellidoM.clear();
        txtCorreo.clear();
        txtContrasena.clear();
        txtConfirmar.clear();
    }
}