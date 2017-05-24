package com.dese.diario.Objects;

/**
 * Created by Eduardo on 14/05/2017.
 */

public class Group {
    private  String nombregrupo;
    private  String idgrupo;
    private  String nombreusuario;
    private  String idrol;

    public Group(){

    }

    public Group(String nombregrupo, String nombreusuario, String idgrupo, String idrol){
        this.nombregrupo=nombregrupo;
        this.nombreusuario=nombreusuario;
        this.idgrupo=idgrupo;
        this.idrol=idrol;

    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getIdgrupo() {
        return idgrupo;
    }

    public void setIdgrupo(String idgrupo) {
        this.idgrupo = idgrupo;
    }

    public String getNombregrupo() {
        return nombregrupo;
    }

    public void setNombregrupo(String nombregrupo) {
        this.nombregrupo = nombregrupo;
    }

    public String getIdrol() {
        return idrol;
    }

    public void setIdrol(String idrol) {
        this.idrol = idrol;
    }
}
