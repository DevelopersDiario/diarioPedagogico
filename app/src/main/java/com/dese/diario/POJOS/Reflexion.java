package com.dese.diario.POJOS;

import java.io.Serializable;

/**
 * Created by Eduardo on 25/08/2017.
 */

public class Reflexion implements Serializable {
    private String idpublicacion;
    private String idusuario;
    private String fecha;
    private String sentimiento;
    private String evaluación;
    private String analisis;
    private String conclusion;
    private String planaccion;
    private String titulo;
    private String observaciones;
    private String padre;

    public static Reflexion instance;

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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSentimiento() {
        return sentimiento;
    }

    public void setSentimiento(String sentimiento) {
        this.sentimiento = sentimiento;
    }

    public String getEvaluación() {
        return evaluación;
    }

    public void setEvaluación(String evaluación) {
        this.evaluación = evaluación;
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

    public static synchronized Reflexion getInstance(){
        if (instance==null){
            instance = new Reflexion();
        }
        return instance;
    }
}
