package mx.tecnm.tepic.u3_practica1_basedatossqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main4.*

class MainActivity4 : AppCompatActivity() {
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        var extra = intent.extras
        id = extra!!.getString("idActualizar")!!

        val conductor = Conductor(this).consulta(id)
        actualizarNombre.setText(conductor.nombre)
        actualizarDomicilio.setText(conductor.domicilio)
        actualizarLicencia.setText(conductor.nolicencia)
        actualizarVence.setText(conductor.vence)

        btnActualizar.setOnClickListener {
            val conductorActualizar = Conductor(this)
            conductorActualizar.nombre = actualizarNombre.text.toString()
            conductorActualizar.domicilio = actualizarDomicilio.text.toString()
            conductorActualizar.nolicencia = actualizarLicencia.text.toString()
            conductorActualizar.vence = actualizarVence.text.toString().toInt()

            val resultado = conductorActualizar.actualizar(id)
            if(resultado){
                Toast.makeText(this,"EXITO SE ACTUALIZÓ", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"ERROR NO SE LOGRÓ ACTUALIZAR", Toast.LENGTH_LONG).show()
            }
        }
    }
}