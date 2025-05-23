package org.cmvd.softwork.fileshieldapp.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.cmvd.softwork.fileshieldapp.DTO.SesionUsuario;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class LoginController {
    @FXML
    private TextField correoField;
    @FXML
    private PasswordField contrasenaField;
    @FXML
    private Label mensajeError;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @FXML
    public void handleLogin(ActionEvent event) {
        String correo = correoField.getText();
        String contrasena = contrasenaField.getText();

        if (correo.isEmpty() || contrasena.isEmpty()) {
            mensajeError.setText("Todos los campos son obligatorios");
            return;
        }

        try {
            String jsonBody = String.format("{\"correo\": \"%s\", \"contrasena\": \"%s\"}", correo, contrasena);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/auth/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                    .build();

            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenAccept(responseBody -> {
                        Platform.runLater(() -> {
                            try {
                                ObjectMapper mapper = new ObjectMapper();
                                LoginResponse response = mapper.readValue(responseBody, LoginResponse.class);

                                System.out.println("Token: " + response.token);
                                System.out.println("Usuario: " + response.nombre);
                                System.out.println("Correo: " + response.correo);

                                SesionUsuario.token = response.token;
                                SesionUsuario.nombre = response.nombre;
                                SesionUsuario.correo = response.correo;

                                mensajeError.setText("Inicio de sesión exitoso");



                            } catch (Exception e) {
                                mensajeError.setText("Error al procesar respuesta.");
                            }
                        });
                    })
                    .exceptionally(e -> {
                        Platform.runLater(() -> mensajeError.setText("Correo o contraseña incorrectos"));
                        return null;
                    });

        } catch (Exception e) {
            mensajeError.setText("Error al intentar iniciar sesión");
        }
    }

    public static class LoginResponse {
        public String token;
        public String tipoToken;
        public String correo;
        public String nombre;
    }


}
