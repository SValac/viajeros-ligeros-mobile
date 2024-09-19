package com.example.valac.viajerosligeros.adapters

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.valac.viajerosligeros.R
import com.example.valac.viajerosligeros.clases.Destino
import kotlinx.android.synthetic.main.item_template_destino.view.*


class DestinosAdapter(destinos: ArrayList<Destino>) : RecyclerView.Adapter<DestinosAdapter.DestinosViewHolder>(){

    private var mListener: DestinoListener? = null
    var destinos : ArrayList<Destino>? = null
    var viewHolder : DestinosViewHolder? = null

        init {
            this.destinos = destinos
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DestinosAdapter.DestinosViewHolder {
        val vista = LayoutInflater.from(p0.context).inflate(R.layout.item_template_destino,p0,false)
        viewHolder = DestinosViewHolder(vista)
        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return this.destinos?.count()!!
    }

    override fun onBindViewHolder(holder: DestinosAdapter.DestinosViewHolder, position: Int) {
        val destino = destinos?.get(position)
        holder.nombreViaje?.text = destino?.nombreViaje
        holder.fechaVIaje?.text = destino?.fechaViaje
        val lugares = "${destino?.numeroAsientosDisponibles} / ${destino?.numeroAsientosTotales}"
        holder.lugaresDisponibles?.text = lugares
        holder.destinoItem?.setOnClickListener { mListener?.onItemClickListener(destino) }
    }

    fun setListener(listener : DestinoListener){
        mListener = listener
    }

    class DestinosViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {
        var vista = vista
        var nombreViaje : TextView? = null
        var fechaVIaje : TextView? = null
        var lugaresDisponibles : TextView? = null

        var destinoItem : ConstraintLayout? = null

            init{
                destinoItem = vista.destino_template
                nombreViaje = vista.input_nombre_viaje_txtvw
                fechaVIaje = vista.input_fecha_viaje
                lugaresDisponibles = vista.input_lugare_disponibles_txtvw
            }
    }

    interface DestinoListener {
        fun onItemClickListener(destino: Destino?)
    }
}