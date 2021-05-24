package com.framus.Fragmentos.LogeoFragments

import android.graphics.Color
import android.os.Bundle
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
import com.framus.a08_settings.R
import com.google.android.material.snackbar.Snackbar

class BajaUsr : Fragment() {

    //+++++DEFINICIONES GLOBALES+++++
    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //Casilla de ingreso de usuario
    lateinit var casilla_usuario: EditText
    //Casilla de ingreso de la contraseña
    lateinit var casilla_contra: EditText
    //Creo el boton de baja de uruario
    lateinit var btn_baja: Button
    //Imagen pulsable para las preferencias
    lateinit var prefes: ImageView
    //El frame
    lateinit var root_layout: ConstraintLayout
    //Variables la base de datos
    private var db: appDatabase? = null
    private var usuarioDao: usuarioDao? = null
    //Bandera que indica si se encontró el usuario en la lista
    var encontrado : Boolean = false
    //Lista para las verificaciones
    lateinit var userList :MutableList<Persona>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_baja_usr, container, false)

        //+++++ASOCIACIONES+++++
        //Casilla usuario
        casilla_usuario= v.findViewById(R.id.baja_usr)
        //Casilla contraseña
        casilla_contra = v.findViewById(R.id.baja_ctr)
        //Boton de baja de usuario
        btn_baja = v.findViewById(R.id.baja)
        //Imagen pulsable para las preferencias
        prefes = v.findViewById(R.id.llave2)
        //El frame
        root_layout = v.findViewById(R.id.frameLayout8)
        //Variables la base de datos
        db = appDatabase.getAppDataBase(v.context)
        usuarioDao = db?.usuarioDao()

        //Boton de preferencias
        prefes.setOnClickListener {
            val action = BajaUsrDirections.actionBajaUsrToSettingsActivity()
            v.findNavController().navigate(action)
        }

        return v
    }

    override fun onStart() {
        super.onStart()

        //Preferencias
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        //+++++Alteraciones segun las preferencias
        //Color de los botones
        btn_baja.setBackgroundColor(Color.parseColor(prefs.getString("Botones","#0000FF")))
        //Color del fondo
        if (prefs.getBoolean("Fondo",false))
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.rojo)))
        else
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.black)))

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
                }
                else
                    Snackbar.make(root_layout, "Contraseña en blanco", Snackbar.LENGTH_SHORT).show()
            }
            else
                Snackbar.make(root_layout, "Ingrese el usuario", Snackbar.LENGTH_SHORT).show()
        }
    }
}