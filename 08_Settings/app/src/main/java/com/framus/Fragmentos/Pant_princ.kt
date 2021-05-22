package com.framus.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.framus.Adaptadores.AdaptadorDiscos
import com.framus.BaseDeDatos.appDatabase
import com.framus.BaseDeDatos.discosDAO
import com.framus.Entidades.Discos
import com.framus.a08_settings.R


class Pant_princ : Fragment() {

    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    lateinit var recDiscos: RecyclerView

    private lateinit var linearLayoutManager: LinearLayoutManager
    var discos : MutableList<Discos> = ArrayList<Discos>()

    private lateinit var discosListAdapter: AdaptadorDiscos

    //Creo el boton de venta
    lateinit var btn_venta: Button

    //Definición de las variables la base de datos
    private var db: appDatabase? = null
    private var discosDAO: discosDAO? = null

    //Generador del ID de usuario
    var gen_id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = appDatabase.getAppDataBase(requireContext())
        discosDAO = db?.discosDAO()

        //+++++PRE-CARGA DE LA LISTA de discos+++++
        var gen_id: Int = 0
        discosDAO?.insertPerson(Discos(gen_id,"The Black Keys","El Camino","2011","Garage Rock","https://images-na.ssl-images-amazon.com/images/I/810GnasrfjL._SX466_.jpg"))
        gen_id+=1
        discosDAO?.insertPerson(Discos(gen_id,"Pearl Jam","Vs","1993","Grunge","https://img.discogs.com/XaZw9d4nux7zQCwVMp3USt2F6QY=/fit-in/600x600/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-1820450-1245546969.jpeg.jpg"))
        gen_id+=1
        discosDAO?.insertPerson(Discos(gen_id,"Deftones","Deftones","2003","Metal Alternativo","https://media.pitchfork.com/photos/5929a8fa5e6ef95969321323/1:1/w_320/b3e6b384.jpg"))
        gen_id+=1
        discosDAO?.insertPerson(Discos(gen_id,"The Offspring","Ignition","1992","Skate Punk","https://img.discogs.com/k3QfPGvxwGt3G-k5RofPajdbnko=/fit-in/300x300/filters:strip_icc():format(jpeg):mode_rgb():quality(40)/discogs-images/R-4892277-1458203046-3312.jpeg.jpg"))
        gen_id+=1
        discosDAO?.insertPerson(Discos(gen_id,"Dinosaur Jr","I Bet On Sky","2012","Indie Rock","https://upload.wikimedia.org/wikipedia/en/c/c4/I_Bet_on_Sky.jpeg"))

        //Eliminando un disco
        //discosDAO?.delete(Discos(5, "", "", "", "", ""))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pant_princ, container, false)

        recDiscos = v.findViewById(R.id.rec_discos)

        //Boton de modificacion de contraseña
        btn_venta = v.findViewById(R.id.Venta)

        return v
    }

    override fun onStart() {
        super.onStart()

        recDiscos.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recDiscos.layoutManager = linearLayoutManager
        //Le paso el listado de discos al Adaptador
        //userList = usuarioDao?.loadAllPersons() as MutableList<Persona>
        discos = discosDAO?.loadAllPersons() as MutableList<Discos>
        discosListAdapter = AdaptadorDiscos(discos, requireContext()) { x -> onItemClick(x) }
        recDiscos.adapter = discosListAdapter

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        btn_venta.setOnClickListener {
            val action = Pant_princDirections.actionPantPrincToVender()
            v.findNavController().navigate(action)
        }
    }

    fun onItemClick ( position : Int ) : Boolean {
        return true
    }
}