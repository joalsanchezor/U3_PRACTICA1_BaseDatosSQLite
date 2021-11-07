package mx.tecnm.tepic.ladm_u2_ejercicio4

import android.graphics.*
import android.view.View

class Lienzo(p:MainActivity) : View(p) {
    val principal = p
    val santa = BitmapFactory.decodeResource(principal.resources, R.drawable.trineosantaclaus)

    override fun onDraw(c: Canvas) {
        super.onDraw(c)

        val p = Paint()

        c.drawColor(Color.BLUE)

        //Montaña 1
        p.style = Paint.Style.FILL
        p.color = Color.WHITE
        c.drawCircle(350f,900f,600f,p)
        p.style = Paint.Style.STROKE
        p.strokeWidth = 5f
        p.color = Color.CYAN
        c.drawCircle(350f,900f,600f,p)

        //Montaña 2
        p.style = Paint.Style.FILL
        p.color = Color.WHITE
        c.drawCircle(1200f,900f,600f,p)
        p.style = Paint.Style.STROKE
        p.strokeWidth = 5f
        p.color = Color.CYAN
        c.drawCircle(1200f,900f,600f,p)

        //Luna
        p.style = Paint.Style.FILL
        p.color = Color.WHITE
        c.drawCircle(160f,100f,80f,p)
        p.style = Paint.Style.FILL
        p.color = Color.BLUE
        c.drawCircle(200f,80f,50f,p)

        //Arbol 1
        p.style = Paint.Style.FILL
        p.color = Color.rgb(128,60,0)
        c.drawRect(320f,350f,380f,430f,p)
        p.style = Paint.Style.FILL
        p.color = Color.GREEN
        c.drawCircle(350f,220f,70f,p)
        p.style = Paint.Style.FILL
        p.color = Color.GREEN
        c.drawCircle(350f,300f,70f,p)

        //Arbol 2
        p.style = Paint.Style.FILL
        p.color = Color.rgb(128,60,0)
        c.drawRect(790f,430f,850f,510f,p)
        p.style = Paint.Style.FILL
        p.color = Color.GREEN
        c.drawCircle(820f,300f,70f,p)
        p.style = Paint.Style.FILL
        p.color = Color.GREEN
        c.drawCircle(820f,380f,70f,p)

        //Casa
        p.style = Paint.Style.FILL
        p.color = Color.rgb(128,60,0)
        c.drawRect(1200f,350f,1350f,250f,p)
        //puerta
        p.style = Paint.Style.FILL
        p.color = Color.YELLOW
        c.drawRect(1150f,380f,1250f,500f,p)
        //techo
        p.style = Paint.Style.FILL
        p.color = Color.RED
        c.drawRect(1150f,300f,1400f,250f,p)

        //Trineo left: 1200f
        //c.drawBitmap(santa,1200f,100f,p)
    }
}                                                                                                                                                                                                                                                                                                                                                                                                                                             