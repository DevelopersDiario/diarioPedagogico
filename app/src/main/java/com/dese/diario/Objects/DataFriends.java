package com.dese.diario.Objects;

/**
 * Created by Eduardo on 15/05/2017.
 */

public class DataFriends {
    public static String idusuario;
    public static String nombre;
    public static String apellidos;
    public static String cuenta;
    public  static String idrol;
    public static String foto;
    public static String grupotmp;

    public DataFriends() {

    }

    public static String getIdusuario() {
        return idusuario;
    }

    public static void setIdusuario(String idusuario) {
        DataFriends.idusuario = idusuario;
    }

    public static String getCuenta() {
        return cuenta;
    }

    public static void setCuenta(String cuenta) {
        DataFriends.cuenta = cuenta;
    }

    public static String getApellidos() {
        return apellidos;
    }

    public static void setApellidos(String apellidos) {
        DataFriends.apellidos = apellidos;
    }

    public static String getNombre() {
        return nombre;
    }

    public static void setNombre(String nombre) {
        DataFriends.nombre = nombre;
    }

    public static String getFoto() {
        return foto;
    }

    public static void setFoto(String foto) {
        DataFriends.foto = foto;
    }

    public static String getIdrol() {
        return idrol;
    }

    public static void setIdrol(String idrol) {
        DataFriends.idrol = idrol;
    }

    public static String getGrupotmp() {
        return grupotmp;
    }

    public static void setGrupotmp(String grupotmp) {
        DataFriends.grupotmp = grupotmp;
    }

    public DataFriends(String idusuario, String nombre, String apellidos, String cuenta, String idrol, String foto, String grupotmp){//}, String foto) {
        this.idusuario=idusuario;
        this.nombre=nombre;
        this.cuenta=cuenta;
        this.idrol=idrol;
        this.apellidos=apellidos;
        this.foto=foto;
        this.grupotmp=grupotmp;
    }

  }