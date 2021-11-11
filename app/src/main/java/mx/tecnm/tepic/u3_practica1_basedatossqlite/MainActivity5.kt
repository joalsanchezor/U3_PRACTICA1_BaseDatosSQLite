package mx.tecnm.tepic.u3_practica1_basedatossqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main4.*
import kotlinx.android.synthetic.main.activity_main4.btnActualizar
import kotlinx.android.synthetic.main.activity_main5.*

class MainActivity5 : AppCompatActivity() {
    var id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        var extra = intent.extras
        id = extra!!.getString("idActualizar")!!

        val vehiculo = Vehiculo(this).consulta(id)
        actualizarPlaca.setText(vehiculo.placa)
        actualizarMarca.setText(vehiculo.marca)
        actualizarModelo.setText(vehiculo.modelo)
        actualizarAnio.setText(vehiculo.anio)

        btnActualizar.setOnClickListener {
            val vehiculoActualizar = Vehiculo(this)
            vehiculoActualizar.placa = actualizarPlaca.text.toString()
            vehiculoActualizar.marca = actualizarMarca.text.toString()
            vehiculoActualizar.modelo = actualizarModelo.text.toString()
            vehiculoActualizar.anio = actualizarAnio.text.toString().toInt()

            val resultado = vehiculoActualizar.actualizar(id)
            if(resultado){
                Toast.makeText(this,"EXITO SE ACTUALIZÓ", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"ERROR NO SE LOGRÓ ACTUALIZAR", Toast.LENGTH_LONG).show()
            }
        }
    }
}