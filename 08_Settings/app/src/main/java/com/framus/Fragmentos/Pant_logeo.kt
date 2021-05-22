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
import com.framus.a08_settings.R
import com.google.android.material.snackbar.Snackbar

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
    //Creo el boton de baja
    lateinit var btn_baja: Button
    //Creo el boton de modificacion de contraseña
    lateinit var btn_contra: Button
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
    //Generador del ID de usuario
    var gen_id: Int = 0
    //Variable para las verificaciones
    lateinit var userList :MutableList<Persona>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        //Boton de registro
        btn_baja = v.findViewById(R.id.Baja)
        //Boton de modificacion de contraseña
        btn_contra = v.findViewById(R.id.Contrasenia)
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

        //Acción del botón de ingreso con usuario existente
        btn_logeo.setOnClickListener {
            //Me aseguro que la bandera está desactivada
            encontrado = false
            if (casilla_usuario.length() > 0) {
                if (casilla_contra.length() > 0) {
                    userList = usuarioDao?.loadAllPersons() as MutableList<Persona>
                    for (cont in 0 until userList.size) {
                        if (userList[cont].usuario.equals(casilla_usuario.text.toString()))
                            encontrado = true
                        if (encontrado) {
                            if (userList[cont].contrasenia.equals(casilla_contra.text.toString())) {
                                val action = Pant_logeoDirections.actionPantLogeoToPantPrinc()
                                v.findNavController().navigate(action)
                                break
                            }
                            else {
                                Snackbar.make(root_layout, "Contraseña incorrecta", Snackbar.LENGTH_SHORT).show()
                                break
                            }
                        }
                        if (cont == (userList.size - 1))
                            Snackbar.make(root_layout, "Usuario no registrado", Snackbar.LENGTH_SHORT).show()
                    }
                } else
                    Snackbar.make(root_layout, "Contraseña en blanco", Snackbar.LENGTH_SHORT).show()
            } else
                Snackbar.make(root_layout, "Ingrese el usuario", Snackbar.LENGTH_SHORT).show()
        }

        //Acción del botón para el registro de usuario
        btn_signup.setOnClickListener {
            encontrado = false
            if (casilla_usuario.length() > 0){
                if (casilla_contra.length() > 0){
                    userList = usuarioDao?.loadAllPersons() as MutableList<Persona>
                    for (cont in 0 until userList.size){
                        if (userList[cont].usuario.equals(casilla_usuario.text.toString())) {
                            encontrado = true
                            break
                        }
                    }
                    if (encontrado)
                        Snackbar.make(root_layout, "Usuario ya existente", Snackbar.LENGTH_SHORT).show()
                    else {
                        gen_id = (0..9999).random()
                        usuarioDao?.insertPerson(Persona(gen_id, casilla_usuario.text.toString(), casilla_contra.text.toString()))
                        Snackbar.make(root_layout, "Registro de usuario OK", Snackbar.LENGTH_SHORT).show()
                    }
                }
                else
                    Snackbar.make(root_layout, "Ingrese una contraseña", Snackbar.LENGTH_SHORT).show()
            }
            else
                Snackbar.make(root_layout, "Ingrese el usuario a registrar", Snackbar.LENGTH_SHORT).show()
        }

        //Accion del boton de baja de usuario
        btn_baja.setOnClickListener {
            encontrado = false
            if (casilla_usuario.length() > 0) {
                if (casilla_contra.length() > 0) {
                    userList = usuarioDao?.loadAllPersons() as MutableList<Persona>
                    for (cont in 0 until userList.size) {
                        if (userList[cont].usuario.equals(casilla_usuario.text.toString()))
                            encontrado = true
                        if (encontrado) {
                            if (userList[cont].contrasenia.equals(casilla_contra.text.toString())) {
                                usuarioDao?.delete(Persona(userList[cont].id,"",""))
                                Snackbar.make(root_layout, "Usuario eliminado", Snackbar.LENGTH_SHORT).show()
                                break
                            }
                            else {
                                Snackbar.make(root_layout, "Contraseña incorrecta", Snackbar.LENGTH_SHORT).show()
                                break
                            }
                        }
                        if (cont == (userList.size - 1))
                            Snackbar.make(root_layout, "Usuario no registrado", Snackbar.LENGTH_SHORT).show()
                    }
                } else
                    Snackbar.make(root_layout, "Contraseña en blanco", Snackbar.LENGTH_SHORT).show()
            } else
                Snackbar.make(root_layout, "Ingrese el usuario", Snackbar.LENGTH_SHORT).show()
        }

        //Accion para la modificación de la contraseña
        btn_contra.setOnClickListener {
            encontrado = false
            if (casilla_usuario.length() > 0){
                if (casilla_contra.length() > 0){
                    userList = usuarioDao?.loadAllPersons() as MutableList<Persona>
                    for (cont in 0 until userList.size){
                        if (userList[cont].usuario.equals(casilla_usuario.text.toString()))
                            encontrado = true
                        if (encontrado) {
                            usuarioDao?.updatePerson(Persona(userList[cont].id,casilla_usuario.text.toString(),casilla_contra.text.toString()))
                            Snackbar.make(root_layout, "Modificacion exitosa"+" "+cont.toString(), Snackbar.LENGTH_SHORT).show()
                            break
                        }
                        if (cont == (userList.size - 1))
                            Snackbar.make(root_layout, "El usuario no existe", Snackbar.LENGTH_SHORT).show()
                    }
                }
                else
                    Snackbar.make(root_layout, "Ingrese una contraseña", Snackbar.LENGTH_SHORT).show()
            }
            else
                Snackbar.make(root_layout, "Ingrese el usuario a modificar", Snackbar.LENGTH_SHORT).show()
        }
    }
}