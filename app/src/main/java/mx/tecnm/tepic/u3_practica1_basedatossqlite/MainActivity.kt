package mx.tecnm.tepic.u3_practica1_basedatossqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnConductor.setOnClickListener {
            var activity2 = Intent(this, MainActivity2::class.java)
            startActivity(activity2)
        }

       btnVehiculo.setOnClickListener {
            var activity3 = Intent(this, MainActivity3::class.java)
            startActivity(activity3)
        }
    }
}