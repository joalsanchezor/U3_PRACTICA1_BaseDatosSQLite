package mx.tecnm.tepic.u3_practica1_basedatossqlite

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.FileWriter
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class Conductor(p:Context) {
    var nombre = ""
    var domicilio = ""
    var nolicencia = ""
    var vence = 0
    val pnt = p

    fun insertar(): Boolean{
        val tablaConductor = BaseDatos(pnt, "DBLuigiPizza", null, 1).writableDatabase
        val datos = ContentValues()
        datos.put("nombre", nombre)
        datos.put("domicilio", domicilio)
        datos.put("nolicencia", nolicencia)
        datos.put("vence", vence)

        val resultado = tablaConductor.insert("CONDUCTOR", null, datos)
        tablaConductor.close()
        if(resultado == -1L){
            return false
        }
        return true
    }

    fun consulta() : ArrayList<String>{
        val tablaConductor = BaseDatos(pnt, "DBLuigiPizza", null, 1).readableDatabase
        val resultadoConsulta = ArrayList<String>()

        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"), null, null, null, null, null)
        if (cursor.moveToFirst()){
            var dato = ""
            do {
                dato = cursor.getString(1)+"\n"+cursor.getString(2)+"\n"+cursor.getString(3)+"\n"+cursor.getString(4)
                resultadoConsulta.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultadoConsulta.add("NO HAY DATOS EN CONDUCTOR")
        }
        tablaConductor.close()
        return resultadoConsulta
    }

    fun obtenerIDs() : ArrayList<Int>{
        val tablaConductor = BaseDatos(pnt,"DBLuigiPizza",null,1).readableDatabase
        val resultado = ArrayList<Int>()
        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),null, null, null, null, null)
        if(cursor.moveToFirst()){
            do{
                resultado.add(cursor.getInt(0))
            }while (cursor.moveToNext())
        }
        tablaConductor.close()
        return resultado
    }

    fun eliminar(idEliminar:Int) : Boolean{
        val tablaConductor = BaseDatos(pnt,"DBLuigiPizza",null,1).writableDatabase
        val resultado = tablaConductor.delete("CONDUCTOR","ID=?", arrayOf(idEliminar.toString()))
        if(resultado==0) return false
        return true
    }

    fun consulta(idBuscar :String) : Conductor{
        val tablaConductor = BaseDatos(pnt,"DBLuigiPizza",null,1).readableDatabase
        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),"ID=?",null,null,null,null)
        val conductor = Conductor(MainActivity2())
        if(cursor.moveToFirst()){
            conductor.nombre = cursor.getString(1)
            conductor.domicilio = cursor.getString(2)
            conductor.nolicencia = cursor.getString(3)
            conductor.vence = cursor.getInt(4)
        }
        tablaConductor.close()
        return conductor
    }

    fun actualizar(idActualizar :String):Boolean{
        val tablaConductor = BaseDatos(pnt,"DBLuigiPizza",null,1).writableDatabase
        val datos = ContentValues()

        datos.put("nombre",nombre)
        datos.put("domicilio",domicilio)
        datos.put("nolicencia",nolicencia)
        datos.put("vence",vence)

        val resultado = tablaConductor.update("CONDUCTOR",datos,"ID=?", arrayOf(idActualizar))
        if(resultado == 0) return false
        return true
    }

    fun consulta1() : ArrayList<String>{
        val tablaConductor = BaseDatos(pnt, "DBLuigiPizza", null, 1).readableDatabase
        val resultadoConsulta = ArrayList<String>()

        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"), "VENCE<="+2021, null, null, null, null)
        if (cursor.moveToFirst()){
            var dato = ""
            do {
                dato = cursor.getString(1)+"\n"+cursor.getString(2)+"\n"+cursor.getString(3)+"\n"+cursor.getString(4)
                resultadoConsulta.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultadoConsulta.add("NO HAY DATOS EN CONDUCTOR")
        }
        tablaConductor.close()
        return resultadoConsulta
    }

    fun consulta2() : ArrayList<String>{
        val tablaConductor = BaseDatos(pnt, "DBLuigiPizza", null, 1).readableDatabase
        val db = BaseDatos(pnt, "DBLuigiPizza",null,1).readableDatabase
        val resultadoConsulta = ArrayList<String>()

        val cursor = db.rawQuery("SELECT * FROM CONDUCTOR, VEHICULO " + "WHERE CONDUCTOR.IDCONDUCTOR = VEHICULO.IDCONDUCTOR " + "GROUP BY CONDUCTOR.IDCONDUCTOR",null)
        if (cursor.moveToFirst()){
            var dato = ""
            do {
                dato = cursor.getString(1)+"\n"+cursor.getString(2)+"\n"+cursor.getString(3)+"\n"+cursor.getString(4)
                resultadoConsulta.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultadoConsulta.add("NO HAY DATOS EN CONDUCTOR")
        }
        tablaConductor.close()
        return resultadoConsulta
    }

    fun consulta3(idABuscar: Int) : java.util.ArrayList<String> {
        val tablaConductor = BaseDatos(pnt, "DBLuigiPizza", null, 1).readableDatabase
        val resultadoConsulta = java.util.ArrayList<String>()
        val cursor = tablaConductor.rawQuery("SELECT DISTINCT * from CONDUCTOR v INNER JOIN VEHICULO c ON v.idConductor = c.idConductor where c.idConductor = $idABuscar",null)
        if (cursor.moveToFirst()){
            var dato = ""
            do {
                dato = cursor.getString(1)+"\n"+cursor.getString(2)+"\n"+cursor.getString(3)+"\n"+cursor.getString(4)
                resultadoConsulta.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultadoConsulta.add("NO HAY DATOS EN CONDUCTOR")
        }
        tablaConductor.close()
        return resultadoConsulta
    }

    fun exportar() : Boolean{
        val folder = File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/SQLite")
        if(!folder.exists()){
            folder.mkdir()
        }
        val fileName = "conductores.csv"
        val fileNameAndPath = "$folder/$fileName"

        try {
            val documents = FileWriter(fileNameAndPath)

            val drivTable = BaseDatos(pnt,"DBLuigiPizza",null,1).readableDatabase
            val cursor = drivTable.query("CONDUCTOR", arrayOf("*"),null,null,null,null,null)
            if(cursor.moveToFirst()){
                do {
                    documents.append("${cursor.getString(0)}")
                    documents.append(",")
                    documents.append("${cursor.getString(1)}")
                    documents.append(",")
                    documents.append("${cursor.getString(2)}")
                    documents.append(",")
                    documents.append("${cursor.getString(3)}")
                    documents.append(",")
                    documents.append("${cursor.getString(4)}")
                    documents.append(",")
                }while (cursor.moveToNext())
            }
            drivTable.close()
            documents.close()
            return true
        }catch (e: Exception){
            return false
        }
    }

    fun exportarLV() : Boolean{
        val folder = File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/SQLite")
        if(!folder.exists()){
            folder.mkdir()
        }
        val fileName = "conductoresLV.csv"
        val fileNameAndPath = "$folder/$fileName"

        try {
            val documents = FileWriter(fileNameAndPath)

            val drivTable = BaseDatos(pnt,"DBLuigiPizza",null,1).readableDatabase
            val cursor = drivTable.query("CONDUCTOR", arrayOf("*"), "VENCE<="+2021, null, null, null, null)
            if(cursor.moveToFirst()){
                do {
                    documents.append("${cursor.getString(0)}")
                    documents.append(",")
                    documents.append("${cursor.getString(1)}")
                    documents.append(",")
                    documents.append("${cursor.getString(2)}")
                    documents.append(",")
                    documents.append("${cursor.getString(3)}")
                    documents.append(",")
                    documents.append("${cursor.getString(4)}")
                    documents.append(",")
                }while (cursor.moveToNext())
            }
            drivTable.close()
            documents.close()
            return true
        }catch (e: Exception){
            return false
        }
    }

    fun exportarSV() : Boolean{
        val folder = File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/SQLite")
        if(!folder.exists()){
            folder.mkdir()
        }
        val fileName = "conductoresSV.csv"
        val fileNameAndPath = "$folder/$fileName"

        try {
            val documents = FileWriter(fileNameAndPath)

            val drivTable = BaseDatos(pnt,"DBLuigiPizza",null,1).readableDatabase
            val cursor = drivTable.rawQuery("SELECT * FROM CONDUCTOR, VEHICULO " + "WHERE CONDUCTOR.IDCONDUCTOR = VEHICULO.IDCONDUCTOR " + "GROUP BY CONDUCTOR.IDCONDUCTOR",null)
            if(cursor.moveToFirst()){
                do {
                    documents.append("${cursor.getString(0)}")
                    documents.append(",")
                    documents.append("${cursor.getString(1)}")
                    documents.append(",")
                    documents.append("${cursor.getString(2)}")
                    documents.append(",")
                    documents.append("${cursor.getString(3)}")
                    documents.append(",")
                    documents.append("${cursor.getString(4)}")
                    documents.append(",")
                }while (cursor.moveToNext())
            }
            drivTable.close()
            documents.close()
            return true
        }catch (e: Exception){
            return false
        }
    }

    fun exportarVC(idABuscar: Int) : Boolean{
        val folder = File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/SQLite")
        if(!folder.exists()){
            folder.mkdir()
        }
        val fileName = "conductoresVC.csv"
        val fileNameAndPath = "$folder/$fileName"

        try {
            val documents = FileWriter(fileNameAndPath)

            val drivTable = BaseDatos(pnt,"DBLuigiPizza",null,1).readableDatabase
            val cursor = drivTable.rawQuery("SELECT DISTINCT * from CONDUCTOR v INNER JOIN VEHICULO c ON v.idConductor = c.idConductor where c.idConductor = $idABuscar",null)
            if(cursor.moveToFirst()){
                do {
                    documents.append("${cursor.getString(0)}")
                    documents.append(",")
                    documents.append("${cursor.getString(1)}")
                    documents.append(",")
                    documents.append("${cursor.getString(2)}")
                    documents.append(",")
                    documents.append("${cursor.getString(3)}")
                    documents.append(",")
                    documents.append("${cursor.getString(4)}")
                    documents.append(",")
                }while (cursor.moveToNext())
            }
            drivTable.close()
            documents.close()
            return true
        }catch (e: Exception){
            return false
        }
    }
}