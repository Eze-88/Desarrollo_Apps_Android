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
import com.framus.a09_firebase.R
import com.google.android.material.snackbar.Snackbar

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
    //Bandera que indica si se encontró el usuario en la lista
    var encontrado : Boolean = false
    //Lista para las verificaciones
    lateinit var userList :MutableList<Persona>

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

        //Boton de preferencias
        prefes.setOnClickListener {
            val action = CmbContraDirections.actionCmbContraToSettingsActivity()
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
        btn_contra.setBackgroundColor(Color.parseColor(prefs.getString("Botones","#0000FF")))
        //Color del fondo
        if (prefs.getBoolean("Fondo",false))
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.rojo)))
        else
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.black)))

        //Accion para la modificación de la contraseña
        btn_contra.setOnClickListener {
            encontrado = false
            if (casilla_usuario.length()>0){
                if (casilla_contra_v.length()>0){
                    if (casilla_contra_n1.length()>0){
                        if (casilla_contra_n2.length()>0){
                            userList = usuarioDao?.loadAllPersons() as MutableList<Persona>
                            for (cont in 0 until userList.size){
                                if (userList[cont].usuario.equals(casilla_usuario.text.toString()))
                                    encontrado = true
                                if (encontrado){
                                    if (userList[cont].contrasenia.equals(casilla_contra_v.text.toString())){
                                        if (casilla_contra_n1.text.toString().equals(casilla_contra_n2.text.toString())){
                                            usuarioDao?.updatePerson(Persona(userList[cont].id,casilla_usuario.text.toString(),casilla_contra_n1.text.toString()))
                                            Snackbar.make(root_layout, "Modificacion exitosa", Snackbar.LENGTH_SHORT).show()
                                            break
                                        }
                                        else {
                                            Snackbar.make(root_layout, "La contraseñas nuevas no coinciden", Snackbar.LENGTH_SHORT).show()
                                            break
                                            }
                                    }
                                    else {
                                        Snackbar.make(root_layout, "Contraseña incorrecta", Snackbar.LENGTH_SHORT).show()
                                        break
                                        }
                                }
                                if (cont == (userList.size - 1))
                                    Snackbar.make(root_layout, "El usuario no existe", Snackbar.LENGTH_SHORT).show()
                            }
                        }
                        else
                            Snackbar.make(root_layout, "Ingrese la nuevamente la nueva contraseña", Snackbar.LENGTH_SHORT).show()
                    }
                    else
                        Snackbar.make(root_layout, "Ingrese la nueva contraseña", Snackbar.LENGTH_SHORT).show()
                }
                else
                    Snackbar.make(root_layout, "Ingrese la contraseña actual", Snackbar.LENGTH_SHORT).show()
            }
            else
                Snackbar.make(root_layout, "Ingrese el usuario", Snackbar.LENGTH_SHORT).show()
        }
    }
}