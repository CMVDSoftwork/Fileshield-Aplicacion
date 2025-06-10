package org.cmvd.softwork.fileshieldapp.Controller.Usuario;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.cmvd.softwork.fileshieldapp.DTO.SesionActiva;
import org.cmvd.softwork.fileshieldapp.Service.AuthService;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField correoField;
    @FXML
    private PasswordField contrasenaField;
    @FXML
    private Label mensajeError;
    @FXML
    private Label mensajeExito;

    private final AuthService authService = new AuthService();

    @FXML
    public void handleLogin(ActionEvent event) {
        String correo = correoField.getText();
        String contrasena = contrasenaField.getText();

        mensajeError.setText("");
        mensajeExito.setText("");

        if (correo.isEmpty() || contrasena.isEmpty()) {
            mensajeError.setText("Todos los campos son obligatorios");
            return;
        }

        authService.login(correo, contrasena)
                .thenAccept(sesion -> Platform.runLater(() -> {
                    mensajeExito.setText("Bienvenido, " + SesionActiva.getNombre());}))
                .exceptionally(e -> {
                    Platform.runLater(() -> mensajeError.setText("Correo o contraseña incorrectos"));
                    return null;
                });
    }

    @FXML
    public void handleIrACambiarContraseña(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/cmvd/softwork/fileshieldapp/Usuario/cambiarContraseña.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Cambiar Contraseña");
            stage.show();

        } catch (IOException e) {
            mensajeError.setText("Error al abrir la ventana de cambio de contraseña");
            e.printStackTrace();
        }
    }

    @FXML
    public void handleIrACarpetaMonitorizada(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/cmvd/softwork/fileshieldapp/Carpeta/carpetaMonitorizada.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Carpeta Monitorizada");
            stage.show();

        } catch (IOException e) {
            mensajeError.setText("Error al abrir la ventana de Carpeta Monitorizada");
            e.printStackTrace();
        }
    }
}
