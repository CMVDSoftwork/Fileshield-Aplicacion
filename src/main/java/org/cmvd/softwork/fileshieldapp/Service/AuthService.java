package org.cmvd.softwork.fileshieldapp.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class AuthService {
    private final HttpClient client = HttpClient.newHttpClient();

    public boolean registrar(String nombre,String apellidoP, String apellidoM, String correo, String contrasena) {
        String bodyJson = String.format("{\"nombre\": \"%s\",\"apellido paterno\": \"%s\", \"apellido materno\": \"%s\" ,\"correo\": \"%s\", \"contrasena\": \"%s\"}",
                nombre, apellidoP, apellidoM, correo, contrasena);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/auth/registro"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(bodyJson, StandardCharsets.UTF_8))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
