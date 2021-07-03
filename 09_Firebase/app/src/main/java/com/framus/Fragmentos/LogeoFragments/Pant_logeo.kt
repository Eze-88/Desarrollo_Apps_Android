package com.framus.Fragmentos.LogeoFragments

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.framus.BaseDeDatos.usuarioDao
import com.framus.BaseDeDatos.appDatabase
import com.framus.Entidades.Persona
import com.framus.a09_firebase.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Pant_logeo : Fragment() {

    //+++++DEFINICIONES GLOBALES+++++
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
    //Casilla de ingreso de usuario
    lateinit var casilla_usuario: EditText
    //Casilla de ingreso de la contraseña
    lateinit var casilla_contra: EditText
    //Imagen pulsable para las preferencias
    lateinit var prefes: ImageView
    //El frame
    lateinit var root_layout: ConstraintLayout
    //Definición de las variables la base de datos
    private var db: appDatabase? = null
    private var usuarioDao: usuarioDao? = null
    //Lista para las verificaciones
    lateinit var userList :MutableList<Persona>
    //Bandera que indica si se encontró el usuario en la lista
    var encontrado : Boolean = false
    //Generador del ID de usuario
    var gen_id: Int = 0
    private lateinit var auth: FirebaseAuth


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
        //Casilla usuario
        casilla_usuario= v.findViewById(R.id.casilla_usuario)
        //Casilla contraseña
        casilla_contra = v.findViewById(R.id.casilla_contra)
        //Imagen pulsable para las preferencias
        prefes = v.findViewById(R.id.llave3)
        //El frame
        root_layout = v.findViewById(R.id.frameLayout)
        //Asociacion de las variables la base de datos
        db = appDatabase.getAppDataBase(v.context)
        usuarioDao = db?.usuarioDao()

        //Boton de preferencias
        prefes.setOnClickListener {
            val action = Pant_logeoDirections.actionPantLogeoToSettingsActivity()
            v.findNavController().navigate(action)
        }

        return v
    }

    override fun onStart() {
        super.onStart()

        // Initialize Firebase Auth
        auth = Firebase.auth

        //Alta usuario
        //Solo correos
        //Verifica que la contraseña sea de por lo menos 6 caracteres
        //Verifica si el usuario ya existe
//        auth.createUserWithEmailAndPassword("ezequielap@gmail.com", "ezeeze")
//            .addOnCompleteListener(requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w("PERRO", "createUserWithEmail:failure", task.exception)
//                }
//            }
        //Login de usuario
        //NO verifica usuario vacio y se rompe
        //NO verifica contraseña vacia y se rompe
        //Verifica si el ususaio existe
        //Verifica contraseña
//        auth.signInWithEmailAndPassword("ezequielap@gmail.com", "comiendo")
//            .addOnCompleteListener(requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d("PERRO", "signInWithEmail:success")
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w("PERRO", "signInWithEmail:failure", task.exception)
//                }
//            }
        // Modificar contraseña de usuario
        // El susuario tiene que estar logeado
//        val user = Firebase.auth.currentUser
//        val newPassword = "comiendo"
//        user!!.updatePassword(newPassword)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("PERRO", "User password updated.")
//                }
//            }
        // BORRAR USUARIO
        // El usuario tiene que estar logeado
//        val user = Firebase.auth.currentUser!!
//
//        user.delete()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("PERRO", "User account deleted.")
//                }
//            }

        //Preferencias
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        //+++++Alteraciones segun las preferencias
        //Color de los botones
        btn_logeo.setBackgroundColor(Color.parseColor(prefs.getString("Botones","#0000FF")))
        btn_signup.setBackgroundColor(Color.parseColor(prefs.getString("Botones","#0000FF")))
        //Color del fondo
        if (prefs.getBoolean("Fondo",false))
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.rojo)))
        else
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.black)))

        //Acción del botón de ingreso con usuario existente
        btn_logeo.setOnClickListener {
            encontrado = false
            if (casilla_usuario.length() > 0) {
                if (casilla_contra.length() > 0) {
                    auth.signInWithEmailAndPassword(casilla_usuario.text.toString(), casilla_contra.text.toString())
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                val action = Pant_logeoDirections.actionPantLogeoToPantPrinc()
                                v.findNavController().navigate(action)
                                Log.d("PERRO", "signInWithEmail:success")
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("PERRO", "signInWithEmail:failure", task.exception)
                                Snackbar.make(root_layout, task.exception.toString(), Snackbar.LENGTH_SHORT).show()

                            }
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
                auth.createUserWithEmailAndPassword(casilla_usuario.text.toString(), casilla_contra.text.toString())
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val action = Pant_logeoDirections.actionPantLogeoToPantPrinc()
                            v.findNavController().navigate(action)
                            Log.d("PERRO", "signInWithEmail:success")
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("PERRO", "createUserWithEmail:failure", task.exception)
                            Snackbar.make(root_layout, task.exception.toString(), Snackbar.LENGTH_SHORT).show()
                        }
                    }
            }
            else
                Snackbar.make(root_layout, "Ingrese el usuario a registrar", Snackbar.LENGTH_SHORT).show()
        }

        //Accion del boton de baja de usuario
        btn_baja.setOnClickListener {
            val action = Pant_logeoDirections.actionPantLogeoToBajaUsr()
            v.findNavController().navigate(action)
        }

        //Accion para la modificación de la contraseña
        btn_contra.setOnClickListener {
            val action = Pant_logeoDirections.actionPantLogeoToCmbContra()
            v.findNavController().navigate(action)
        }
    }
}
