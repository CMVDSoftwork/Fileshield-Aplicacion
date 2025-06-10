package org.cmvd.softwork.fileshieldapp.Controller.Carpeta;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.DirectoryChooser;
import org.cmvd.softwork.fileshieldapp.DTO.*;
import org.cmvd.softwork.fileshieldapp.Service.CarpetaMonitorizadaService;
import java.io.File;

public class CarpetaMonitorizadaController {
    @FXML
    private Label labelEstado;
    @FXML
    private Button btnIniciarMonitoreo;
    @FXML
    private Button btnEliminarCarpeta;
    @FXML
    private PasswordField inputContrasena;

    private final CarpetaMonitorizadaService carpetaService = new CarpetaMonitorizadaService();
    private String rutaSeleccionada;
    private Long idCarpetaRegistrada;

    @FXML
    private void manejarSeleccionCarpeta() {
        DirectoryChooser selector = new DirectoryChooser();
        selector.setTitle("Selecciona una carpeta para monitorear");

        File carpeta = selector.showDialog(null);

        if (carpeta != null) {
            rutaSeleccionada = carpeta.getAbsolutePath();

            if (!SesionActiva.sesionIniciada()) {
                labelEstado.setText("Error: No hay usuario autenticado. Inicia sesión primero.");
                return;
            }
            UsuarioDTO usuario = SesionActiva.getUsuarioDTO();

            labelEstado.setText("Registrando carpeta: " + rutaSeleccionada);

            CarpetaMonitorizadaSimpleDTO simpleDTO = new CarpetaMonitorizadaSimpleDTO(0, rutaSeleccionada, "ACTIVO", usuario);
            CarpetaMonitorizadaDTO dto = new CarpetaMonitorizadaDTO(simpleDTO);

            carpetaService.registrarCarpeta(dto, exito -> {
                Platform.runLater(() -> {
                    if (exito != null && exito.getIdAsObject() != null) {
                        idCarpetaRegistrada = exito.getIdAsObject().longValue();
                        labelEstado.setText("Carpeta registrada exitosamente:\n" + rutaSeleccionada);
                        btnIniciarMonitoreo.setVisible(true);
                        btnEliminarCarpeta.setVisible(true);
                    } else {
                        labelEstado.setText("Error al registrar la carpeta.");
                    }
                });
            });
        }
    }

    @FXML
    private void iniciarMonitoreo() {
        if (rutaSeleccionada != null && SesionActiva.sesionIniciada()) {
            String contrasena = inputContrasena.getText();

            if (contrasena == null || contrasena.isEmpty()) {
                labelEstado.setText("Por favor ingresa una contraseña antes de iniciar el monitoreo.");
                return;
            }

            carpetaService.iniciarMonitoreo(
                    rutaSeleccionada,
                    contrasena,
                    SesionActiva.getIdUsuario(),
                    exito -> Platform.runLater(() -> {
                        if (exito) {
                            labelEstado.setText("Monitoreo iniciado en:\n" + rutaSeleccionada);
                        } else {
                            labelEstado.setText("Error al iniciar monitoreo.");
                        }
                    })
            );
        }
        else {
            labelEstado.setText("Debe seleccionar una carpeta y estar autenticado.");
        }
    }

    @FXML
    private void eliminarCarpeta() {
        if (idCarpetaRegistrada != null) {
            carpetaService.eliminarCarpeta(idCarpetaRegistrada, () -> {
                Platform.runLater(() -> {
                    labelEstado.setText("Carpeta eliminada.");
                    btnIniciarMonitoreo.setVisible(false);
                    btnEliminarCarpeta.setVisible(false);
                    rutaSeleccionada = null;
                    idCarpetaRegistrada = null;
                });
            });
        }
    }
}
