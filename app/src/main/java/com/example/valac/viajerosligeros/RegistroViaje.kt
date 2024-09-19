package com.example.valac.viajerosligeros

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.valac.viajerosligeros.db.DestinosDAO
import kotlinx.android.synthetic.main.activity_registro_viaje.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread

class RegistroViaje : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_viaje)


        registrar_viaje_btn.setOnClickListener{ v: View? -> insertDestino(
                nombre_viaje_edtxt.text.toString(),
                costo_por_persona_edtxt.text.toString().toFloat(),
                fecha_viaje_edtxt.text.toString(),
                numero_asientos_edtxt.text.toString().toInt(),
                detalles_adicionales_edtxt.toString())
            finish()
        }
    }

    private fun insertDestino(nombre : String, costo : Float,fecha : String, asientos : Int, detalles : String) {
        doAsync {
            val dao = DestinosDAO(applicationContext)
            var destinoAgregadoCorrectamente = false
            if (!dao.exists(nombre)) {
                dao.insertDestino(nombre, costo,fecha, asientos, detalles)
                destinoAgregadoCorrectamente = true
            }

            uiThread {
                if (destinoAgregadoCorrectamente)
                    longToast("Destino Agregado Correctamente")
                else
                    longToast("El Destino Ya Existe")
            }
        }




    }
}
