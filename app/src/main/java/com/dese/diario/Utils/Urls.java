package com.dese.diario.Utils;

/**
 * Created by @Benja, @Dereckz on 07/04/2017.
 */

public class Urls {

/** Server Online **/
    /**
     public static final String login =  "http://201.144.196.51:8080/diariopws/api/1.0/user/autenticar";                        //en uso
     public static final String createaccount =  "http://201.144.196.51:8080/diariopws/api/1.0/usuario/insert";                //en uso

     //URLs Seleccionar usurio
     public static final String userslist =  "http://201.144.196.51:8080/diariopws/api/1.0/usuario/list";           //Funcional en server
     public static final String ufilXnombre =  "http://201.144.196.51:8080/diariopws/api/1.0/usuario/filtrousuarioXnombre"; //Funcional en server
     public static final String filtrousuarioXid =  "http://201.144.196.51:8080/diariopws/api/1.0/usuario/filtrousuarioXid";  //Funcional en server
     public static final String download =  "http://201.144.196.51:8080/diariopws/api/1.0/archivos/descargar/";         //en uso  "local"
     public static final String upload =  "http://201.144.196.51:8080/diariopws/api/1.0/usuario/upload/";
     public static final String uploadholder =  "http://201.144.196.51:8080/diariopws/api/1.0/usuario/uploadfPortada";         //en uso  "local"

     ////actualizacion de informacion de perfil
     public static final String updateestado ="http://201.144.196.51:8080/diariopws/api/1.0/usuario/updateestado";                //en uso
     public static final String updateinfPersonal ="http://201.144.196.51:8080/diariopws/api/1.0/usuario/updateinfPersonal";       //en uso
     public static final String updateinfacademica ="http://201.144.196.51:8080/diariopws/api/1.0/usuario/updateinfacademica";     //en uso
     public static final String updateinfsocial ="http://201.144.196.51:8080/diariopws/api/1.0/usuario/updateinfsocial";           //en uso


     //URLs listar  publicaciones
     //  public static final String listarpublicacionxiduser= "http://201.144.196.51:8080/diariopws/api/1.0/publicacion/listxiduser";

     public static final String listpublicacion =  "http://201.144.196.51:8080/diariopws/api/1.0/publicacion/list";                //en uso
     public static final String listxiduser =  "http://201.144.196.51:8080/diariopws/api/1.0/publicacion/listxiduser";  //funcional server "Requiere idusuario" como header

     //URLs insertar  publicaciones
     public static final String insertpublicacion =  "http://201.144.196.51:8080/diariopws/api/1.0/publicacion/createpublicacionpadre";   //En reconstruccion
     public static final String repuplication ="http://201.144.196.51:8080/diariopws/api/1.0/publicacion/republication";
     public static final String listarrepublication="http:///201.144.196.51:8080/diariopws/api/1.0/publicacion/listrepublication";
     public static final String publicararchivo= "http://201.144.196.51:8080/diariopws/api/1.0/publicacion/publicarArchivo";
     public static final String obtenerdetallepublicacion="http://201.144.196.51:8080/diariopws/api/1.0/publicacion/listdetpublication";

     public static final String updateMyPublication="http://201.144.196.51:8080/diariopws/api/1.0/publicacion/updatepublicacionpadre";
     public static final String updateSentimientos="http://201.144.196.51:8080/diariopws/api/1.0/publicacion/updatesentimiento";
     public static final String updateEvaluacion= "http://201.144.196.51:8080/diariopws/api/1.0/publicacion/updateevaluacion";
     public static final String updateAnalisis= "http://201.144.196.51:8080/diariopws/api/1.0/publicacion/updateanalisis";
     public static final String updateConclusion= "http://201.144.196.51:8080/diariopws/api/1.0/publicacion/updateconclusion";
     public static final String updatePlan="http://201.144.196.51:8080/diariopws/api/1.0/publicacion/updateplanaccion";

     //URLs Grupo
     public static final String listgrupo ="http://201.144.196.51:8080/diariopws/api/1.0/grupo/list";            //Funcional en server
     public static final String listgpoxid ="http://201.144.196.51:8080/diariopws/api/1.0/grupo/listgpoxid";     //Funcional en server " requiere parametro idgrupo"
     public static final String listuserxgpo="http://201.144.196.51:8080/diariopws/api/1.0/grupodetalle/listusersgpo";
     public static final String creategpo ="http://201.144.196.51:8080/diariopws/api/1.0/grupo/create";    //correcion
     public static final String listargpoxpublicacion="http://201.144.196.51:8080/diariopws/api/1.0/grupo/listgpoXppadre";

     //URLs Grupodetalle
     public static final String listgpodetalle ="http://201.144.196.51:8080/diariopws/api/1.0/grupodetalle/list";   //Funcional en server
     public static final String addparticipante ="http://201.144.196.51:8080/diariopws/api/1.0/grupodetalle/addparticipante";   //Funcional en server

**/

    /**   LOCAL       **/

    //URL usuario login
    public static final String login =  "http://192.168.0.105:8080/diariopws/api/1.0/user/autenticar";                        //en uso
    public static final String createaccount =  "http://192.168.0.105:8080/diariopws/api/1.0/usuario/insert";                //en uso

    //URLs Seleccionar usurio
    public static final String userslist =  "http://192.168.0.105:8080/diariopws/api/1.0/usuario/list";           //Funcional en server
    public static final String ufilXnombre =  "http://192.168.0.105:8080/diariopws/api/1.0/usuario/filtrousuarioXnombre"; //Funcional en server
    public static final String filtrousuarioXid =  "http://192.168.0.105:8080/diariopws/api/1.0/usuario/filtrousuarioXid";  //Funcional en server
    public static final String download =  "http://192.168.0.105:8080/diariopws/api/1.0/archivos/descargar/";         //en uso  "local"
    public static final String upload =  "http://192.168.0.105:8080/diariopws/api/1.0/usuario/upload/";
    public static final String uploadholder =  "http://192.168.0.105:8080/diariopws/api/1.0/usuario/uploadfPortada";         //en uso  "local"

    ////actualizacion de informacion de perfil
    public static final String updateestado ="http://192.168.0.105:8080/diariopws/api/1.0/usuario/updateestado";                //en uso
    public static final String updateinfPersonal ="http://192.168.0.105:8080/diariopws/api/1.0/usuario/updateinfPersonal";       //en uso
    public static final String updateinfacademica ="http://192.168.0.105:8080/diariopws/api/1.0/usuario/updateinfacademica";     //en uso
    public static final String updateinfsocial ="http://192.168.0.105:8080/diariopws/api/1.0/usuario/updateinfsocial";           //en uso


    //URLs listar  publicaciones
    //  public static final String listarpublicacionxiduser= "http://192.168.0.105:8080/diariopws/api/1.0/publicacion/listxiduser";

    public static final String listpublicacion =  "http://192.168.0.105:8080/diariopws/api/1.0/publicacion/list";                //en uso
    public static final String listxiduser =  "http://192.168.0.105:8080/diariopws/api/1.0/publicacion/listxiduser";  //funcional server "Requiere idusuario" como header

    //URLs insertar  publicaciones
    public static final String insertpublicacion =  "http://192.168.0.105:8080/diariopws/api/1.0/publicacion/createpublicacionpadre";   //En reconstruccion
    public static final String repuplication ="http://192.168.0.105:8080/diariopws/api/1.0/publicacion/republication";
    public static final String listarrepublication="http:///192.168.0.105:8080/diariopws/api/1.0/publicacion/listrepublication";
    public static final String publicararchivo= "http://192.168.0.105:8080/diariopws/api/1.0/publicacion/publicarArchivo";
    public static final String obtenerdetallepublicacion="http://192.168.0.105:8080/diariopws/api/1.0/publicacion/listdetpublication";

    public static final String updateMyPublication="http://192.168.0.105:8080/diariopws/api/1.0/publicacion/updatepublicacionpadre";
    public static final String updateSentimientos="http://192.168.0.105:8080/diariopws/api/1.0/publicacion/updatesentimiento";
    public static final String updateEvaluacion= "http://192.168.0.105:8080/diariopws/api/1.0/publicacion/updateevaluacion";
    public static final String updateAnalisis= "http://192.168.0.105:8080/diariopws/api/1.0/publicacion/updateanalisis";
    public static final String updateConclusion= "http://192.168.0.105:8080/diariopws/api/1.0/publicacion/updateconclusion";
    public static final String updatePlan="http://192.168.0.105:8080/diariopws/api/1.0/publicacion/updateplanaccion";

    //URLs Grupo
    public static final String listgrupo ="http://192.168.0.105:8080/diariopws/api/1.0/grupo/list";            //Funcional en server
    public static final String listgpoxid ="http://192.168.0.105:8080/diariopws/api/1.0/grupo/listgpoxid";     //Funcional en server " requiere parametro idgrupo"
    public static final String listuserxgpo="http://192.168.0.105:8080/diariopws/api/1.0/grupodetalle/listusersgpo";
    public static final String creategpo ="http://192.168.0.105:8080/diariopws/api/1.0/grupo/create";    //correcion
    public static final String listargpoxpublicacion="http://192.168.0.105:8080/diariopws/api/1.0/grupo/listgpoXppadre";

    //URLs Grupodetalle
    public static final String listgpodetalle ="http://192.168.0.105:8080/diariopws/api/1.0/grupodetalle/list";   //Funcional en server
    public static final String addparticipante ="http://192.168.0.105:8080/diariopws/api/1.0/grupodetalle/addparticipante";   //Funcional en server
}