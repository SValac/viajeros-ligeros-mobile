package com.example.valac.viajerosligeros

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.valac.viajerosligeros.db.DestinosDAO
import kotlinx.android.synthetic.main.activity_registro_viajeros.*
import com.example.valac.viajerosligeros.db.ViajerosDAO
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class RegistroViajeros : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_viajeros)

        destino_edtxt.setText(intent.extras["nombre_viaje"].toString())
        asiento_edtxt.setText(intent.extras["asiento_numero"].toString())
        var asientosDisponibles: Int = intent.extras["asientosDisponibles"] as Int

        anticipo_rdgrp.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener{group, checkedId ->
            val radio : RadioButton = findViewById(checkedId)
            if (checkedId == R.id.anticipo_yes_rdbtn)
                anticipo_edtxt.visibility = View.VISIBLE
            else {
                anticipo_edtxt.setText("0.00")
                anticipo_edtxt.visibility = View.INVISIBLE
            }

        } )

        registrar_viajero_btn.setOnClickListener {v : View? -> insertViajero(
                nombre_edtxt.text.toString(),
                telefono_edtxt.text.toString(),
                destino_edtxt.text.toString(),
                tipo_habitacion_edtxt.text.toString(),
                comprobarAnticipo(),
                anticipo_edtxt.text.toString().toDouble(),
                punto_abordaje_edtxt.text.toString(),
                asiento_edtxt.text.toString().toInt(),
                comprobarRepresentanteGrupo(),
                observaciones_comentarios_edtxt.text.toString())

            var returnAsientosDisponibles:Intent = Intent()
            asientosDisponibles--
                returnAsientosDisponibles.putExtra("return_asientos_disponibles",asientosDisponibles)
            setResult(Activity.RESULT_OK,returnAsientosDisponibles)
        finish()
        }
    }

    private fun insertViajero( nombre : String, telefono : String, destino : String, tipoHabitacion : String, anticipo : Boolean, anticipo_cantidad : Double, puntoAbordaje : String, numeroAsiento : Int, representanteGrupo : Boolean, observacionesComentarios : String){
        doAsync {
            val viajerosDao = ViajerosDAO(applicationContext)
            var viajeroAgregadoCorrectamente = false
            if (!viajerosDao.exists(nombre)){
                viajerosDao.insertViajero(nombre,telefono,destino,tipoHabitacion,anticipo,anticipo_cantidad,puntoAbordaje,numeroAsiento,representanteGrupo,observacionesComentarios)
                updateAsientosDisponibles()
                viajeroAgregadoCorrectamente = true
            }
            uiThread {
                if (viajeroAgregadoCorrectamente)
                    longToast("Viajero Agregado Correctamente")
                else
                    toast("EL viajero a ya existe")
            }
        }
    }

    private fun updateAsientosDisponibles(){
        val destinoDao = DestinosDAO(applicationContext)
        val mDestinoId = intent.extras["id"].toString().toInt()
        var mAsientosDisponibles = intent.extras["asientosDisponibles"].toString().toInt()
        mAsientosDisponibles--
        destinoDao.updateAsientosDisponibles(mDestinoId,mAsientosDisponibles)
    }

    private fun comprobarAnticipo() : Boolean {
        if (anticipo_rdgrp.getCheckedRadioButtonId() == R.id.anticipo_yes_rdbtn)
            return  true
        else
            return  false
    }

    private fun comprobarRepresentanteGrupo() : Boolean{
       if(representante_grupo_rdgrp.checkedRadioButtonId == R.id.represante_grupo_yes_rdbtn)
           return true
        else
           return false
    }
}
