package com.framus.a02_logeo_usuario

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.framus.Entidades.Persona
import com.google.android.material.snackbar.Snackbar

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

        //Defino el snackbar para el ususario
        val root_layout: ConstraintLayout = findViewById(R.id.root_layout)

        //Ingreso del usuario
        val casilla_usuario: EditText = findViewById(R.id.casilla_usuario)

        //Pruebo el ingreso del texto
        val login: Button = findViewById(R.id.login)
        login.setOnClickListener {
            if (casilla_usuario.length()>0)
                cartel.text = casilla_usuario.text
            else
                Snackbar.make(root_layout,"Ingrese el usuario", Snackbar.LENGTH_SHORT).show()
        }
    }
}