package com.dese.diario.POJOS;

import com.dese.diario.Objects.Publication;
import com.dese.diario.Profile;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by TOSHIBA on 24/04/2017.
 */

public class DatosUsr implements Serializable{
    public  static    String idusuario;
    public  static   String nombre;
    public  static  String apellidos;
    public  static  String genero;
    public  static   String cuenta;
    public  static   String vigencia;
    public  static   String fnacimiento;
    public  static   String correo;
    public  static   String telefono;
    public  static   String institucion;
    public  static   String grupo;
    public  static   String estado;
    public  static   String foto;

    public static DatosUsr instance;

    public  String getIdusuario() {
        return DatosUsr.idusuario;
    }

    public  String getNombre() {
        return DatosUsr.nombre;
    }

    public  String getApellidos() {
        return DatosUsr.apellidos;
    }

    public  String getGenero() {
        return DatosUsr.genero;
    }

    public  String getCuenta() {
        return DatosUsr.cuenta;
    }

    public  String getVigencia() { return DatosUsr.vigencia;
    }

    public  String getFnacimiento() {
        return DatosUsr.fnacimiento;
    }

    public  String getCorreo() {
        return DatosUsr.correo;
    }

    public  String getTelefono() {
        return DatosUsr.telefono;
    }

    public  String getInstitucion() {
        return DatosUsr.institucion;
    }

    public  String getGrupo() {
        return DatosUsr.grupo;
    }

    public  String getEstado() {    return DatosUsr.estado;  }

    public  String getFoto() {
        return DatosUsr.foto;
    }

    public  void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public  void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public  void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public  void setGenero(String genero) {
        this.genero = genero;
    }

    public  void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public  void setVigencia(String vigencia) { this.vigencia = vigencia;  }

    public  void setFnacimiento(String fnacimiento) {
        this.fnacimiento = fnacimiento;
    }

    public  void setCorreo(String correo) {
        this.correo = correo;
    }

    public  void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public  void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public  void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public  void setEstado(String estado) {     this.estado = estado;   }

    public  void setFoto(String foto) {
        this.foto = foto;
    }

    public  void setInstance(DatosUsr instance) {
        this.instance = instance;
    }

    public static synchronized DatosUsr getInstance(){
        if (instance==null){
            instance = new DatosUsr();
        }
        return instance;
    }

}
