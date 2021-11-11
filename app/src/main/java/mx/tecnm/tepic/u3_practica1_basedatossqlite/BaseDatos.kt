package mx.tecnm.tepic.u3_practica1_basedatossqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(p: SQLiteDatabase) {
        p.execSQL("CREATE TABLE CONDUCTOR( IDCONDUCTOR INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE VARCHAR(200), DOMICILIO VARCHAR(200), NOLICENCIA VARCHAR(50), VENCE VARCHAR(50))")
        p.execSQL("CREATE TABLE VEHICULO( IDVEHICULO INTEGER PRIMARY KEY AUTOINCREMENT, PLACA VARCHAR(200), MARCA VARCHAR(200), MODELO VARCHAR(200), ANIO VARCHAR(200), IDCONDUCTOR INTEGER REFERENCES CONDUCTOR(IDCONDUCTOR))")
    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {

    }
}