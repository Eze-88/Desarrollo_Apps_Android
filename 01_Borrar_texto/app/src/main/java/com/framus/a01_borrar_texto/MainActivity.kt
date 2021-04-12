package com.framus.a01_borrar_texto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Definición de la variable cartel y la asociacion al mensaje a mostrar
        val cartel: TextView = findViewById(R.id.cartel)
        //Se define el mensaje mostrado
        cartel.text = "Borrame"

        //Establezco el valor inicial del tamaño del texto, para tenerlo conocido
        var tamanio = cartel.textSize
        cartel.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanio)

        //CAMBIO EL TEXTO
        //Definición de la variable boton_borrado y la asociación al boton
        val boton_borrado: Button = findViewById(R.id.boton_borrado)
        //Se define la acción al presionar el boton
        boton_borrado.setOnClickListener {
            cartel.text = ":|"
        }

        //ACHICO EL ETXTO
        //Definición de la variable boton_menos y la asociación al boton
        val boton_menos: Button = findViewById(R.id.boton_menos)
        //Se define la acción al presionar el boton
        boton_menos.setOnClickListener {
            tamanio = tamanio - 1
            cartel.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanio)
        }

        //AGRANDO EL TEXTO
        //Definición de la variable boton_mas y la asociación al boton
        val boton_mas: Button = findViewById(R.id.boton_mas)
        //Se define la acción al presionar el boton
        boton_mas.setOnClickListener {
            tamanio = tamanio + 1
            cartel.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanio)
        }
    }
}