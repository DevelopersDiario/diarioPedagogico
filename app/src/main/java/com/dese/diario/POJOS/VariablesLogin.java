package com.dese.diario.POJOS;

import java.io.Serializable;

/**
 * Created by TOSHIBA on 22/04/2017.
 */

public class VariablesLogin implements Serializable{

    public  static String idusuario;
    public  static String cuenta;
    public  static String correo;
    public  static String foto;
    public  static String fportada;
    public  static String telefono;


    public static VariablesLogin instance;

    public String getIdusuario() {
        return VariablesLogin.idusuario;
    }
    public String getCuenta() {
        return VariablesLogin.cuenta;
    }
    public String getCorreo() {
        return VariablesLogin.correo;
    }
    public String getTelefono() {
        return VariablesLogin.telefono;
    }
    public String getFoto() {
        return VariablesLogin.foto;
    }

    public static String getFportada() {
        return fportada;
    }

    public static void setFportada(String fportada) {
        VariablesLogin.fportada = fportada;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public void setFoto(String foto) { this.foto = foto;
    }

    public static synchronized VariablesLogin getInstance(){
        if (instance==null){
            instance = new VariablesLogin();
        }
        return instance;
    }

}//Fin clase
