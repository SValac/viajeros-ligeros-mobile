package com.example.valac.viajerosligeros

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import com.example.valac.viajerosligeros.adapters.AsientosAdapter
import com.example.valac.viajerosligeros.clases.Destino
import com.example.valac.viajerosligeros.clases.Viajero
import com.example.valac.viajerosligeros.db.ViajerosDAO
import org.jetbrains.anko.*

class Autobus : AppCompatActivity(), AsientosAdapter.AsientosListener {

    override fun onItemClickListener(viajero: Viajero?, position: Int) {
        Log.d("Asiento", "viajero clikeado: ${viajero?.id}")

        if (viajero?.id == -1) {

            val intentRegistroViajeros: Intent = Intent(this, RegistroViajeros::class.java)
            intentRegistroViajeros.putExtra("asiento_numero", position + 1)
            intentRegistroViajeros.putExtra("nombre_viaje", destino?.nombreViaje)
            intentRegistroViajeros.putExtra("id", destino?.id)
            intentRegistroViajeros.putExtra("asientosDisponibles", destino?.numeroAsientosDisponibles)

            startActivityForResult(intentRegistroViajeros,destino!!.id)

        } else {
            //toast("asiento ocupado")
            val viajero = viajero
            val dialog = AsientoDialog.newInstance(viajero!!)
            dialog.show(supportFragmentManager, "viajero_data") }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu?.add(0, 1, 0, R.string.menu_viajero_editar)!!
        menu?.add(0, 2, 1, R.string.menu_viajero_eliminar)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {

        //var info = item?.menuInfo as AdapterView.AdapterContextMenuInfo

        when(item?.itemId){
            1 -> Toast.makeText(this,"Editar viajero en pocision selected",Toast.LENGTH_SHORT).show()
            2 -> {
                Toast.makeText(this,"Eliminar viajero selected",Toast.LENGTH_SHORT).show()
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            //do something
            if (data != null) {
                val asientosDisponibles: Int = data.getIntExtra("return_asientos_disponibles", 99)
                destino?.numeroAsientosDisponibles = asientosDisponibles
                Log.d("testDebug", asientosDisponibles.toString())
            } else {
                //do something else
                if (resultCode == Activity.RESULT_CANCELED)
                    Log.d("EA", "User Cancelled")
            }
        }
    }

    var listaAsientos: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var adaptador: AsientosAdapter? = null

    var destino: Destino? = null



    // var listaDeViajeros = Array<Viajero>()

    override fun onPostResume() {
        super.onPostResume()
        crearCamion(destino!!.numeroAsientosTotales)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autobus)

        destino = Destino(
                intent.extras["id"].toString().toInt(),
                intent.extras["destino"].toString(),
                intent.extras["costo"].toString().toDouble(),
                intent.extras["fecha"].toString(),
                intent.extras["asientosDisponibles"].toString().toInt(),
                intent.extras["asientosTotales"].toString().toInt(),
                intent.extras["detallesAdicionales"].toString())
        crearCamion(destino!!.numeroAsientosTotales)

    }

    fun crearCamion(numeroAsientos: Int) {

        //val listaDeViajeros = Array<Viajero?>(destino!!.numeroAsientosTotales){null}

        val dao = ViajerosDAO(applicationContext)
        val viajeros = dao.getViajeros(destino!!.nombreViaje)
        val listaDeViajeros = Array<Viajero>(numeroAsientos) { Viajero(-1, "Vacio", "", "", "", false, 0.00, "", 0, false, "", R.drawable.asiento_libre) }
        if (!viajeros.isNullOrEmpty()) {
            for (viajero in viajeros) {
//                if (viajero.anticipo_cantidad == 0.00)
//                    viajero.asientoImage = R.drawable.asiento_pagado
                if (viajero.anticipo_cantidad > 0.00 && viajero.anticipo_cantidad < destino!!.costoPorPersona)
                    viajero.asientoImage = R.drawable.asiento_apartado
                if (viajero.anticipo_cantidad >= destino!!.costoPorPersona)
                    viajero.asientoImage = R.drawable.asiento_pagado

                listaDeViajeros[viajero.numeroAsiento - 1] = viajero
            }
        }
        listaAsientos = findViewById(R.id.asientos_rclrvw)
        adaptador = AsientosAdapter(listaDeViajeros)
        adaptador?.setListener(this@Autobus)
        listaAsientos?.adapter = adaptador
        layoutManager = GridLayoutManager(this@Autobus, 4)
        listaAsientos?.layoutManager = layoutManager
    }

    fun openViajeroDialog(viajero: Viajero?) {

        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_template_asiento, null)

        val nombre = findViewById<TextView>(R.id.nombre_txtvw)
        val abonado = findViewById<TextView>(R.id.abonado_cantidad_txtvw)
        val costoTotal = findViewById<TextView>(R.id.total_costo_txtvw)
        val agregar = findViewById<TextView>(R.id.agregar_edtxt)
        val telefono = findViewById<TextView>(R.id.telefono_txtvw)
        val numeroHabitacion = findViewById<TextView>(R.id.habitacion_no_txtvw)
        val acompañantes = findViewById<TextView>(R.id.acompañantes_txtvw)
        val comentariosObservaciones = findViewById<TextView>(R.id.observaciones_commentarios_txtvw)


        /*nombre.text = viajero!!.nombre
        abonado.text = viajero.anticipo_cantidad.toString()
        costoTotal.text = destino!!.costoPorPersona.toString()
        telefono.text = viajero.telefono
        numeroHabitacion.text = viajero.tipoHabitacion
        acompañantes.text = "nombre 1, nombre 3"
        comentariosObservaciones.text = viajero.observacionesComentarios

        */

        /*make_call_imgvw.setOnClickListener { v: View? ->
            makeCall(viajero!!.telefono)
        }

        send_message_imgvw.setOnClickListener { v: View? ->
            sendSMS(viajero!!.telefono)
        }*/


        dialog.setView(dialogView)
        dialog.setCancelable(false)
        dialog.setPositiveButton("Validate", { dialogInterface: DialogInterface, i: Int -> })
        //dialog.show()
        dialog.create().show()
    }
}
