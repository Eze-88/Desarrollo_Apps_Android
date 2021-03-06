package com.framus.Fragmentos

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.framus.a09_firebase.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class Detalles_B : Fragment() {

    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //El frame
    lateinit var root_layout: ConstraintLayout
    //Base de datos online
    private val bd = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_detalles__b, container, false)

        //+++++ASOCIACIONES+++++
        //El frame
        root_layout = v.findViewById(R.id.frameLayout6)

        bd.collection("albums").document(Detalles_A.identificador.toString()).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot != null){
                val cd = dataSnapshot.toObject<Discos>()
                if (cd != null) {
                    Log.d("LOG_BD","Exito")
                    //Muestro la caratula
                    Glide
                        .with(requireContext())
                        .load(cd.caratula)
                        .centerInside()
                        .into(getImageView())
                    getImageView()
                }
            } else {
                Log.d("ERROR_BD", "No existe el documento")
            }
        }

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