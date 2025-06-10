package org.cmvd.softwork.fileshieldapp.DTO;

import javafx.beans.property.*;

public class CarpetaMonitorizadaDTO {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty ruta = new SimpleStringProperty();
    private final StringProperty estado = new SimpleStringProperty();
    private UsuarioDTO usuarioDTO;

    public CarpetaMonitorizadaDTO() {
    }

    public CarpetaMonitorizadaDTO(CarpetaMonitorizadaSimpleDTO simpleDTO) {
        this.id.set(simpleDTO.getIdCarpetaMonitorizada());
        this.ruta.set(simpleDTO.getRuta());
        this.estado.set(simpleDTO.getEstado());
        this.usuarioDTO = simpleDTO.getUsuarioDTO();
    }

    public CarpetaMonitorizadaSimpleDTO toSimpleDTO() {
        return new CarpetaMonitorizadaSimpleDTO(
                getId(),
                getRuta(),
                getEstado(),
                getUsuarioDTO()
        );
    }

    public Integer getIdAsObject() {
        return idProperty().getValue();
    }

    public IntegerProperty idProperty() { return id; }
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }

    public StringProperty rutaProperty() { return ruta; }
    public String getRuta() { return ruta.get(); }
    public void setRuta(String ruta) { this.ruta.set(ruta); }

    public StringProperty estadoProperty() { return estado; }
    public String getEstado() { return estado.get(); }
    public void setEstado(String estado) { this.estado.set(estado); }

    public UsuarioDTO getUsuarioDTO() { return usuarioDTO; }
    public void setUsuarioDTO(UsuarioDTO usuarioDTO) { this.usuarioDTO = usuarioDTO; }
}
