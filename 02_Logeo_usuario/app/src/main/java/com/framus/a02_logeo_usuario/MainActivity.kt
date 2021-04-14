package com.framus.a02_logeo_usuario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.framus.Entidades.Persona

class MainActivity : AppCompatActivity() {

    //Creo la variable del tipo Persona
    lateinit var miUser : Persona

    //Creo la lista de usuarios
    var Personas : MutableList<Persona> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Definición de la variable para asociar al monitor
        val cartel: TextView = findViewById(R.id.Monitor)
        //Se deja en blanco
        cartel.text = ""



        //Cargo hardcoded la variable creada para probar
        miUser = Persona ("Eze", "1935")

        //Imprimo en el LOG la variable creada
        Log.d("PRUEBA",miUser.usuario+" "+miUser.contrasenia)
    }
}