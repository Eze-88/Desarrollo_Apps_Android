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

        //+++++DEFINICIONES+++++
        //Definición de la variable para asociar al monitor
        val cartel: TextView = findViewById(R.id.Monitor)
        cartel.text = ""
        //Definición del botón para ingresar
        val login: Button = findViewById(R.id.login)
        login.text = "Ingresar"
        //Defino el snackbar para el ususario y la contraseña
        val root_layout: ConstraintLayout = findViewById(R.id.root_layout)
        //Casilla de ingreso de usuario
        val casilla_usuario: EditText = findViewById(R.id.casilla_usuario)
        //Casilla de ingreso de la contraseña
        val casilla_contra: EditText = findViewById(R.id.casilla_contra)

        //+++++ACCIONES+++++
        //Comportamiento del botón
        login.setOnClickListener {
            if (casilla_usuario.length()>0){
                if (casilla_contra.length()>0)
                    cartel.text = "Datos ingresados OK"
                else
                    Snackbar.make(root_layout,"Contraseña en blanco", Snackbar.LENGTH_SHORT).show()
            }
            else
                Snackbar.make(root_layout,"Ingrese el usuario", Snackbar.LENGTH_SHORT).show()
        }
    }
}