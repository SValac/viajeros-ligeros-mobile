package com.example.valac.viajerosligeros.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.MediaStore
import com.example.valac.viajerosligeros.R
import com.example.valac.viajerosligeros.clases.Viajero
import org.jetbrains.anko.db.*

class ViajerosDAO(private val ctx : Context, private val db : SQLiteDatabase = ctx.database.readableDatabase) {

    fun getViajeros(nombreDestino: String): ArrayList<Viajero> {
        val viajeros = ArrayList<Viajero>()
        db.select("Viajeros", "_id", "nombre_viajero", "telefono", "destino_viaje", "tipo_habitacion", "anticipo_boolean",
                "anticipo_cantidad", "punto_abordaje", "numero_asiento", "representante_grupo", "observaciones_comentarios")
                .whereSimple("destino_viaje=?", "$nombreDestino")
                .parseList(object : MapRowParser<List<Viajero>> {
                    override fun parseRow(columns: Map<String, Any?>): List<Viajero> {
                        val id = columns.getValue("_id")
                        val nombre_viajero = columns.getValue("nombre_viajero")
                        val telefono = columns.getValue("telefono")
                        val destino_viaje = columns.getValue("destino_viaje")
                        val tipo_habitacion = columns.getValue("tipo_habitacion")
                        val anticipo_boolean = columns.getValue("anticipo_boolean")
                        val anticipo_cantidad = columns.getValue("anticipo_cantidad")
                        val punto_abordaje = columns.getValue("punto_abordaje")
                        val numero_asiento = columns.getValue("numero_asiento")
                        val representante_grupo = columns.getValue("representante_grupo")
                        val observaciones_comentarios = columns.getValue("observaciones_comentarios")

                        val viajero = Viajero(
                                id.toString().toInt(),
                                nombre_viajero.toString(),
                                telefono.toString(),
                                destino_viaje.toString(),
                                tipo_habitacion.toString(),
                                anticipo_boolean.toString().toBoolean(),
                                anticipo_cantidad.toString().toDouble(),
                                punto_abordaje.toString(),
                                numero_asiento.toString().toInt(),
                                representante_grupo.toString().toBoolean(),
                                observaciones_comentarios.toString(),
                                R.drawable.asiento_libre
                        )

                        viajeros.add(viajero)
                        return viajeros
                    }
                })
        return viajeros
    }

    fun insertViajero(nombre: String, telefono: String, destino: String, tipoHabitacion: String, anticipo: Boolean, anticipoCantidad: Double, abordaje: String, asiento: Int, representanteGrupo: Boolean, obervacionesComentarios: String) {
        db.insert("Viajeros",
                "nombre_viajero" to nombre,
                "telefono" to telefono,
                "destino_viaje" to destino,
                "tipo_habitacion" to tipoHabitacion,
                "anticipo_boolean" to anticipo,
                "anticipo_cantidad" to anticipoCantidad,
                "punto_abordaje" to abordaje,
                "numero_asiento" to asiento,
                "representante_grupo" to representanteGrupo,
                "observaciones_comentarios" to obervacionesComentarios)
    }

    fun deleteViajero(id: Long) = db.delete("Viajeros", "_id = {id}", "id" to id)

    fun deleteAllViajeros() = db.delete("Viajeros")

    fun updateViajero(id: Int, nombre: String, telefono: String, destino: String, tipoHabitacion: String, anticipo: Boolean, anticipoCantidad: Double, abordaje: String, asiento: Int, representanteGrupo: String, obervacionesComentarios: String) {
        db.update("Viajeros",
                "nombre_viajero" to nombre,
                "telefono" to telefono,
                "destino_viaje" to destino,
                "tipo_habitacion" to tipoHabitacion,
                "anticipo_boolean" to anticipo,
                "anticipo_cantidad" to anticipoCantidad,
                "punto_abordaje" to abordaje,
                "numero_asiento" to asiento,
                "representante_grupo" to representanteGrupo,
                "observaciones_comentarios" to obervacionesComentarios).whereSimple("_id = ?", "$id")
    }

    fun exists(name: String): Boolean {
        val string = db.select("Viajeros", "nombre_viajero").whereSimple("nombre_viajero = ?", name).exec {
            parseOpt(StringParser)
        }
        return !string.isNullOrBlank()
    }
}
