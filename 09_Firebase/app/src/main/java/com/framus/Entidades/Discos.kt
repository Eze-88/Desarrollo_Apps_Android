package com.framus.Entidades

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "discos")
class Discos (id: Int, banda: String, titulo: String, anio: String, genero: String, caratula: String, latitud: Double, longitud: Double) : Parcelable {

    //Identificador del usuario, el que nunca se debe repetir
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int

    //Listado de atributos
    @ColumnInfo(name = "banda")
    var banda: String
    @ColumnInfo(name = "titulo")
    var titulo: String
    @ColumnInfo(name = "anio")
    var anio: String
    @ColumnInfo(name = "genero")
    var genero: String
    @ColumnInfo(name = "caratula")
    var caratula: String
    @ColumnInfo(name = "latitud")
    var lat: Double
    @ColumnInfo(name = "longitud")
    var long: Double

    constructor() : this(0,"","","","","",0.toDouble(),0.toDouble())

    //Tomo los parámetros recibidos y se los asigno a los atributos
    init {
        this.id = id
        this.banda = banda
        this.titulo = titulo
        this.anio = anio
        this.genero = genero
        this.caratula = caratula
        this.lat = latitud
        this.long = longitud
    }

    constructor(source: Parcel) :this(
            source.readInt()!!,
            source.readString()!!,
            source.readString()!!,
            source.readString()!!,
            source.readString()!!,
            source.readString()!!,
            source.readDouble()!!,
            source.readDouble()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(banda)
        writeString(titulo)
        writeString(anio)
        writeString(genero)
        writeString(caratula)
        writeDouble(lat)
        writeDouble(long)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Discos> = object : Parcelable.Creator<Discos> {
            override fun createFromParcel(source: Parcel): Discos = Discos(source)
            override fun newArray(size: Int): Array<Discos?> = arrayOfNulls(size)
        }
    }
}