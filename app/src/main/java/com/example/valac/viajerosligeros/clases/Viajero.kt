package com.example.valac.viajerosligeros.clases

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import android.widget.ImageView
import com.example.valac.viajerosligeros.R

@Parcelize
data class Viajero(var id: Int, var nombre : String, var telefono : String, var destino : String, var tipoHabitacion : String, var anticipo : Boolean,
                   var anticipo_cantidad : Double, var puntoAbordaje : String, var numeroAsiento : Int, var representanteGrupo : Boolean,
                   var observacionesComentarios : String,  var asientoImage : Int) : Parcelable
    {
        /*var id = 0
        var nombre = ""
        var telefono = ""
        var destino = ""
        var tipoHabitacion = ""
        var anticipo = false
        var anticipo_cantidad = 0.00
        var puntoAbordaje = ""
        var numeroAsiento = 0
        var representanteGrupo = false
        var observacionesComentarios = ""
        var asientoImage  = R.drawable.asiento_libre

        init {
        this.id = id
        this.nombre = nombre
        this.telefono = telefono
        this.destino = destino
        this.tipoHabitacion = tipoHabitacion
        this.anticipo = anticipo
        this.anticipo_cantidad = anticipo_cantidad
        this.puntoAbordaje = puntoAbordaje
        this.numeroAsiento = numeroAsiento
        this.representanteGrupo = representanteGrupo
        this.observacionesComentarios = observacionesComentarios
        }
*/
    }