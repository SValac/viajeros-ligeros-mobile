package com.example.valac.viajerosligeros.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.ContactsContract
import org.jetbrains.anko.db.*

class DatabaseHelper(ctx : Context) : ManagedSQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION){

    companion object {

        const val DB_NAME = "viajeros_lijeros.db"
        const val DB_VERSION = 1

        private var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable("Destinos",true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT + UNIQUE,
                "nombre_viaje" to TEXT,
                "costo_pp" to REAL,
                "fecha_viaje" to TEXT,
                "asientos_totales" to INTEGER,
                "asientos_disponibles" to INTEGER,
                "detalles_adicionales" to TEXT)

       db?.createTable("Viajeros",true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT + UNIQUE,
                "nombre_viajero" to TEXT,
                "telefono" to TEXT,
                "destino_viaje" to TEXT,
                "tipo_habitacion" to TEXT,
                "anticipo_boolean" to  INTEGER,
                "anticipo_cantidad" to REAL,
                "punto_abordaje" to TEXT,
                "numero_asiento" to INTEGER,
                "representante_grupo" to INTEGER,
                "observaciones_comentarios" to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //db?.dropTable("Viajeros", true)
        db?.dropTable("Destinos", true)
        onCreate(db)
    }


}

val Context.database : DatabaseHelper
    get() =DatabaseHelper.getInstance(applicationContext)