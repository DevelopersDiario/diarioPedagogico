package com.dese.diario.Objects;

/**
 * Created by Eduardo on 04/06/2017.
 */

public class RePublication {

    private String padre;
    private String idpublicacion;
    private String foto;
    private String observaciones;
    private String titulo;
    private String nombre;
    private String idusuario;
    private String analisis;
    private String evaluacion;
    private String token;
    private String conclusion;
    private String planaccion;
    private String sentimiento;

    public RePublication(String padre, String idpublicacion, String foto,
                         String observaciones, String titulo, String nombre, String idusuario, String analisis,
                         String evaluacion, String token, String conclusion, String planaccion, String sentimiento) {
        this.padre = padre;
        this.idpublicacion = idpublicacion;
        this.foto = foto;
        this.observaciones = observaciones;
        this.titulo = titulo;
        this.nombre = nombre;
        this.idusuario = idusuario;
        this.analisis = analisis;
        this.evaluacion = evaluacion;
        this.token = token;
        this.conclusion = conclusion;
        this.planaccion = planaccion;
        this.sentimiento = sentimiento;
    }

    public String getSentimiento() {
        return sentimiento;
    }

    public void setSentimiento(String sentimiento) {
        this.sentimiento = sentimiento;
    }

    public String getAnalisis() {
        return analisis;
    }

    public void setAnalisis(String analisis) {
        this.analisis = analisis;
    }

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getPlanaccion() {
        return planaccion;
    }

    public void setPlanaccion(String planaccion) {
        this.planaccion = planaccion;
    }

    public RePublication() {

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
