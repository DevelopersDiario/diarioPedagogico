package com.dese.diario.Utils.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class AdminBD extends SQLiteOpenHelper {

    /* -------USUARIO----------- */
    public static final String TABLE_USER = "usuario";
    public static final String COLUMN_ID = "idusuario";
    public static final String COLUMN_NAME = "nombre";
    public static final String COLUMN_LASTNAME = "apellido";
    public static final String COLUMN_COUNT = "cuenta";
    public static final String COLUMN_PASS = "password";
    public static final String COLUMN_VALIDITY = "vigencia";
    public static final String COLUMN_MAIL = "correo";
    public static final String COLUMN_PHONE = "telefono";
    public static final String COLUMN_INSTIT = "institucion";
    public static final String COLUMN_GROUP = "grupo";
    public static final String COLUMN_IMAGE = "foto";

    /* ---------ROL--------- */
    public static final String TABLE_ROLE = "rol";
    public static final String COLUMN_ID_R = "idrol";
    public static final String COLUMN_ROLE = "rol";

    /* -------USUARIOROL----------- */
    public static final String TABLE_USERROLE = "usuariorol";
    public static final String COLUMN_ID_R_UR = "idrol";
    public static final String COLUMN_ID_U_UR = "idusuario";
    public static final String COLUMN_STATUS = "estatus";

    /* -------GRUPO----------- */
    public static final String TABLE_GROUP = "grupo";
    public static final String COLUMN_ID_G = "idgrupo";
    public static final String COLUMN_NAME_G = "nombregrupo";
    public static final String COLUMN_DATE_G = "fecha";
    public static final String COLUMN_USERALUM = "usuarioalumno";
    public static final String COLUMN_VALUNTIL = "vigenciahasta";

    /* -------GRUPODETALLE----------- */
    public static final String TABLE_GROUPDETAIL = "grupodetalle";
    public static final String COLUMN_ID_GD = "idgrupodetalle";
    public static final String COLUMN_ID_G_GD = "idgrupo";
    public static final String COLUMN_ID_U_GD = "idusuario";

    /* -------PUBLICACION----------- */
    public static final String TABLE_PUBLICATION = "publicacion";
    public static final String COLUMN_ID_P = "idpublicacion";
    public static final String COLUMN_ID_U_P = "idusuario";
    public static final String COLUMN_DATE_P = "fecha";
    public static final String COLUMN_TITLE = "titulo";
    public static final String COLUMN_OBSERVATIONS = "observaciones";
    public static final String COLUMN_FATHER = "padre";

    /* -------DETALLEPUBLICACION----------- */
    public static final String TABLE_PUBDETAIL = "detallepublicacion";
    public static final String COLUMN_ID_PD = "iddetallepublicacion";
    public static final String COLUMN_ID_P_PD = "idpublicacion";
    public static final String COLUMN_ROUTE = "ruta";
    public static final String COLUMN_TYPE = "tipo";
    public static final String COLUMN_FORMAT = "formato";
    public static final String COLUMN_DESCRIP = "descripcion";

    /* -------GRUPOPUBLICACION----------- */
    public static final String TABLE_GP = "grupopublicacion";
    public static final String COLUMN_ID_GP = "idgrupopublicacion";
    public static final String COLUMN_ID_P_GP = "idpublicacion";
    public static final String COLUMN_ID_G_GP = "idgrupo";


    String sqlUser = "CREATE TABLE " + TABLE_USER + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_LASTNAME + " TEXT, " +
            COLUMN_COUNT + " TEXT, " +
            COLUMN_PASS + " TEXT, " +
            COLUMN_VALIDITY + " TEXT, " +
            COLUMN_MAIL + " TEXT, " +
            COLUMN_PHONE + " TEXT, " +
            COLUMN_INSTIT + " TEXT, " +
            COLUMN_GROUP + " TEXT, " +
            COLUMN_IMAGE + " TEXT);";

    String sqlRole = "CREATE TABLE " + TABLE_ROLE + "(" +
            COLUMN_ID_R + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ROLE + " TEXT);";

    String sqlUserRol = "CREATE TABLE " + TABLE_USERROLE + "(" +
            COLUMN_ID_R_UR + " INTEGER FOREING KEY, " +
            COLUMN_ID_U_UR + " INTEGER FOREING KEY, " +
            COLUMN_STATUS + " TEXT);";

    String sqlGroup = "CREATE TABLE " + TABLE_GROUP + "(" +
            COLUMN_ID_G + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME_G + " TEXT, " +
            COLUMN_USERALUM + " INTEGER, " +
            COLUMN_DATE_G + " DATE, " +
            COLUMN_VALUNTIL + " DATE);";

    String sqlGroupDetail = "CREATE TABLE " + TABLE_GROUPDETAIL + "(" +
            COLUMN_ID_GD + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ID_G_GD + " INTEGER FOREING KEY, " +
            COLUMN_ID_U_GD + " INTEGER FOREING KEY);";

    String sqlPublication = "CREATE TABLE " + TABLE_PUBLICATION + "(" +
            COLUMN_ID_P + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ID_U_P + " INTEGER FOREING KEY, " +
            COLUMN_DATE_P + " DATETIME, " +
            COLUMN_TITLE + " TEXT," +
            COLUMN_OBSERVATIONS + " TEXT," +
            COLUMN_FATHER + " INTEGER);";

    String sqlPublicationDetail = "CREATE TABLE " + TABLE_PUBDETAIL + "(" +
            COLUMN_ID_PD + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ID_P_PD + " INTEGER FOREING KEY, " +
            COLUMN_ROUTE + " TEXT, " +
            COLUMN_TYPE + " TEXT," +
            COLUMN_FORMAT + " TEXT," +
            COLUMN_DESCRIP + " TEXT);";

    String sqlGroupPublication = "CREATE TABLE " + TABLE_GP + "(" +
            COLUMN_ID_GP + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ID_P_GP + " INTEGER FOREING KEY, " +
            COLUMN_ID_G_GP + " INTEGER FOREING KEY);";

     /*___________________________________________________*/

    public AdminBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlUser);
        db.execSQL(sqlRole);
        db.execSQL(sqlUserRol);
        db.execSQL(sqlGroup);
        db.execSQL(sqlGroupDetail);
        db.execSQL(sqlPublication);
        db.execSQL(sqlPublicationDetail);
        db.execSQL(sqlGroupPublication);
    }

    //----------------------ALTA GRUPO-----------------//

    public void AltaG(String NombreGrupo, String Fecha, String Usuarioalumno, String Vigencia) {
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues registro = new ContentValues();


        registro.put(COLUMN_NAME_G, NombreGrupo);
        registro.put(COLUMN_DATE_G, Fecha);
        registro.put(COLUMN_USERALUM, Usuarioalumno);
        registro.put(COLUMN_VALUNTIL, String.valueOf(Vigencia));

        bd.insert(TABLE_GROUP, null, registro);
    }

    public ArrayList<String> Listar() {
        ArrayList datos = new ArrayList();

        Cursor cursor;
        SQLiteDatabase bd = this.getReadableDatabase();
        cursor = bd.rawQuery("Select * from " + TABLE_GROUP, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int idg = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_G));
                    String nombregrupo = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_G));
                    String usuarioalumno = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_G));
                    String fecha = cursor.getString(cursor.getColumnIndex(COLUMN_USERALUM));
                    String vigencia = cursor.getString(cursor.getColumnIndex(COLUMN_VALUNTIL));


                    ArrayList elemento = new ArrayList();

                    elemento.add(idg);
                    elemento.add(nombregrupo);
                    elemento.add(usuarioalumno);
                    elemento.add(fecha);
                    elemento.add(vigencia);

                    datos.add(elemento);


                } while (cursor.moveToNext());
            }
        }


        return datos;
    }

    public void AltaDG(String idGrupo, String idUsuario) {
        SQLiteDatabase bd = this.getWritableDatabase();
        ContentValues registrodg = new ContentValues();

        registrodg.put(COLUMN_ID_G, idGrupo);
        registrodg.put(COLUMN_ID, idUsuario);

        bd.insert(TABLE_GROUPDETAIL, null, registrodg);
    }

    public ArrayList<String> ListarDG() {
        ArrayList datos = new ArrayList();

        Cursor cursor;
        SQLiteDatabase bd = this.getReadableDatabase();
        cursor = bd.rawQuery("Select * from " + TABLE_GROUPDETAIL, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int iddg = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_GD));
                    String idg= cursor.getString(cursor.getColumnIndex(COLUMN_ID_G));
                    String idu = cursor.getString(cursor.getColumnIndex(COLUMN_ID));


                    ArrayList elemento = new ArrayList();

                    elemento.add(iddg);
                    elemento.add(idg);
                    elemento.add(idu);

                    datos.add(elemento);


                } while (cursor.moveToNext());
            }
        }


        return datos;
    }

    public void EliminarG(int _id) {

        SQLiteDatabase bd = this.getWritableDatabase();


        bd.delete(TABLE_GROUP, COLUMN_ID_G + "=" + _id, null); //
        bd.close();
    }

    public ArrayList BuscarG(int _id) {
        SQLiteDatabase bd = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_GROUP + " WHERE " + COLUMN_ID + " = " + _id + ";";
        ArrayList datos = new ArrayList();
        Cursor c = bd.rawQuery(query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                int ide = c.getInt(c.getColumnIndex(COLUMN_ID_G));
                String nomgru = c.getString(c.getColumnIndex(COLUMN_NAME_G));
                String usugru = c.getString(c.getColumnIndex(COLUMN_USERALUM));
                String fecha = c.getString(c.getColumnIndex(COLUMN_DATE_G));
                String vige = c.getString(c.getColumnIndex(COLUMN_VALUNTIL));
                datos.add(ide);
                datos.add(nomgru);
                datos.add(usugru);
                datos.add(fecha);
                datos.add(vige);
            }
        }

        return datos;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
