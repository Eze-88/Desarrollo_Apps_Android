package com.framus.Fragmentos

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.framus.BaseDeDatos.appDatabase
import com.framus.BaseDeDatos.discosDAO
import com.framus.Entidades.Discos
import com.framus.a09_firebase.R

class Correccion : Fragment() {

    //+++++DEFINICIONES GLOBALES+++++
    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //Creo el boton de correccion
    lateinit var btn_mod: Button
    //Casillas de la banda
    lateinit var casilla_banda: EditText
    //Casillas de el titulo
    lateinit var casilla_titulo: EditText
    //Casillas de el año
    lateinit var casilla_anio: EditText
    //Casillas de el genero
    lateinit var casilla_genero: EditText
    //Casillas de la caratula
    lateinit var casilla_cover: EditText
    //El frame
    lateinit var root_layout: ConstraintLayout
    //Definición de las variables la base de datos
    private var db: appDatabase? = null
    private var discosDAO: discosDAO? = null
    //Lista para las verificaciones
    var cd:  MutableList<Discos> = mutableListOf()
    //Variable para determinar la posicion en la lista
    var pos: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Asociacion de las variables la base de datos
        db = appDatabase.getAppDataBase(requireContext())
        discosDAO = db?.discosDAO()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_correccion, container, false)

        //Boton de modificacion de contraseña
        btn_mod = v.findViewById(R.id.Correccion)
        //Casillas de la banda
        casilla_banda= v.findViewById(R.id.casilla_banda_co)
        //Casillas de el titulo
        casilla_titulo= v.findViewById(R.id.casilla_titulo_co)
        //Casillas de el año
        casilla_anio= v.findViewById(R.id.casilla_anio_co)
        //Casillas de el genero
        casilla_genero= v.findViewById(R.id.casilla_genero_co)
        //Casillas de la caratula
        casilla_cover= v.findViewById(R.id.casilla_cover_co)
        //El frame
        root_layout = v.findViewById(R.id.frameLayout5)

        return v
    }

    override fun onStart() {
        super.onStart()

        //Preferencias
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        //+++++Alteraciones segun las preferencias
        //Color de los botones
        btn_mod.setBackgroundColor(Color.parseColor(prefs.getString("Botones","#0000FF")))
        //Color del fondo
        if (prefs.getBoolean("Fondo",false))
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.rojo)))
        else
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.black)))

        //Variables locales para la carga de los campos del disco a corregir
        var banda: String
        var titulo: String
        var anio: String
        var genero: String
        var cover: String

        cd = discosDAO?.loadAllPersons() as MutableList<Discos>

        //Determinacion de la posicion del disco a corregir
        var id: Int = CorreccionArgs.fromBundle(requireArguments()).id
        for ( i in 0 until cd.size){
            if (cd[i].id == id){
                pos = i
                break
            }
        }

        //Accion del boton corregir
        btn_mod.setOnClickListener {

            if (casilla_banda.length() > 0)
                banda = casilla_banda.text.toString()
            else
                banda = cd[pos].banda

            if (casilla_titulo.length() > 0)
                 titulo = casilla_titulo.text.toString()
            else
                titulo = cd[pos].titulo

            if (casilla_anio.length() > 0)
                anio = casilla_anio.text.toString()
            else
                anio = cd[pos].anio

            if (casilla_genero.length() > 0)
                genero = casilla_genero.text.toString()
            else
                genero = cd[pos].genero

            if (casilla_cover.length() > 0)
                cover = casilla_cover.text.toString()
            else
                cover = cd[pos].caratula

            discosDAO?.updatePerson(Discos(id,banda,titulo,anio,genero,cover))
            val action = CorreccionDirections.actionCorreccionToPantPrinc()
            v.findNavController().navigate(action)
        }
    }
}