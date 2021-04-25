package com.framus.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.framus.Entidades.Persona
import com.framus.a03_fragmentos.R
import kotlinx.android.synthetic.main.fragment_pant_logeo.*
import kotlinx.android.synthetic.main.fragment_pant_logeo.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [Pant_logeo.newInstance] factory method to
 * create an instance of this fragment.
 */
class Pant_logeo : Fragment() {

    //+++++DEFINICIONES GLOBALES+++++
    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //Creo el boton de logeo
    lateinit var btn_logeo: Button
    //Creo el monitor
    lateinit var monitor: TextView
    //Creo la lista de usuarios
    var Personas : MutableList<Persona> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_pant_logeo, container, false)

        //+++++ASOCIACIONES+++++
        //Boton de ingreso
        btn_logeo = v.findViewById(R.id.login)
        btn_logeo.text = "Ingresar"
        //Monitor
        monitor = v.findViewById(R.id.cartel)
        monitor.text = ""

        return v
    }

    override fun onStart() {
        super.onStart()

        btn_logeo.setOnClickListener {
            val action = Pant_logeoDirections.actionPantLogeoToPantPrinc()
            v.findNavController().navigate(action)
        }
    }
}