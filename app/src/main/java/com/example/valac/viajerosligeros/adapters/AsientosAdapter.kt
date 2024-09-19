package com.example.valac.viajerosligeros.adapters

import android.app.Application
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.valac.viajerosligeros.R
import com.example.valac.viajerosligeros.clases.Destino
import com.example.valac.viajerosligeros.clases.Viajero
import kotlinx.android.synthetic.main.item_template_asiento.view.*
import org.jetbrains.anko.longToast

class AsientosAdapter(viajero : Array<Viajero>) : RecyclerView.Adapter<AsientosAdapter.AsientosViewHolder>() {

    private var mListener: AsientosListener? = null
    var viajeros : Array<Viajero>? = null
    var viewHolder : AsientosViewHolder? = null


    init {
        this.viajeros = viajero
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AsientosViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_template_asiento,p0,false)
        viewHolder = AsientosViewHolder(view)
        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return this.viajeros?.count()!!
    }

    override fun onBindViewHolder(holder: AsientosViewHolder, position: Int) {
        val viajero = viajeros?.get(position)
        holder.asiento?.setImageResource(viajero?.asientoImage!!)
        holder.nombreViajero?.text = viajero?.nombre
        holder.numeroAsiento?.text = (position + 1).toString()
        holder.asientoItem?.setOnClickListener {
            mListener?.onItemClickListener(viajero, position)
            //mListener?.onContextItemSelected(holder.ctxItem,viajero,position) //agregado "ctxItem" pero no manda nada
        }
    }

    fun setListener(listener : AsientosListener){
        mListener = listener
    }

    class AsientosViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener{
        var vista = view
        var asiento : ImageView? = null
        var nombreViajero : TextView? = null
        var numeroAsiento : TextView? = null
        //variables del context menu
        var ctxMenuEditarViajero : TextView? = null
        var ctxMenuEmliminarViajero : TextView? = null
        var ctxItem : MenuItem? = null  //esta variable es del menuitem que me pide el onContextItemSeleced para saber que item fue presionado

        val asientoItem : ConstraintLayout?

        init {
            asientoItem = view.asientos_template
            asiento = view.asiento_Imgvw
            nombreViajero = view.asiento_nombre_viajero_txtvw
            numeroAsiento = view.num_asiento_txtvw

            ctxMenuEditarViajero = view.findViewById(R.id.menu_editar_viajero)
            ctxMenuEmliminarViajero = view.findViewById(R.id.menu_eliminar_viajero)

            vista.setOnCreateContextMenuListener(this)
        }

        //codigo para contex menu,  funca, falta agregar el click listener pasando viajero ID
        override fun onCreateContextMenu(menu: ContextMenu?, vista: View?, p2: ContextMenu.ContextMenuInfo?) {
            menu?.add(this.adapterPosition, 1, 0, R.string.menu_viajero_editar)!!
            menu?.add(this.adapterPosition, 2, 1, R.string.menu_viajero_eliminar) }
    }


    interface AsientosListener {
        fun onItemClickListener(viajero: Viajero?, position : Int)

       // fun onContextItemSelected(item: MenuItem?, viajero: Viajero?, position: Int): Boolean
    }
}