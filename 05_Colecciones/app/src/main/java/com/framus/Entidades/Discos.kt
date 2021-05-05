package com.framus.Entidades

import android.os.Parcel
import android.os.Parcelable

class Discos (banda: String, titulo: String, anio: String, genero: String, caratula: String) : Parcelable {

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

    constructor(source: Parcel) : this(
            source.readString()!!,
            source.readString()!!,
            source.readString()!!,
            source.readString()!!,
            source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(banda)
        writeString(titulo)
        writeString(anio)
        writeString(genero)
        writeString(caratula)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Discos> = object : Parcelable.Creator<Discos> {
            override fun createFromParcel(source: Parcel): Discos = Discos(source)
            override fun newArray(size: Int): Array<Discos?> = arrayOfNulls(size)
        }
    }
}