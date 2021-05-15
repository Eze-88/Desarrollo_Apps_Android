package com.framus.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.framus.Adaptadores.AdaptadorDiscos
import com.framus.Entidades.Discos
import com.framus.a06_tabs.R
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

        //+++++PRE-CARGA DE LA LISTA de discos+++++
        discos.add(Discos("Pearl Jam","Vs","1993","Grunge","https://img.discogs.com/XaZw9d4nux7zQCwVMp3USt2F6QY=/fit-in/600x600/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-1820450-1245546969.jpeg.jpg"))
        discos.add(Discos("The Black Keys","El Camino","2011","Garage Rock","https://images-na.ssl-images-amazon.com/images/I/810GnasrfjL._SX466_.jpg"))
        discos.add(Discos("Deftones","Deftones","2003","Metal Alternativo","https://media.pitchfork.com/photos/5929a8fa5e6ef95969321323/1:1/w_320/b3e6b384.jpg"))
        discos.add(Discos("The Offspring","Ignition","1992","Skate Punk","https://img.discogs.com/k3QfPGvxwGt3G-k5RofPajdbnko=/fit-in/300x300/filters:strip_icc():format(jpeg):mode_rgb():quality(40)/discogs-images/R-4892277-1458203046-3312.jpeg.jpg"))
        discos.add(Discos("Dinosaur Jr","I Bet On Sky","2012","Indie Rock","https://upload.wikimedia.org/wikipedia/en/c/c4/I_Bet_on_Sky.jpeg"))

        return v
    }

    override fun onStart() {
        super.onStart()

        recDiscos.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recDiscos.layoutManager = linearLayoutManager
        //Le paso el listado de discos al Adaptador
        discosListAdapter = AdaptadorDiscos(discos, requireContext()) { x -> onItemClick(x) }
        recDiscos.adapter = discosListAdapter
    }

    fun onItemClick ( position : Int ) : Boolean {
        return true
    }
}