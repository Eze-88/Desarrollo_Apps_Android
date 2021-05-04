package com.framus.Entidades

class Persona (usuario: String, contrasenia: String, cds: MutableList<Discos>){

    //Listado de atributos
    var usuario: String
    var contrasenia: String
    var cds: MutableList<Discos>

    //Tomo los parámetros recibidos y se los asigno a los atributos
    init {
        this.usuario = usuario
        this.contrasenia = contrasenia
        this.cds = cds
    }
}