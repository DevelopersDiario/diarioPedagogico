package com.dese.diario.Objects;

/**
 * Created by TOSHIBA on 07/04/2017.
 */

public class Urls {

    //URL usuario login
    public static final String login =  "http://187.188.168.51:8080/diariopws/api/1.0/user/autenticar";                        //en uso
    public static final String createaccount =  "http://187.188.168.51:8080/diariopws/api/1.0/usuario/insert";                //en uso

    //URLs Seleccionar usurio
    public static final String userslist =  "http://187.188.168.51:8080/diariopws/api/1.0/usuario/list";           //Funcional en server
    public static final String ufilXnombre =  "http://187.188.168.51:8080/diariopws/api/1.0/usuario/filtrousuarioXnombre"; //Funcional en server
    public static final String filtrousuarioXid =  "http://187.188.168.51:8080/diariopws/api/1.0/usuario/filtrousuarioXid";  //Funcional en server
    public static final String fotouser =  "http://187.188.168.51:8080/diariopws/api/1.0/archivos/descargar/";         //en uso  "local"
    public static final String upload =  "http://187.188.168.51:8080/diariopws/api/1.0/usuario/upload/";         //en uso  "local"
    ////actualizacion de informacion de perfil
    public static final String updateestado ="http://187.188.168.51:8080/diariopws/api/1.0/usuario/updateestado";                //en uso
    public static final String updateinfPersonal ="http://187.188.168.51:8080/diariopws/api/1.0/usuario/updateinfPersonal";       //en uso
    public static final String updateinfacademica ="http://187.188.168.51:8080/diariopws/api/1.0/usuario/updateinfacademica";     //en uso
    public static final String updateinfsocial ="http://187.188.168.51:8080/diariopws/api/1.0/usuario/updateinfsocial";           //en uso


    //URLs listar  publicaciones
    public static final String listpublicacion =  "http://187.188.168.51:8080/diariopws/api/1.0/publicacion/list";                //en uso
    public static final String listxiduser =  "http://187.188.168.51:8080/diariopws/api/1.0/publicacion/listxiduser";  //funcional server "Requiere idusuario" como header

    //URLs insertar  publicaciones
    public static final String insertpublicacion =  "http://187.188.168.51:8080/diariopws/api/1.0/publicacion/createpublicacionpadre";   //En reconstruccion


    //URLs Grupo
    public static final String listgrupo ="http://187.188.168.51:8080/diariopws/api/1.0/grupo/list";            //Funcional en server
    public static final String listgpoxid ="http://187.188.168.51:8080/diariopws/api/1.0/grupo/listgpoxid";     //Funcional en server " requiere parametro idgrupo"

    public static final String creategpo ="http://187.188.168.51:8080/diariopws/api/1.0/grupo/create";    //correcion

    //URLs Grupodetalle
    public static final String listgpodetalle ="http://187.188.168.51:8080/diariopws/api/1.0/grupodetalle/list";   //Funcional en server
    public static final String addparticipante ="http://187.188.168.51:8080/diariopws/api/1.0/grupodetalle/addparticipante";   //Funcional en server
    public static final String repuplication ="http://187.188.168.51:8080/diariopws/api/1.0/publicacion/republication";




/**
    //URL usuario login
public static final String login =  "http://192.168.20.25:8084/diariopws/api/1.0/user/autenticar";                        //en uso

/**
**/
/**
    //URL usuario login
    public static final String login =  "http://192.168.20.25:8084/diariopws/api/1.0/user/autenticar";                        //en uso
>>>>>>> republicacion
    public static final String createaccount =  "http://192.168.20.25:8084/diariopws/api/1.0/usuario/insert";                //en uso

    //URLs Seleccionar usurio
    public static final String userslist =  "http://192.168.20.25:8084/diariopws/api/1.0/usuario/list";           //Funcional en server
    public static final String ufilXnombre =  "http://192.168.20.25:8084/diariopws/api/1.0/usuario/filtrousuarioXnombre"; //Funcional en server
    public static final String filtrousuarioXid =  "http://192.168.20.25:8084/diariopws/api/1.0/usuario/filtrousuarioXid";  //Funcional en server
    public static final String fotouser =  "http://192.168.20.25:8084/diariopws/api/1.0/archivos/descargar/";         //en uso  "local"

 public static final String upload =  "http://192.168.20.25:8084/diariopws/api/1.0/usuario/upload//";         //en uso  "local"


    ////actualizacion de informacion de perfil
    public static final String updateestado ="http://192.168.20.25:8084/diariopws/api/1.0/usuario/updateestado";                //en uso
    public static final String updateinfPersonal ="http://192.168.20.25:8084/diariopws/api/1.0/usuario/updateinfPersonal";       //en uso
    public static final String updateinfacademica ="http://192.168.20.25:8084/diariopws/api/1.0/usuario/updateinfacademica";     //en uso
    public static final String updateinfsocial ="http://192.168.20.25:8084/diariopws/api/1.0/usuario/updateinfsocial";           //en uso


    //URLs listar  publicaciones
    public static final String listpublicacion =  "http://192.168.20.25:8084/diariopws/api/1.0/publicacion/list";                //en uso
    public static final String listxiduser =  "http://192.168.20.25:8084/diariopws/api/1.0/publicacion/listxiduser";  ///funcional server "Requiere idusuario" como header
    public static final String listxidgrupo =  "http://192.168.20.25:8084/diariopws/api/1.0/publicacion/listxidgrupo"; //ok

    public static final String repuplication ="http://192.168.20.25:8084/diariopws/api/1.0/publicacion/republication";

    //URLs insertar  publicaciones
    public static final String insertpublicacion =  "http://192.168.20.25:8084/diariopws/api/1.0/publicacion/createpublicacionpadre";   //En reconstruccion


    //URLs Grupo
    public static final String listgrupo ="http://192.168.20.25:8084/diariopws/api/1.0/grupo/list";            //Funcional en server
    public static final String listgpoxid ="http://192.168.20.25:8084/diariopws/api/1.0/grupo/listgpoxid";     //Funcional en server " requiere parametro idgrupo"

    public static final String creategpo ="http://192.168.20.25:8084/diariopws/api/1.0/grupo/create";    //ok

    //URLs Grupodetalle
    public static final String listgpodetalle ="http://192.168.20.25:8084/diariopws/api/1.0/grupodetalle/list";   //Funcional en server
    public static final String addparticipante ="http://192.168.20.25:8084/diariopws/api/1.0/grupodetalle/addparticipante";   //ok

    public static final String republication ="http://192.168.20.25:8084/diariopws/api/1.0/publicacion/republication";
    **/


}

