package com.framus.Entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class Persona (id: Int, usuario: String, contrasenia: String){

    //Identificador del usuario, el que nunca se debe repetir
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int

    //Listado de atributos
    @ColumnInfo(name = "usuario")
    var usuario: String
    @ColumnInfo(name = "contrasenia")
    var contrasenia: String

    //Tomo los parámetros recibidos y se los asigno a los atributos
    init {
        this.id = id
        this.usuario = usuario
        this.contrasenia = contrasenia
    }
}