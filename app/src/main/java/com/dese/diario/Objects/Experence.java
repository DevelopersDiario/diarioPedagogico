package com.dese.diario.Objects;

/**
 * Created by Eduardo on 08/10/2017.
 */

public class Experence {

    private String idpublicacion;
    private String idusuario;
    private String nombre;
    private String foto;
    private String fecha;
    private String titulo;
    private String observaciones;
    private String sentimiento;
    private String evaluacion;
    private String analisis;
    private String conclusion;
    private String planaccion;
    private String padre;
    private String token;
    private String nombregrupo;
    private String parabuscaruser;
    private String cuenta;
    private String idgrupo;

    public Experence() {
    }

    public Experence(String idpublicacion, String idusuario, String nombre, String foto, String fecha,
                     String titulo, String observaciones, String sentimiento, String evaluacion,
                     String analisis, String conclusion, String planaccion, String padre,
                     String token, String nombregrupo, String parabuscaruser, String cuenta, String idgrupo) {
        this.idpublicacion = idpublicacion;
        this.idusuario = idusuario;
        this.nombre = nombre;
        this.foto = foto;
        this.fecha = fecha;
        this.titulo = titulo;
        this.observaciones = observaciones;
        this.sentimiento = sentimiento;
        this.evaluacion = evaluacion;
        this.analisis = analisis;
        this.conclusion = conclusion;
        this.planaccion = planaccion;
        this.padre = padre;//A parirt
        this.token = token;
        this.nombregrupo = nombregrupo;
        this.parabuscaruser = parabuscaruser;
        this.cuenta = cuenta;
        this.idgrupo = idgrupo;
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

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

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

    public String getSentimiento() {
        return sentimiento;
    }

    public void setSentimiento(String sentimiento) {
        this.sentimiento = sentimiento;
    }

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getAnalisis() {
        return analisis;
    }

    public void setAnalisis(String analisis) {
        this.analisis = analisis;
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

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombregrupo() {
        return nombregrupo;
    }

    public void setNombregrupo(String nombregrupo) {
        this.nombregrupo = nombregrupo;
    }

    public String getParabuscaruser() {
        return parabuscaruser;
    }

    public void setParabuscaruser(String parabuscaruser) {
        this.parabuscaruser = parabuscaruser;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getIdgrupo() {
        return idgrupo;
    }

    public void setIdgrupo(String idgrupo) {
        this.idgrupo = idgrupo;
    }


    @Override
    public String toString() {
        return "Experence{" +
                "idpublicacion='" + idpublicacion + '\'' +
                ", idusuario='" + idusuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", foto='" + foto + '\'' +
                ", fecha='" + fecha + '\'' +
                ", titulo='" + titulo + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", sentimiento='" + sentimiento + '\'' +
                ", evaluacion='" + evaluacion + '\'' +
                ", analisis='" + analisis + '\'' +
                ", conclusion='" + conclusion+ '\'' +
                ", planaccion='" + planaccion + '\'' +
                ", padre='" + padre + '\'' +
                ", token='" + token + '\'' +
                ", nombregrupo='" + nombregrupo + '\'' +
                ", parabuscaruser='" + parabuscaruser + '\'' +
                ", cuenta='" + cuenta + '\'' +
                ", idgrupo='" + idgrupo + '\'' +
                '}';
    }
}
