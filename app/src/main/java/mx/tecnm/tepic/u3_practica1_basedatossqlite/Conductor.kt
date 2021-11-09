package mx.tecnm.tepic.u3_practica1_basedatossqlite

import android.content.ContentValues
import android.content.Context
import java.util.*

class Conductor(p:Context) {
    var nombre = ""
    var domicilio = ""
    var nolicencia = ""
    var vence = ""
    val pnt = p

    fun insertar(): Boolean{
        val tablaConductor = BaseDatos(pnt, "Luigi_Pizza", null, 1).writableDatabase
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
        val tablaConductor = BaseDatos(pnt, "Luigi_Pizza", null, 1).readableDatabase
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
        val tablaConductor = BaseDatos(pnt,"Luigi_Pizza",null,1).readableDatabase
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
        val tablaConductor = BaseDatos(pnt,"Luigi_Pizza",null,1).writableDatabase
        val resultado = tablaConductor.delete("CONDUCTOR","ID=?", arrayOf(idEliminar.toString()))
        if(resultado==0) return false
        return true
    }

    fun consulta(idABuscar:String) : Conductor{
        val tablaConductor = BaseDatos(pnt,"Luigi_Pizza",null,1).readableDatabase
        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),"ID=?", arrayOf(idABuscar),null,null,null)
        val conductor = Conductor(MainActivity2())
        if(cursor.moveToFirst()){
            conductor.nombre = cursor.getString(1)
            conductor.domicilio = cursor.getString(2)
            conductor.nolicencia = cursor.getString(3)
            conductor.vence = cursor.getString(4)
        }
        return conductor
    }

    fun actualizar(idActualizar :String):Boolean{
        val tablaConductor = BaseDatos(pnt,"Luigi_Pizza",null,1).writableDatabase
        val datos = ContentValues()

        datos.put("nombre",nombre)
        datos.put("domicilio",domicilio)
        datos.put("nolicencia",nolicencia)
        datos.put("vence",vence)

        val resultado = tablaConductor.update("CONDUCTOR",datos,"ID=?", arrayOf(idActualizar))
        if(resultado == 0) return false
        return true
    }
}