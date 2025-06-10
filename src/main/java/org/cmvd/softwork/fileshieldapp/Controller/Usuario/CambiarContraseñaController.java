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
import javafx.stage.Stage;
import org.cmvd.softwork.fileshieldapp.DTO.SesionActiva;
import org.cmvd.softwork.fileshieldapp.Service.AuthService;
import java.io.IOException;


public class CambiarContraseñaController {
    @FXML
    private PasswordField contrasenaActual;
    @FXML
    private PasswordField nuevaContraseña;
    @FXML
    private Label mensajeExito;
    @FXML
    private Label mensajeError;

    private final AuthService authService = new AuthService();

    @FXML
    private void handleCambiarContraseña() {

        String actual = contrasenaActual.getText();
        String nueva = nuevaContraseña.getText();

        if (actual.isBlank() || nueva.isBlank()) {
            mensajeError.setText("Debe completar todos los campos.");
            return;
        }

        authService.cambiarContrasena(SesionActiva.getToken(), actual, nueva)
                .thenAccept(exito -> Platform.runLater(() -> {
                    if (exito) {
                        mensajeExito.setText("Contraseña actualizada correctamente.");
                        contrasenaActual.clear();
                        nuevaContraseña.clear();
                    } else {
                        mensajeError.setText("Error al cambiar la contraseña.");
                    }
                }))
                .exceptionally(e -> {
                    Platform.runLater(() -> mensajeError.setText("Error en la petición."));
                    return null;
                });
    }

    @FXML
    public void volverAlLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/cmvd/softwork/fileshieldapp/Usuario/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Iniciar Sesión");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
