package com.framus.Entidades

class Persona (usuario: String, contrasenia: String){

    //Listado de atributos
    var usuario: String
    var contrasenia: String

    //Tomo los parámetros recibidos y se los asigno a los atributos
    init {
        this.usuario = usuario
        this.contrasenia = contrasenia
    }
}