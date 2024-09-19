package com.example.valac.viajerosligeros.clases

class Destino(id : Int, nombre : String, costo : Double, fecha: String, asientos_disponibles :  Int, asientos_totales : Int, detalles : String) {
    var id = 0
    var nombreViaje  = ""
    var costoPorPersona = 0.00
    var fechaViaje = ""
    var numeroAsientosDisponibles = 0
    var numeroAsientosTotales = 0
    var detallesAdicionales = ""

    init {
        this.id = id
        this.nombreViaje = nombre
        this.costoPorPersona = costo
        this.fechaViaje = fecha
        this.numeroAsientosDisponibles = asientos_disponibles
        this.numeroAsientosTotales = asientos_totales
        this.detallesAdicionales = detalles
    }


}