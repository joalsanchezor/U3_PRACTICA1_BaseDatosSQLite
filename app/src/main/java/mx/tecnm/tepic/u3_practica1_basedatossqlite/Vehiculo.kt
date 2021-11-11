package mx.tecnm.tepic.u3_practica1_basedatossqlite

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import java.io.File
import java.io.FileWriter
import java.lang.Exception
import java.util.ArrayList

class Vehiculo(p:Context) {
    var placa = ""
    var marca = ""
    var modelo = ""
    var anio = 0
    var idconductor = ""
    val pnt = p

    fun insertar(): Boolean{
        val tablaVehiculo = BaseDatos(pnt, "DBLuigiPizza", null, 1).writableDatabase
        val datos = ContentValues()
        datos.put("placa", placa)
        datos.put("marca", marca)
        datos.put("modelo", modelo)
        datos.put("anio", anio)
        datos.put("idconductor", idconductor.toInt())
        val resultado = tablaVehiculo.insert("VEHICULO", null, datos)
        tablaVehiculo.close()
        if(resultado == -1L){
            return false
        }
        return true
    }

    fun consulta() : ArrayList<String> {
        val tablaVehiculo = BaseDatos(pnt, "DBLuigiPizza", null, 1).readableDatabase
        val resultadoConsulta = ArrayList<String>()

        val cursor = tablaVehiculo.query("VEHICULO", arrayOf("*"), null, null, null, null, null)
        if (cursor.moveToFirst()){
            var dato = ""
            do {
                dato = cursor.getString(1)+"\n"+cursor.getString(2)+"\n"+cursor.getString(3)+"\n"+cursor.getString(4)
                resultadoConsulta.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultadoConsulta.add("NO HAY DATOS EN VEHICULO")
        }
        tablaVehiculo.close()
        return resultadoConsulta
    }

    fun obtenerIDs() : ArrayList<Int> {
        val tablaVehiculo = BaseDatos(pnt,"DBLuigiPizza",null,1).readableDatabase
        val resultado = ArrayList<Int>()
        val cursor = tablaVehiculo.query("VEHICULO", arrayOf("*"),null, null, null, null, null)
        if(cursor.moveToFirst()){
            do{
                resultado.add(cursor.getInt(0))
            }while (cursor.moveToNext())
        }
        return resultado
    }

    fun eliminar(idEliminar:Int) : Boolean{
        val tablaVehiculo = BaseDatos(pnt,"DBLuigiPizza",null,1).writableDatabase
        val resultado = tablaVehiculo.delete("VEHICULO","ID=?", arrayOf(idEliminar.toString()))
        if(resultado==0) return false
        return true
    }

    fun consulta(idABuscar:String) : Vehiculo{
        val tablaVehiculo = BaseDatos(pnt,"DBLuigiPizza",null,1).readableDatabase
        val cursor = tablaVehiculo.query("VEHICULO", arrayOf("*"),"ID=?", arrayOf(idABuscar),null,null,null)
        val vehiculo = Vehiculo(MainActivity3())
        if(cursor.moveToFirst()){
            vehiculo.placa = cursor.getString(1)
            vehiculo.marca = cursor.getString(2)
            vehiculo.modelo = cursor.getString(3)
            vehiculo.anio = cursor.getInt(4)
            vehiculo.idconductor = cursor.getString(5)
        }
        return vehiculo
    }

    fun actualizar(idActualizar :String):Boolean{
        val tablaVehiculo = BaseDatos(pnt,"DBLuigiPizza",null,1).writableDatabase
        val datos = ContentValues()

        datos.put("placa",placa)
        datos.put("marca",marca)
        datos.put("modelo",modelo)
        datos.put("año",anio)
        datos.put("idconductor",idconductor)

        val resultado = tablaVehiculo.update("VEHICULO",datos,"ID=?", arrayOf(idActualizar))
        if(resultado == 0) return false
        return true
    }

    fun consulta1(idABuscar: Int) : ArrayList<String>{
        val tablaVehiculo = BaseDatos(pnt, "DBLuigiPizza", null, 1).readableDatabase
        val resultadoConsulta = ArrayList<String>()
        val cursor = tablaVehiculo.query("VEHICULO", arrayOf("*"),
            "(2021-ANIO)==${idABuscar.toInt()}", null, null, null, null)
        if (cursor.moveToFirst()){
            var dato = ""
            do {
                dato = cursor.getString(1)+"\n"+cursor.getString(2)+"\n"+cursor.getString(3)+"\n"+cursor.getString(4)
                resultadoConsulta.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultadoConsulta.add("NO HAY DATOS EN VEHICULO")
        }
        tablaVehiculo.close()
        return resultadoConsulta
    }

    fun consulta2(idABuscar: Int) : ArrayList<String>{
        val tablaVehiculo = BaseDatos(pnt, "DBLuigiPizza", null, 1).readableDatabase
        val resultadoConsulta = ArrayList<String>()
        val cursor = tablaVehiculo.rawQuery("SELECT DISTINCT * from VEHICULO v INNER JOIN CONDUCTOR c ON v.idConductor = c.idConductor where c.idConductor = $idABuscar",null)
        if (cursor.moveToFirst()){
            var dato = ""
            do {
                dato = cursor.getString(1)+"\n"+cursor.getString(2)+"\n"+cursor.getString(3)+"\n"+cursor.getString(4)
                resultadoConsulta.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultadoConsulta.add("NO HAY DATOS EN VEHICULO")
        }
        tablaVehiculo.close()
        return resultadoConsulta
    }

    fun exportar() : Boolean{
        val folder = File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/SQLite")
        if(!folder.exists()){
            folder.mkdir()
        }
        val fileName = "vehiculos.csv"
        val fileNameAndPath = "$folder/$fileName"

        try {
            val documents = FileWriter(fileNameAndPath)

            val drivTable = BaseDatos(pnt,"DBLuigiPizza",null,1).readableDatabase
            val cursor = drivTable.query("VEHICULO", arrayOf("*"),null,null,null,null,null)
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
                    documents.append("${cursor.getString(5)}")
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

    fun exportarAU(idABuscar: Int) : Boolean{
        val folder = File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)}/SQLite")
        if(!folder.exists()){
            folder.mkdir()
        }
        val fileName = "vehiculosAU.csv"
        val fileNameAndPath = "$folder/$fileName"

        try {
            val documents = FileWriter(fileNameAndPath)

            val drivTable = BaseDatos(pnt,"DBLuigiPizza",null,1).readableDatabase
            val cursor = drivTable.query("VEHICULO", arrayOf("*"),"(2021-ANIO)==${idABuscar.toInt()}", null, null, null, null)
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
        val fileName = "vehiculosVC.csv"
        val fileNameAndPath = "$folder/$fileName"

        try {
            val documents = FileWriter(fileNameAndPath)

            val drivTable = BaseDatos(pnt,"DBLuigiPizza",null,1).readableDatabase
            val cursor = drivTable.rawQuery("SELECT DISTINCT * from VEHICULO v INNER JOIN CONDUCTOR c ON v.idConductor = c.idConductor where c.idConductor = $idABuscar",null)
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
                    documents.append("${cursor.getString(5)}")
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