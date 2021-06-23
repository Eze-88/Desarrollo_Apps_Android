package com.framus.Fragmentos

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.framus.BaseDeDatos.appDatabase
import com.framus.BaseDeDatos.discosDAO
import com.framus.Entidades.Discos
import com.framus.a08_settings.R

class Detalles_B : Fragment() {

    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //El frame
    lateinit var root_layout: ConstraintLayout
    //Definición de las variables la base de datos
    private var db: appDatabase? = null
    private var discosDAO: discosDAO? = null
    //Variable auxiliar para encontrar el disco a mostrar
    var cd:  MutableList<Discos> = mutableListOf()
    //Variable auxiliar para identificar el disco a mostrar
    var pos: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_detalles__b, container, false)

        //+++++ASOCIACIONES+++++
        //El frame
        root_layout = v.findViewById(R.id.frameLayout6)
        //Asociacion de las variables la base de datos
        db = appDatabase.getAppDataBase(v.context)
        discosDAO = db?.discosDAO()

        //Identifico el disco a mostrar
        cd = discosDAO?.loadAllPersons() as MutableList<Discos>
        val id: Int = Detalles_A.identificador
        for ( i in 0 until cd.size){
            if (cd[i].id == id){
                pos = i
                break
            }
        }

        //Muestro la caratula
        Glide
            .with(requireContext())
            .load(cd[pos].caratula)
            .centerInside()
            .into(getImageView())
        getImageView()

        //Preferencias
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        //+++++Alteraciones segun las preferencias
        //Color del fondo
        if (prefs.getBoolean("Fondo",false))
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.rojo)))
        else
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.black)))

        return v
    }

    fun getImageView () : ImageView {
        return v.findViewById(R.id.caratula_grande)
    }

    companion object{
        var identificador: Int = 0
    }
}