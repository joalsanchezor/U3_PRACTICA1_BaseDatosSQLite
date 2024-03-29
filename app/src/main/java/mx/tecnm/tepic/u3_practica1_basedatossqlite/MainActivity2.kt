package mx.tecnm.tepic.u3_practica1_basedatossqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.File

class MainActivity2 : AppCompatActivity() {
    var idConductores = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        listaCaptura()
        btnInsertar.setOnClickListener{
            val conductor = Conductor(this)

            conductor.nombre = campoNombre.text.toString()
            conductor.domicilio = campoDomicilio.text.toString()
            conductor.nolicencia = campoLicencia.text.toString()
            conductor.vence = campoVence.text.toString().toInt()

            val resultado = conductor.insertar()
            if(resultado) {
                Toast.makeText(this, "SE CAPTURARON LOS DATOS", Toast.LENGTH_LONG).show()
                campoDomicilio.setText("")
                campoNombre.setText("")
                campoLicencia.setText("")
                campoVence.setText("")
                listaCaptura()
            } else {
                Toast.makeText(this, "ERROR! NO SE PUDO CAPTURAR", Toast.LENGTH_LONG).show()
            }
        }

        btnConsulta1.setOnClickListener {
            val resultado = Conductor(this).consulta1()
            listaConductores.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,resultado)
            idConductores.clear()
            idConductores = Conductor(this).obtenerIDs()
            activarEVENTO(listaConductores)

            val conductor = Conductor(this).exportarLV()
            if(Conductor(this).exportarLV()){
                Toast.makeText(this,"SE GUARDO Y EXPORTO CONTENIDO DE LA CONSULTA 1 CON EXITO",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"NO SE PUDO EXPORTAR EL ARCHIVO",Toast.LENGTH_LONG).show()
            }
        }

        btnConsulta2.setOnClickListener {
            val resultado = Conductor(this).consulta2()
            listaConductores.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,resultado)
            idConductores.clear()
            idConductores = Conductor(this).obtenerIDs()
            activarEVENTO(listaConductores)

            val conductor = Conductor(this).exportarSV()
            if(Conductor(this).exportarSV()){
                Toast.makeText(this,"SE GUARDO Y EXPORTO CONTENIDO DE LA CONSULTA 2 CON EXITO",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"NO SE PUDO EXPORTAR EL ARCHIVO",Toast.LENGTH_LONG).show()
            }
        }

        btnConsulta3.setOnClickListener {
            val resultado = Conductor(this).consulta3(txtVC.text.toString().toInt())
            listaConductores.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,resultado)
            idConductores.clear()
            idConductores = Conductor(this).obtenerIDs()
            activarEVENTO(listaConductores)

            val conductor = Conductor(this).exportarVC(txtVC.text.toString().toInt())
            if(Conductor(this).exportarVC(txtVC.text.toString().toInt())){
                Toast.makeText(this,"SE GUARDO Y EXPORTO CONTENIDO DE LA CONSULTA 3 CON EXITO",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"NO SE PUDO EXPORTAR EL ARCHIVO",Toast.LENGTH_LONG).show()
            }

            txtVC.setText("")
        }

        btnExportar.setOnClickListener{
            val conductor = Conductor(this).exportar()
            if(Conductor(this).exportar()){
                Toast.makeText(this,"SE GUARDO Y EXPORTO CONTENIDO DE LA TABLA CON EXITO",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"NO SE PUDO EXPORTAR EL ARCHIVO",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun listaCaptura() {
        val arregloConductor = Conductor(this).consulta()
        listaConductores.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arregloConductor)
        idConductores.clear()
        idConductores = Conductor(this).obtenerIDs()
        activarEVENTO(listaConductores)
    }

    private fun activarEVENTO(listaCapturados: ListView){
        listaCapturados.setOnItemClickListener{adapterView, view, indiceSeleccionado, l ->

            val idSeleccionado = idConductores[indiceSeleccionado]
            AlertDialog.Builder(this)
                .setTitle("ATENCION")
                .setMessage("¿QUE DESEA HACER CON EL CONDUCTOR?")
                .setPositiveButton("EDITAR"){d,i-> actualizar(idSeleccionado)}
                .setNegativeButton("ELIMINAR"){d,i-> eliminar(idSeleccionado)}
                .setNeutralButton("CANCELAR"){d,i->
                    d.cancel()
                }
                .show()
        }
    }

    private fun actualizar(idSeleccionado: Int){
        val intento = Intent(this, MainActivity4::class.java)
        intento.putExtra("idActualizar",idSeleccionado.toString())
        startActivity(intento)

        AlertDialog.Builder(this).setMessage("DESEAS ACTUALIZAR LA LISTA?")
            .setPositiveButton("SI"){d,i-> listaCaptura()}
            .setNegativeButton("NO"){d,i-> d.cancel()}
            .show()
    }

    private fun eliminar(idSeleccionado: Int){
        AlertDialog.Builder(this)
            .setTitle("IMPORTANTE")
            .setMessage("¿SEGURO QUE DESEAS ELIMINAR ID $(idSeleccionado)?")
            .setPositiveButton("SI"){d,i->
                val resultado = Conductor(this).eliminar(idSeleccionado)
                if(resultado){
                    Toast.makeText(this,"SE ELIMINO CON EXITO",Toast.LENGTH_LONG).show()
                    listaCaptura()
                }else{
                    Toast.makeText(this,"ERROR NO SE LOGRO ELIMINAR", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("NO"){d,i->
                d.cancel()
            }
            .show()
    }


}