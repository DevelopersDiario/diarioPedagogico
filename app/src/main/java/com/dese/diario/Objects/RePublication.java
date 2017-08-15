package com.dese.diario.Objects;

/**
 * Created by Eduardo on 04/06/2017.
 */

public class RePublication {

    private String padre;
    private String idpublicacion;
    private String foto;
    private String ruta;
    private String observaciones;
    private String titulo;
    private String nombre;
    private String idusuario;

    public RePublication() {
    }

    public RePublication(String padre, String idpublicacion, String foto, String observaciones, String titulo, String nombre, String idusuario) {
        this.padre = padre;
        this.idusuario = idusuario;
        this.nombre = nombre;
        this.titulo = titulo;
        this.observaciones=observaciones;
      //  this.ruta = ruta;
        this.idpublicacion = idpublicacion;
        this.foto = foto;
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getIdpublicacion() {
        return idpublicacion;
    }

    public void setIdpublicacion(String idpublicacion) {
        this.idpublicacion = idpublicacion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    /*public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }*/

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "RePublication{" +
                "nombre='" + nombre + '\'' +
                ", idpublicacion='" + idpublicacion + '\'' +
                ", foto='" + foto + '\'' +
                ", idusuario='" + idusuario + '\'' +
                ", titulo='" + titulo + '\'' +
                ", padre='" + padre + '\'' +
                '}';
    }
}
