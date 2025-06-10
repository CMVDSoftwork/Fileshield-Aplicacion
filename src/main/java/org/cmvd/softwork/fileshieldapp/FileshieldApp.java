package org.cmvd.softwork.fileshieldapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FileshieldApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/cmvd/softwork/fileshieldapp/Usuario/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 420, 240);
        stage.setTitle("Registro de usuario");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}