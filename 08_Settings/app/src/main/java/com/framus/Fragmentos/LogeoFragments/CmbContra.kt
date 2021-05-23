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
import androidx.preference.PreferenceManager
import com.framus.BaseDeDatos.appDatabase
import com.framus.BaseDeDatos.usuarioDao
import com.framus.a08_settings.R

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
    //Casilla de ingreso de la contraseña nueva 2
    lateinit var casilla_contra_n2: EditText
    //Creo el boton de modificacion de contraseña
    lateinit var btn_contra: Button
    //Imagen pulsable para las preferencias
    lateinit var prefes: ImageView
    //El frame
    lateinit var root_layout: ConstraintLayout
    //Variables la base de datos
    private var db: appDatabase? = null
    private var usuarioDao: usuarioDao? = null

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
        //Casilla contraseña  nueva 1
        casilla_contra_n2 = v.findViewById(R.id.cmb_nueva_2)
        //Boton de modificacion de contraseña
        btn_contra = v.findViewById(R.id.cambio_ctr)
        //Imagen pulsable para las preferencias
        prefes = v.findViewById(R.id.llave)
        //El frame
        root_layout = v.findViewById(R.id.frameLayout7)
        //Variables la base de datos
        db = appDatabase.getAppDataBase(v.context)
        usuarioDao = db?.usuarioDao()
        //Preferencias
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        //+++++Alteraciones segun las preferencias
        //Color de los botones
        btn_contra.setBackgroundColor(Color.parseColor(prefs.getString("Botones","")))
        //Color del fondo
        if (prefs.getBoolean("Fondo",false))
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.rojo)))
        else
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.black)))

        return v
    }
}