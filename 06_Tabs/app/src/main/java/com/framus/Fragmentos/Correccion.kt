package com.framus.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import com.framus.BaseDeDatos.appDatabase
import com.framus.BaseDeDatos.discosDAO
import com.framus.Entidades.Discos
import com.framus.a06_tabs.R
import kotlinx.android.synthetic.main.fragment_correccion.*

class Correccion : Fragment() {

    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //Creo el boton de correccion
    lateinit var btn_mod: Button
    //Casillas
    lateinit var casilla_banda: EditText
    lateinit var casilla_titulo: EditText
    lateinit var casilla_anio: EditText
    lateinit var casilla_genero: EditText
    lateinit var casilla_cover: EditText


    //Definición de las variables la base de datos
    private var db: appDatabase? = null
    private var discosDAO: discosDAO? = null

    var pos: Int = 0

    var cd:  MutableList<Discos> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        //Casillas
        casilla_banda= v.findViewById(R.id.casilla_banda_co)
        casilla_titulo= v.findViewById(R.id.casilla_titulo_co)
        casilla_anio= v.findViewById(R.id.casilla_anio_co)
        casilla_genero= v.findViewById(R.id.casilla_genero_co)
        casilla_cover= v.findViewById(R.id.casilla_cover_co)

        return v
    }

    override fun onStart() {
        super.onStart()

        var banda: String
        var titulo: String
        var anio: String
        var genero: String
        var cover: String

        cd = discosDAO?.loadAllPersons() as MutableList<Discos>

        var id: Int = CorreccionArgs.fromBundle(requireArguments()).id

        for ( i in 0 until cd.size){
            if (cd[i].id == id){
                pos = i
                break
            }
        }

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
        }
    }
}