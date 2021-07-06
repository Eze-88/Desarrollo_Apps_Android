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
    //Definición de las variables la base de datos
    private var db: appDatabase? = null
    private var discosDAO: discosDAO? = null
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
    var flag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Asociacion de las variables la base de datos
        db = appDatabase.getAppDataBase(requireContext())
        discosDAO = db?.discosDAO()


        //Base de datos Firestore
        //ESCRIBO
//        var lat: Double = -34.60364307941135
//        var lon: Double = -58.381597449199305
//        var cd = Discos(4525,"The Black Keys","El Camino","2011","Garage Rock","https://images-na.ssl-images-amazon.com/images/I/810GnasrfjL._SX466_.jpg",lat,lon)
//        bd.collection("albums").document(cd.id.toString()).set(cd)
//        lat = -34.59471766346627
//        lon = -58.376084378282776
//        cd = Discos(4084,"Pearl Jam","Vs","1993","Grunge","https://img.discogs.com/XaZw9d4nux7zQCwVMp3USt2F6QY=/fit-in/600x600/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-1820450-1245546969.jpeg.jpg",lat,lon)
//        bd.collection("albums").document(cd.id.toString()).set(cd)
//        lat = -34.575563554723054
//        lon = -58.40953558926083
//        cd = Discos(5692,"Deftones","Deftones","2003","Metal Alternativo","https://media.pitchfork.com/photos/5929a8fa5e6ef95969321323/1:1/w_320/b3e6b384.jpg",lat,lon)
//        bd.collection("albums").document(cd.id.toString()).set(cd)
//        lat = -34.56940483386843
//        lon = -58.41164038455751
//        cd = Discos(976,"Dinosaur Jr","I Bet On Sky","2012","Indie Rock","https://upload.wikimedia.org/wikipedia/en/c/c4/I_Bet_on_Sky.jpeg",lat,lon)
//        bd.collection("albums").document(cd.id.toString()).set(cd)
//        lat = -34.58147425922137
//        lon = -58.444227126039124
//        cd = Discos(9836,"The Offspring","Ignition","1992","Skate Punk","https://img.discogs.com/k3QfPGvxwGt3G-k5RofPajdbnko=/fit-in/300x300/filters:strip_icc():format(jpeg):mode_rgb():quality(40)/discogs-images/R-4892277-1458203046-3312.jpeg.jpg",lat,lon)
//        bd.collection("albums").document(cd.id.toString()).set(cd)
        //LEO
//        bd.collection("albums").document("77").get().addOnSuccessListener { dataSnapshot ->
//            if (dataSnapshot != null){
//                val cd = dataSnapshot.toObject<Discos>()
//                if (cd != null) {
//                    Log.d("PERRO",cd.banda)
//                }
//            } else {
//                Log.d("PERRO", "No existe el documento")
//            }
//        }
        //BORRO
        //bd.collection("albums").document("8289").delete()
        bd.collection("albums")
            .limit(30)
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    for (disco in snapshot) {
                        Log.d("PERRO","Lista traida OK")
                        discos.add(disco.toObject())
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("PERRO", "Error getting documents: ", exception)
            }
    }

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

        bd.collection("albums")
            .limit(30)
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot != null) {
                    discos.clear()
                    for (disco in snapshot) {
                        Log.d("PERRO","Lista traida OK")
                        discos.add(disco.toObject())
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("PERRO", "Error getting documents: ", exception)
            }

//        bd.collection("albums")
//            .limit(30)
//            .get()
//            .addOnSuccessListener { snapshot ->
//                if (snapshot != null) {
//                    for (disco in snapshot) {
//                        Log.d("PERRO","Lista traida OK")
//                        discos.add(disco.toObject())
//                    }
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w("PERRO", "Error getting documents: ", exception)
//            }

        return v
    }

    override fun onStart() {
        super.onStart()

        //var discos : MutableList<Discos> = ArrayList<Discos>()
        //Leo la BD y la cargo en una lista para pasársela al Recycled View
//        bd.collection("albums")
//            .limit(30)
//            .get()
//            .addOnSuccessListener { snapshot ->
//                if (snapshot != null) {
//                    discos.clear()
//                    for (disco in snapshot) {
//                        Log.d("PERRO","Lista traida OK")
//                        discos.add(disco.toObject())
//                    }
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w("PERRO", "Error getting documents: ", exception)
//            }

        //+++++RECYCLED VIEW+++++
        recDiscos.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recDiscos.layoutManager = linearLayoutManager
        discosListAdapter = AdaptadorDiscos(discos, requireContext()) { x -> onItemClick(x) }
        recDiscos.adapter = discosListAdapter

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