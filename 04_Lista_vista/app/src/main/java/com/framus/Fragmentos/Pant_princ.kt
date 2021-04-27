package com.framus.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.framus.a04_lista_vista.R

class Pant_princ : Fragment() {

    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //Creo el monitor
    lateinit var monitor: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pant_princ, container, false)

        //Monitor
        monitor = v.findViewById(R.id.cartel2)

        var usuario  = Pant_princArgs.fromBundle(requireArguments()).Usuario
        monitor.text = "El usuario ingresado es " + usuario

        return v
    }

    override fun onStart() {
        super.onStart()
    }
}