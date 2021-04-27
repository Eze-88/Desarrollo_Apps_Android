package com.framus.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.framus.Adaptadores.AdaptadorDiscos
import com.framus.Entidades.Discos
import com.framus.a04_lista_vista.R
import com.google.android.material.snackbar.Snackbar

class Pant_princ : Fragment() {

    //Definicion de la variable para referenciar la vista
    lateinit var v: View

    lateinit var recDiscos: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager

    var discos : MutableList<Discos> = ArrayList<Discos>()
    private lateinit var discosListAdapter: AdaptadorDiscos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pant_princ, container, false)

        recDiscos = v.findViewById(R.id.rec_discos)

        return v
    }

    override fun onStart() {
        super.onStart()

        recDiscos.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recDiscos.layoutManager = linearLayoutManager

        discosListAdapter = AdaptadorDiscos(discos) { x ->
            onItemClick(x)
        }

        //  discosListAdapter = DiscoListAdapter(discos)

        recDiscos.adapter = discosListAdapter
    }

    fun onItemClick ( position : Int ) : Boolean {
        Snackbar.make(v,position.toString(),Snackbar.LENGTH_SHORT).show()
        return true
    }
}