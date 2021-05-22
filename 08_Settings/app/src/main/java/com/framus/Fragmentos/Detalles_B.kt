package com.framus.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.framus.BaseDeDatos.appDatabase
import com.framus.BaseDeDatos.discosDAO
import com.framus.Entidades.Discos
import com.framus.a08_settings.R

class Detalles_B : Fragment() {

    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //Definición de las variables la base de datos
    private var db: appDatabase? = null
    private var discosDAO: discosDAO? = null

    var cd:  MutableList<Discos> = mutableListOf()
    var pos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_detalles__b, container, false)

        db = appDatabase.getAppDataBase(v.context)
        discosDAO = db?.discosDAO()

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

        return v
    }

    fun getImageView () : ImageView {
        return v.findViewById(R.id.caratula_grande)
    }

    companion object{
        var identificador: Int = 0
    }
}