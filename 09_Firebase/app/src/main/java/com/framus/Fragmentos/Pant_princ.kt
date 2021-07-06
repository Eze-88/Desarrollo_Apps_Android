package com.framus.Fragmentos

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.framus.Adaptadores.AdaptadorDiscos
import com.framus.BaseDeDatos.appDatabase
import com.framus.BaseDeDatos.discosDAO
import com.framus.Entidades.Discos
import com.framus.a09_firebase.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class Pant_princ : Fragment() {

    //+++++DEFINICIONES GLOBALES+++++
    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //Creo el boton de venta
    lateinit var btn_venta: Button
    //Imagen pulsable para las preferencias
    lateinit var prefes: ImageView
    //El frame
    lateinit var root_layout: ConstraintLayout
    //Lista para las verificaciones
    var discos : MutableList<Discos> = ArrayList<Discos>()
    //Recycled view
    lateinit var recDiscos: RecyclerView
    // Acaptador para la lista de discos
    private lateinit var discosListAdapter: AdaptadorDiscos
    //Linear layout manager para el recycled view
    private lateinit var linearLayoutManager: LinearLayoutManager
    //Base de datos online
    private val bd = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pant_princ, container, false)

        //+++++ASOCIACIONES+++++
        //Boton de modificacion de contraseña
        btn_venta = v.findViewById(R.id.Venta)
        //Imagen pulsable para las preferencias
        prefes = v.findViewById(R.id.llave4)
        //El frame
        root_layout = v.findViewById(R.id.frameLayout2)
        //Recycled view
        recDiscos = v.findViewById(R.id.rec_discos)

        return v
    }

    override fun onStart() {
        super.onStart()

        //Leo la BD y la cargo en una lista para pasársela al Recycled View
        bd.collection("albums")
            .limit(30)
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    discos.clear()
                    for (disco in snapshot) {
                        Log.d("CARGA_RV","Lista traida OK")
                        discos.add(disco.toObject())
                        recDiscos.setHasFixedSize(true)
                        linearLayoutManager = LinearLayoutManager(context)
                        recDiscos.layoutManager = linearLayoutManager
                        discosListAdapter = AdaptadorDiscos(discos, requireContext()) { x -> onItemClick(x) }
                        recDiscos.adapter = discosListAdapter
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("CARGA_RV", "Error getting documents: ", exception)
            }

        //Preferencias
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        //+++++Alteraciones segun las preferencias
        //Color de los botones
        btn_venta.setBackgroundColor(Color.parseColor(prefs.getString("Botones","#0000FF")))
        //Color del fondo
        if (prefs.getBoolean("Fondo",false))
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.rojo)))
        else
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.black)))

        //Accion del boton de venta
        btn_venta.setOnClickListener {
            val action = Pant_princDirections.actionPantPrincToVender()
            v.findNavController().navigate(action)
        }

        //Accion del boton de preferencias
        prefes.setOnClickListener {
            val action = Pant_princDirections.actionPantPrincToSettingsActivity()
            v.findNavController().navigate(action)
        }
    }

    fun onItemClick ( position : Int ) : Boolean {
        return true
    }
}