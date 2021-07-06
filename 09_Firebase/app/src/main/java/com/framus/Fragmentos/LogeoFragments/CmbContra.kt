package com.framus.Fragmentos.LogeoFragments

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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.framus.BaseDeDatos.appDatabase
import com.framus.BaseDeDatos.usuarioDao
import com.framus.Entidades.Persona
import com.framus.a09_firebase.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CmbContra : Fragment() {

    //+++++DEFINICIONES GLOBALES+++++
    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //Casilla de ingreso de usuario
    lateinit var casilla_usuario: EditText
    //Casilla de ingreso de la contraseña vieja
    lateinit var casilla_contra_v: EditText
    //Casilla de ingreso de la contraseña nueva 1
    lateinit var casilla_contra_n1: EditText
    //Creo el boton de modificacion de contraseña
    lateinit var btn_contra: Button
    //Imagen pulsable para las preferencias
    lateinit var prefes: ImageView
    //El frame
    lateinit var root_layout: ConstraintLayout
    //Variable para la autenticacion por Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_cmb_contra, container, false)

        //+++++ASOCIACIONES+++++
        //Casilla usuario
        casilla_usuario= v.findViewById(R.id.cmb_usr)
        //Casilla contraseña vieja
        casilla_contra_v = v.findViewById(R.id.cmb_vieja_ctr)
        //Casilla contraseña  nueva 1
        casilla_contra_n1 = v.findViewById(R.id.cmb_nueva_1)
        //Boton de modificacion de contraseña
        btn_contra = v.findViewById(R.id.cambio_ctr)
        //Imagen pulsable para las preferencias
        prefes = v.findViewById(R.id.llave)
        //El frame
        root_layout = v.findViewById(R.id.frameLayout7)

        //Boton de preferencias
        prefes.setOnClickListener {
            val action = CmbContraDirections.actionCmbContraToSettingsActivity()
            v.findNavController().navigate(action)
        }

        return v
    }

    override fun onStart() {
        super.onStart()

        // Initialize Firebase Auth
        auth = Firebase.auth

        //Preferencias
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        //+++++Alteraciones segun las preferencias
        //Color de los botones
        btn_contra.setBackgroundColor(Color.parseColor(prefs.getString("Botones","#0000FF")))
        //Color del fondo
        if (prefs.getBoolean("Fondo",false))
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.rojo)))
        else
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.black)))

        //Accion para la modificación de la contraseña
        btn_contra.setOnClickListener {
            if (casilla_usuario.length() > 0) {
                if (casilla_contra_v.length() > 0) {
                    auth.signInWithEmailAndPassword(casilla_usuario.text.toString(), casilla_contra_v.text.toString())
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                val user = Firebase.auth.currentUser
                                val newPassword = casilla_contra_n1.text.toString()
                                user!!.updatePassword(newPassword)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Log.d("LOGIN", "User password updated.")
                                        }
                                    }
                                Log.d("LOGIN", "signInWithEmail:success")
                            } else {
                                Log.w("LOGIN", "signInWithEmail:failure", task.exception)
                                Snackbar.make(root_layout, task.exception.toString(), Snackbar.LENGTH_SHORT).show()
                            }
                        }
                } else
                    Snackbar.make(root_layout, "Contraseña en blanco", Snackbar.LENGTH_SHORT).show()
            } else
                Snackbar.make(root_layout, "Ingrese el usuario", Snackbar.LENGTH_SHORT).show()
        }
    }
}