package com.example.valac.viajerosligeros

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.valac.viajerosligeros.adapters.DestinosAdapter
import com.example.valac.viajerosligeros.clases.Destino
import com.example.valac.viajerosligeros.db.DestinosDAO
import com.example.valac.viajerosligeros.db.ViajerosDAO
import kotlinx.android.synthetic.main.activity_mis_viajes.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MisViajes : AppCompatActivity() , DestinosAdapter.DestinoListener{

    override fun onItemClickListener(destino: Destino?) {
        doAsync {
            val dao = DestinosDAO(applicationContext)
            val destino = dao.getDestino(destino!!.id)
            startActivity<Autobus>(
                "id" to destino.id,
                "destino" to destino.nombreViaje,
                "costo" to destino.costoPorPersona,
                "fecha" to destino.fechaViaje,
                "asientosDisponibles" to destino.numeroAsientosDisponibles,
                "asientosTotales" to destino.numeroAsientosTotales,
                "detallesAdicionales" to destino.detallesAdicionales
            )

            uiThread {
                Toast.makeText(this@MisViajes, "ID : ${destino?.id} y numero de asientos: ${destino?.numeroAsientosTotales}", Toast.LENGTH_SHORT).show()
                Log.d("Destion Clikeado","" +
                        "${destino.id}" +
                        "${destino.nombreViaje}" +
                        "${destino.costoPorPersona}" +
                        "${destino.fechaViaje}" +
                        "${destino.numeroAsientosTotales}" +
                        "${destino.numeroAsientosDisponibles}" +
                        "${destino.detallesAdicionales}"
                )
            }
        }
    }

    var listaViajes : RecyclerView? = null
    var layoutManager : RecyclerView.LayoutManager? =null
    var adaptador : DestinosAdapter? = null

    override fun onPostResume() {
        super.onPostResume()
        actualizarListaDeViajes()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_viajes)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        actualizarListaDeViajes()

    }


    private fun actualizarListaDeViajes() {

        doAsync {
            val dao = DestinosDAO(applicationContext)
            val listaDeDestinios = dao.getAllDestinos()

            uiThread {
                if (listaDeDestinios.isNullOrEmpty() )
                    mis_viajes_txtvw.text = "No tienes Viajes"
                else{
                    listaViajes =findViewById(R.id.mis_viajes_rclrvw)
                    adaptador = DestinosAdapter(listaDeDestinios)
                    adaptador?.setListener(this@MisViajes)
                    listaViajes?.adapter = adaptador
                    layoutManager = LinearLayoutManager(this@MisViajes)
                    listaViajes?.layoutManager =layoutManager
                    mis_viajes_txtvw.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_mis_viajes, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
            when(item?.itemId){
                R.id.agregar_viaje ->{
                    startActivity<RegistroViaje>()
                    true
                }
                R.id.delete_table_destinos ->{
                    DestinosDAO(applicationContext).deleteAllDestinos()
                    ViajerosDAO(applicationContext).deleteAllViajeros()
                    actualizarListaDeViajes()
                    toast("Todos los destinos fueron borrados")
                    true
                }
                R.id.about ->{
                    //DestinosDAO(applicationContext).borrarTabla()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

//    override fun onBackPressed() {
//        //
//        Toast.makeText(getApplicationContext(), "Back press disabled!", Toast.LENGTH_SHORT).show()
//    }
}



