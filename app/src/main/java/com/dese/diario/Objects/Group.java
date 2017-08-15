package com.dese.diario.Objects;

/**
 * Created by Eduardo on 14/05/2017.
 */

public class Group {
    private  String nombregrupo;
    private  String idgrupo;
    private  String nombreusuario;
    private  String idrol;
    private String idusuario;
    private  String usuarioalumno;

    public Group(){

    }

    public Group(String nombregrupo, String nombreusuario, String idgrupo, String idrol,String idusuario, String usuarioalumno){
        this.nombregrupo=nombregrupo;
        this.nombreusuario=nombreusuario;
        this.idgrupo=idgrupo;
        this.idrol=idrol;
        this.idusuario=idusuario;
        this.usuarioalumno=usuarioalumno;
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

    public String getIdusuario() {
        return idusuario;
    }

    public String getUsuarioalumno() {
        return usuarioalumno;
    }

    public void setUsuarioalumno(String usuarioalumno) {
        this.usuarioalumno = usuarioalumno;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public void setIdrol(String idrol) {
        this.idrol = idrol;
    }
}
