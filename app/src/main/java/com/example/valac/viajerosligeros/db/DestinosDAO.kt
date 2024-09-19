package com.example.valac.viajerosligeros.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.valac.viajerosligeros.clases.Destino
import org.jetbrains.anko.db.*

class DestinosDAO(private val ctx : Context, private val db : SQLiteDatabase = ctx.database.readableDatabase) {

    fun getAllDestinos() : ArrayList<Destino>{
        val destinos = ArrayList<Destino>()
        db.select("Destinos", "_id","nombre_viaje","costo_pp","fecha_viaje","asientos_totales","asientos_disponibles","detalles_adicionales")
                .parseList(object : MapRowParser<List<Destino>>{
                    override fun parseRow(columns: Map<String, Any?>): List<Destino> {
                        val id = columns.getValue("_id")
                        val nombre_viaje = columns.getValue("nombre_viaje")
                        val costo_pp = columns.getValue("costo_pp")
                        val fecha_viaje = columns.getValue("fecha_viaje")
                        val asientos_totales = columns.getValue("asientos_totales")
                        val asientos_disponibles = columns.getValue("asientos_disponibles")
                        val detalles_adicionales = columns.getValue("detalles_adicionales")

                        val destino = Destino(
                                id.toString().toInt(),
                                nombre_viaje.toString(),
                                costo_pp.toString().toDouble(),
                                fecha_viaje.toString(),
                                asientos_disponibles.toString().toInt(),
                                asientos_totales.toString().toInt(),
                                detalles_adicionales.toString())
                        destinos.add(destino)
                        return  destinos
                    }
                })
        return destinos
        //return db.select("Destinos", "nombre_viaje").exec { parseList(object ) }
    }

    fun getDestino(id : Int) : Destino {
         return db.select("Destinos","_id","nombre_viaje","costo_pp","fecha_viaje","asientos_totales","asientos_disponibles","detalles_adicionales")
                .whereSimple("_id =?" ,"$id")
                .parseSingle(object : MapRowParser<Destino> {
                    override fun parseRow(columns: Map<String, Any?>): Destino {
                        val id = columns.getValue("_id")
                        val nombre_viaje = columns.getValue("nombre_viaje")
                        val costo_pp = columns.getValue("costo_pp")
                        val fecha_viaje = columns.getValue("fecha_viaje")
                        val asientos_totales = columns.getValue("asientos_totales")
                        val asientos_disponibles = columns.getValue("asientos_disponibles")
                        val detalles_adicionales = columns.getValue("detalles_adicionales")

                        val destino = Destino(
                                id.toString().toInt(),
                                nombre_viaje.toString(),
                                costo_pp.toString().toDouble(),
                                fecha_viaje.toString(),
                                asientos_disponibles.toString().toInt(),
                                asientos_totales.toString().toInt(),
                                detalles_adicionales.toString())
                        return destino
                    }
                })
    }


    fun insertDestino( nombre_viaje : String, costp_pp : Float, fecha_viaje : String ,asientos_totales : Int, detalles_adicionales : String) : Long =
        db.insert("Destinos",
                "nombre_viaje" to nombre_viaje,
                "costo_pp" to costp_pp,
                "fecha_viaje" to fecha_viaje,
                "asientos_totales" to asientos_totales,
                "asientos_disponibles" to asientos_totales,
                "detalles_adicionales" to detalles_adicionales)


    fun deleteDestino(id: Long) = db.delete("Destinos", "_id = {id}", "id" to id)

    fun updateDestino(id : Int,nombre_viaje : String, costp_pp : Float,fecha_viaje: String, asientos_totales : Int, detalles_adicionales : String){
        db.update("Destinos",
                "nombre_viaje" to nombre_viaje,
                "costo_pp" to costp_pp,
                "fecha_viaje" to fecha_viaje,
                "asientos_totales" to asientos_totales,
                "detalles_adicionales" to detalles_adicionales).whereSimple("_id =?" ,"$id")
    }

    fun updateAsientosDisponibles(id : Int, asientos_disponibles : Int){
        db.update("Destinos",
                    "asientos_disponibles" to asientos_disponibles).whereSimple("_id =?" ,"$id")
                .exec()
    }


    fun deleteAllDestinos() = db.delete("Destinos")

    //fun borrarTabla()= db.dropTable("Destinos", true)

    fun exists(name: String) : Boolean{
        val string = db.select("Destinos", "nombre_viaje").whereSimple("nombre_viaje = ?" , name).exec {
            parseOpt(StringParser)
        }
        return !string.isNullOrBlank()
    }









}