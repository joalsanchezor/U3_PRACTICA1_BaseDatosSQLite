package mx.tecnm.tepic.u3_practica1_basedatossqlite

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main2.btnInsertar
import kotlinx.android.synthetic.main.activity_main3.*

class MainActivity3 : AppCompatActivity() {
    var idVehiculos = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        listaCaptura()
        btnInsertar.setOnClickListener{
            val Vehiculo = Vehiculo(this)

            Vehiculo.placa = campoPlaca.text.toString()
            Vehiculo.marca = campoMarca.text.toString()
            Vehiculo.modelo = campoModelo.text.toString()
            Vehiculo.anio = campoAño.text.toString().toInt()
            Vehiculo.idconductor = campoIdconductor.text.toString()
            val resultado = Vehiculo.insertar()

            if(resultado) {
                Toast.makeText(this, "SE CAPTURARON LOS DATOS", Toast.LENGTH_LONG).show()
                campoPlaca.setText("")
                campoMarca.setText("")
                campoModelo.setText("")
                campoAño.setText("")
                campoIdconductor.setText("")
                listaCaptura()

            } else {
                Toast.makeText(this, "ERROR! NO SE PUDO CAPTURAR", Toast.LENGTH_LONG).show()
            }
        }

        btnVehiculosAniosUso.setOnClickListener {
            consulta1(txtAniosUso.text.toString().toInt())

            val vehiculo = Vehiculo(this).exportarAU(txtAniosUso.text.toString().toInt())
            if(Vehiculo(this).exportarAU(txtAniosUso.text.toString().toInt())){
                Toast.makeText(this,"SE GUARDO Y EXPORTO CONTENIDO DE LA CONSULTA 1 CON EXITO",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"NO SE PUDO EXPORTAR EL ARCHIVO",Toast.LENGTH_LONG).show()
            }
            txtAniosUso.setText("")
        }

        btnVC.setOnClickListener {
            consulta1(txtVC1.text.toString().toInt())


            val vehiculo = Vehiculo(this).exportarVC(txtVC1.text.toString().toInt())
            if(Vehiculo(this).exportarVC(txtVC1.text.toString().toInt())){
                Toast.makeText(this,"SE GUARDO Y EXPORTO CONTENIDO DE LA CONSULTA 2 CON EXITO",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"NO SE PUDO EXPORTAR EL ARCHIVO",Toast.LENGTH_LONG).show()
            }
            txtVC1.setText("")
        }

        btnExportar1.setOnClickListener{
            val vehiculo = Vehiculo(this).exportar()
            if(Vehiculo(this).exportar()){
                Toast.makeText(this,"SE GUARDO CONTENIDO DE LA TABLA CON EXITO",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"NO SE PUDO EXPORTAR EL ARCHIVO",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun listaCaptura() {
        val arregloVehiculo = Vehiculo(this).consulta()
        listaVehiculos.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arregloVehiculo)
        idVehiculos.clear()
        idVehiculos = Vehiculo(this).obtenerIDs()
        activarEVENTO(listaVehiculos)
    }

    private fun activarEVENTO(listaCapturados: ListView){
        listaCapturados.setOnItemClickListener{adapterView, view, indiceSeleccionado, l ->

            val idSeleccionado = idVehiculos[indiceSeleccionado]
            AlertDialog.Builder(this)
                .setTitle("ATENCION")
                .setMessage("¿QUE DESEA HACER CON EL VEHICULO?")
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
                val resultado = Vehiculo(this).eliminar(idSeleccionado)
                if(resultado){
                    Toast.makeText(this,"SE ELIMINO CON EXITO", Toast.LENGTH_LONG).show()
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

    private fun consulta1(cantidad: Int){
        val resultado = Vehiculo(this).consulta1(cantidad)
        listaVehiculos.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,resultado)
        idVehiculos.clear()
        idVehiculos = Vehiculo(this).obtenerIDs()
        activarEVENTO(listaVehiculos)
    }
}