package com.dese.diario.Objects;

/**
 * Created by Eduardo on 20/03/2017.
 */

public class Usuario {

    private int idusuario;
    private String _nombre;
    private  String _apellido;
    private  String _cuenta;
    private  String _password;
    private  String _vigencia;
    private String _correo;
    private String _telefono;
    private  String _institucion;
    private String _grupo;
    private String  _foto;


    public  Usuario (){

    }
    public Usuario(String nombre, String apellido, String cuenta, String password, String vigencia,

                   String correo, String telefono, String institucion,String grupo, String foto){
        this._nombre = nombre;
        this._apellido = apellido;
        this._cuenta = cuenta;
        this._password= password;
        this._vigencia= vigencia;
        this._correo=correo;
        this._telefono = telefono;
        this._institucion= institucion;
        this._grupo= grupo;
        this._foto=foto;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String get_apellido() {
        return _apellido;
    }

    public void set_apellido(String _apellido) {
        this._apellido = _apellido;
    }

    public String get_cuenta() {
        return _cuenta;
    }

    public void set_cuenta(String _cuenta) {
        this._cuenta = _cuenta;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_vigencia() {
        return _vigencia;
    }

    public void set_vigencia(String _vigencia) {
        this._vigencia = _vigencia;
    }

    public String get_correo() {
        return _correo;
    }

    public void set_correo(String _correo) {
        this._correo = _correo;
    }

    public String get_telefono() {
        return _telefono;
    }

    public void set_telefono(String _telefono) {
        this._telefono = _telefono;
    }

    public String get_institucion() {
        return _institucion;
    }

    public void set_institucion(String _institucion) {
        this._institucion = _institucion;
    }

    public String get_grupo() {
        return _grupo;
    }

    public void set_grupo(String _grupo) {
        this._grupo = _grupo;
    }

    public String get_foto() {
        return _foto;
    }

    public void set_foto(String _foto) {
        this._foto = _foto;
    }
}

