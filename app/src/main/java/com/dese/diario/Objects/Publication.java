package com.dese.diario.Objects;

/**
 * Created by Eduardo on 04/04/2017.
 */
public class Publication {
    // idpublicacion='12', idusuario='benjamin', nombre='holaa', foto='hola como estan', fecha='', titulo='1', observaciones='1', padre='2017-03-17 00:00:00.0'
    private String idpublicacion;
    private String idusuario;
    private String nombre;
    private String foto;
    private String fecha;
    private String titulo;
    private String observaciones;
    private String padre;

    public Publication(){

    }

    public Publication(String idpublicacion, String idusuario, String nombre, String foto, String fecha, String titulo, String observaciones, String padre) {
        this.idpublicacion = idpublicacion;
        this.idusuario = idusuario;
        this.nombre = nombre;
        this.foto = foto;
        this.fecha = fecha;
        this.titulo = titulo;
        this.observaciones = observaciones;
        this.padre = padre;
    }
    public String getIdpublicacion() {
        return idpublicacion;
    }

    public void setIdpublicacion(String idpublicacion) {
        this.idpublicacion = idpublicacion;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) { this.idusuario = idusuario; }
    public String getNombre() {return nombre;}
    public String getFoto() { return foto;}

    public void setNombre(String nombre) { this.nombre = nombre;}

    public void setFoto(String foto) { this.foto = foto; }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "idpublicacion='" + idpublicacion + '\'' +
                ", idusuario='" + idusuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", foto='" + foto + '\'' +
                ", fecha='" + fecha + '\'' +
                ", titulo='" + titulo + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", padre='" + padre + '\'' +
                '}';
    }
}
