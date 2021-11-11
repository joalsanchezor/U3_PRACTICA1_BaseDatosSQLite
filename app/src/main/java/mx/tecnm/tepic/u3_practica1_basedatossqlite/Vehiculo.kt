package mx.tecnm.tepic.u3_practica1_basedatossqlite

import android.content.ContentValues
import android.content.Context
import java.util.ArrayList

class Vehiculo(p:Context) {
    var placa = ""
    var marca = ""
    var modelo = ""
    var año = 0
    var idconductor = 0
    val pnt = p

    fun insertar(): Boolean{
        val tablaVehiculo = BaseDatos(pnt, "Luigi_Pizza", null, 1).writableDatabase
        val datos = ContentValues()
        datos.put("placa", placa)
        datos.put("marca", marca)
        datos.put("modelo", modelo)
        datos.put("año", año)
        datos.put("idconductor", idconductor)
        val resultado = tablaVehiculo.insert("VEHICULO", null, datos)
        if(resultado == -1L){
            return false
        }
        return true
    }

    fun consulta() : ArrayList<String> {
        val tablaVehiculo = BaseDatos(pnt, "Luigi_Pizza", null, 1).readableDatabase
        val resultadoConsulta = ArrayList<String>()

        val cursor = tablaVehiculo.query("VEHICULO", arrayOf("*"), null, null, null, null, null)
        if (cursor.moveToFirst()){
            var dato = ""
            do {
                dato = cursor.getString(1)+"/n"+cursor.getString(2)+"/n"+cursor.getString(3)+"/n"+cursor.getString(4)
                resultadoConsulta.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultadoConsulta.add("NO HAY DATOS EN VEHICULO")
        }
        tablaVehiculo.close()
        return resultadoConsulta
    }

    fun obtenerIDs() : ArrayList<Int> {
        val tablaVehiculo = BaseDatos(pnt,"Luigi_Pizza",null,1).readableDatabase
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
        val tablaVehiculo = BaseDatos(pnt,"Luigi_Pizza",null,1).writableDatabase
        val resultado = tablaVehiculo.delete("VEHICULO","ID=?", arrayOf(idEliminar.toString()))
        if(resultado==0) return false
        return true
    }

    fun consulta(idABuscar:String) : Vehiculo{
        val tablaVehiculo = BaseDatos(pnt,"Luigi_Pizza",null,1).readableDatabase
        val cursor = tablaVehiculo.query("VEHICULO", arrayOf("*"),"ID=?", arrayOf(idABuscar),null,null,null)
        val vehiculo = Vehiculo(MainActivity3())
        if(cursor.moveToFirst()){
            vehiculo.placa = cursor.getString(1)
            vehiculo.marca = cursor.getString(2)
            vehiculo.modelo = cursor.getString(3)
            vehiculo.año = cursor.getInt(4)
            vehiculo.idconductor = cursor.getInt(5)
        }
        return vehiculo
    }

    fun actualizar(idActualizar :String):Boolean{
        val tablaVehiculo = BaseDatos(pnt,"Luigi_Pizza",null,1).writableDatabase
        val datos = ContentValues()

        datos.put("placa",placa)
        datos.put("marca",marca)
        datos.put("modelo",modelo)
        datos.put("año",año)
        datos.put("idconductor",idconductor)

        val resultado = tablaVehiculo.update("VEHICULO",datos,"ID=?", arrayOf(idActualizar))
        if(resultado == 0) return false
        return true
    }

    fun consulta1(idABuscar: Int) : ArrayList<String>{
        val tablaConductor = BaseDatos(pnt, "Luigi_Pizza", null, 1).readableDatabase
        val resultadoConsulta = ArrayList<String>()
        val cursor = tablaConductor.query("VEHICULO", arrayOf("*"),
            "(2021-AÑO)==$idABuscar", null, null, null, null)
        if (cursor.moveToFirst()){
            var dato = ""
            do {
                dato = cursor.getString(1)+"\n"+cursor.getString(2)+"\n"+cursor.getString(3)+"\n"+cursor.getString(4)
                resultadoConsulta.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultadoConsulta.add("NO HAY DATOS EN VEHICULO")
        }
        tablaConductor.close()
        return resultadoConsulta
    }
}