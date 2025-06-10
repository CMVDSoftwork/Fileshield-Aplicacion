module org.cmvd.softwork.fileshieldapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens org.cmvd.softwork.fileshieldapp to javafx.fxml;
    exports org.cmvd.softwork.fileshieldapp;
    exports org.cmvd.softwork.fileshieldapp.DTO;
    opens org.cmvd.softwork.fileshieldapp.DTO to javafx.fxml;
    exports org.cmvd.softwork.fileshieldapp.Controller.Usuario;
    opens org.cmvd.softwork.fileshieldapp.Controller.Usuario to javafx.fxml;
    exports org.cmvd.softwork.fileshieldapp.Controller.Carpeta;
    opens org.cmvd.softwork.fileshieldapp.Controller.Carpeta to javafx.fxml;
}