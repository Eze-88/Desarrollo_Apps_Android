package com.framus.Entidades

class Discos (banda: String, titulo: String, anio: String, genero: String, caratula: String) {

    //Listado de atributos
    var banda: String
    var titulo: String
    var anio: String
    var genero: String
    var caratula: String

    //Tomo los parámetros recibidos y se los asigno a los atributos
    init {
        this.banda = banda
        this.titulo = titulo
        this.anio = anio
        this.genero = genero
        this.caratula = caratula
    }
}