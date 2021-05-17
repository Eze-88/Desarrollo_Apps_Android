package com.framus.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.framus.a06_tabs.R

class Correccion : Fragment() {

    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //Creo el boton de correccion
    lateinit var btn_mod: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_correccion, container, false)

        //Boton de modificacion de contraseña
        btn_mod = v.findViewById(R.id.Correccion)

        return v
    }

    override fun onStart() {
        super.onStart()

        btn_mod.setOnClickListener {
        }
    }
}