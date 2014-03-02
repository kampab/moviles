package com.example.registro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Clase que crea la base de datos con sus correspondientes tablas y que permite acceder a sus filas y columnas cuando se requiere
 * @author pablo
 *
 */
public class UsuarioBDHelper extends SQLiteOpenHelper {
 
    //Sentencia SQL para crear la tabla de Usuarios
    String sqlCreate = "CREATE TABLE Usuarios (nombre TEXT, email TEXT, password TEXT, fecha_nac TEXT, PRIMARY KEY(email))";
 
    public UsuarioBDHelper(Context contexto, String nombre,CursorFactory factory, int version) 
    {
        super(contexto, nombre, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) 
    {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) 
    {
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }
}