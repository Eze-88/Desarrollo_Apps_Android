package com.framus.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import com.framus.Entidades.Discos
import com.framus.Entidades.Persona
import com.framus.a05_colecciones.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_pant_logeo.*
import kotlinx.android.synthetic.main.fragment_pant_logeo.view.*

class Pant_logeo : Fragment() {

    //+++++DEFINICIONES GLOBALES+++++
    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //Creo el boton de logeo
    lateinit var btn_logeo: Button
    //Creo el boton de registro
    lateinit var btn_signup: Button
    //Creo el monitor
    lateinit var monitor: TextView
    //Creo la lista de usuarios
    var Personas : MutableList<Persona> = mutableListOf()
    //Creo la lista de discos
    var Discos : MutableList<Discos> = mutableListOf()
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
        //Monitor
        monitor = v.findViewById(R.id.cartel)
        monitor.text = ""
        //Snackbar
        root_layout = v.findViewById(R.id.frameLayout)
        //Casilla usuario
        casilla_usuario= v.findViewById(R.id.casilla_usuario)
        //Casilla contraseña
        casilla_contra = v.findViewById(R.id.casilla_contra)
        //+++++PRE-CARGA DE LA LISTA+++++
        Discos.add(Discos("Dinosaur Jr","I Bet On Sky","2012","Indie Rock","https://upload.wikimedia.org/wikipedia/en/c/c4/I_Bet_on_Sky.jpeg"))
        Personas.add(Persona("Eze","Eze", Discos))
//        Personas.add(Persona("Pablo","Pablo"))
//        Personas.add(Persona("Jorge","Jorge"))
//        Personas.add(Persona("Tito","Tito"))

        return v
    }

    override fun onStart() {
        super.onStart()

        //Acción del botón de ingreso con usuario existente
        btn_logeo.setOnClickListener {
            //Me aseguro que la bandera está desactivada
            encontrado = false
            if (casilla_usuario.length() > 0) {
                if (casilla_contra.length() > 0) {
                    for (cont in 0 until Personas.size) {
                        if (Personas[cont].usuario.equals(casilla_usuario.text.toString()))
                            encontrado = true
                        if (encontrado) {
                            if (Personas[cont].contrasenia.equals(casilla_contra.text.toString())) {
                                val action = Pant_logeoDirections.actionPantLogeoToPantPrinc(Personas[cont].usuario,Personas[cont].cds.toTypedArray())
                                v.findNavController().navigate(action)
                                break
                            } else {
                                cartel.text = "Contraseña incorrecta"
                                break
                            }
                        }
                        if (cont == (Personas.size - 1))
                            cartel.text = "Usuario no registrado"
                    }
                } else
                    Snackbar.make(root_layout, "Contraseña en blanco", Snackbar.LENGTH_SHORT).show()
            } else
                Snackbar.make(root_layout, "Ingrese el usuario", Snackbar.LENGTH_SHORT).show()
        }

        //Acción del botón para el registro de usuario
        btn_signup.setOnClickListener {
            if (casilla_usuario.length() > 0){
                if (casilla_contra.length() > 0){
                    for (cont in 0 until Personas.size){
                        if (Personas[cont].usuario.equals(casilla_usuario.text.toString())) {
                            cartel.text = "Usuario ya registrado"
                            break
                        }
                        if (cont == (Personas.size - 1)){
                            Discos.add(Discos("","","","","https://static.wikia.nocookie.net/temonpe/images/c/cd/Cd.gif/revision/latest?cb=20100930214539&path-prefix=es"))
                            Personas.add(Persona(casilla_usuario.text.toString(),casilla_contra.text.toString(), Discos))
                            cartel.text = "Registro de usuario correcto"
                        }
                    }
                }
                else
                    Snackbar.make(root_layout, "Ingrese una contraseña", Snackbar.LENGTH_SHORT).show()
            }
            else
                Snackbar.make(root_layout, "Ingrese el usuario a registrar", Snackbar.LENGTH_SHORT).show()
        }
    }
}
