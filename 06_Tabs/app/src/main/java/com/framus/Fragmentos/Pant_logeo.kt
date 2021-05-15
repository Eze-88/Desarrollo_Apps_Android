package com.framus.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.framus.BaseDeDatos.usuarioDao
import com.framus.BaseDeDatos.appDatabase
import com.framus.Entidades.Persona
import com.framus.a06_tabs.R
import com.google.android.material.snackbar.Snackbar
import com.wajahatkarim3.roomexplorer.RoomExplorer

class Pant_logeo : Fragment() {

    //+++++DEFINICIONES GLOBALES+++++
    //Definición de las variables la base de datos
    private var db: appDatabase? = null
    private var usuarioDao: usuarioDao? = null
    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //Creo el boton de logeo
    lateinit var btn_logeo: Button
    //Creo el boton de registro
    lateinit var btn_signup: Button
    //Creo la lista de usuarios
    var Personas : MutableList<Persona> = mutableListOf()
    //Bandera que indica si se encontró el usuario en la lista
    var encontrado : Boolean = false
    //Contador para recorrer la lista de usuarios
    val cont: Int = 0
    //Defino el snackbar para el ususario y la contraseña
    lateinit var root_layout: ConstraintLayout
    //Casilla de ingreso de usuario
    lateinit var casilla_usuario: EditText
    //Casilla de ingreso de la contraseña
    lateinit var casilla_contra: EditText

    var rnd: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        //+++++PRE-CARGA DE LA LISTA de usuarios+++++
//        Personas.add(Persona(1,"Eze", "Eze"))
//        Personas.add(Persona(2,"Pablo", "Pablo"))
//        Personas.add(Persona(3,"Jorge", "Jorge"))
//        Personas.add(Persona(4,"Tito", "Tito"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_pant_logeo, container, false)

        //+++++ASOCIACIONES+++++
        //Boton de ingreso
        btn_logeo = v.findViewById(R.id.login)
        btn_logeo.text = "Ingresar"
        //Boton de registro
        btn_signup = v.findViewById(R.id.Signup)
        btn_signup.text = "Registrarse"
        //Snackbar
        root_layout = v.findViewById(R.id.frameLayout)
        //Casilla usuario
        casilla_usuario= v.findViewById(R.id.casilla_usuario)
        //Casilla contraseña
        casilla_contra = v.findViewById(R.id.casilla_contra)

        db = appDatabase.getAppDataBase(v.context)
        usuarioDao = db?.usuarioDao()

        return v
    }

    override fun onStart() {
        super.onStart()

        btn_signup.setOnClickListener {
            rnd = (0..10).random()
            usuarioDao?.insertPerson(Persona(rnd, casilla_usuario.text.toString(), casilla_contra.text.toString()))
        }

//        //Acción del botón de ingreso con usuario existente
//        btn_logeo.setOnClickListener {
//            //Me aseguro que la bandera está desactivada
//            encontrado = false
//            if (casilla_usuario.length() > 0) {
//                if (casilla_contra.length() > 0) {
//                    for (cont in 0 until Personas.size) {
//                        if (Personas[cont].usuario.equals(casilla_usuario.text.toString()))
//                            encontrado = true
//                        if (encontrado) {
//                            if (Personas[cont].contrasenia.equals(casilla_contra.text.toString())) {
//                                val action = Pant_logeoDirections.actionPantLogeoToPantPrinc()
//                                v.findNavController().navigate(action)
//                                break
//                            } else {
//                                Snackbar.make(root_layout, "Contraseña incorrecta", Snackbar.LENGTH_SHORT).show()
//                                break
//                            }
//                        }
//                        if (cont == (Personas.size - 1))
//                            Snackbar.make(root_layout, "Usuario no registrado", Snackbar.LENGTH_SHORT).show()
//                    }
//                } else
//                    Snackbar.make(root_layout, "Contraseña en blanco", Snackbar.LENGTH_SHORT).show()
//            } else
//                Snackbar.make(root_layout, "Ingrese el usuario", Snackbar.LENGTH_SHORT).show()
//        }
//
//        //Acción del botón para el registro de usuario
//        btn_signup.setOnClickListener {
//            if (casilla_usuario.length() > 0){
//                if (casilla_contra.length() > 0){
//                    for (cont in 0 until Personas.size){
//                        if (Personas[cont].usuario.equals(casilla_usuario.text.toString())) {
//                            Snackbar.make(root_layout, "Usuario ya registrado", Snackbar.LENGTH_SHORT).show()
//                            break
//                        }
//                        if (cont == (Personas.size - 1)){
//                            Personas.add(Persona(cont,casilla_usuario.text.toString(),casilla_contra.text.toString()))
//                            Snackbar.make(root_layout, "Registro de usuario correcto", Snackbar.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//                else
//                    Snackbar.make(root_layout, "Ingrese una contraseña", Snackbar.LENGTH_SHORT).show()
//            }
//            else
//                Snackbar.make(root_layout, "Ingrese el usuario a registrar", Snackbar.LENGTH_SHORT).show()
//        }
    }
}
