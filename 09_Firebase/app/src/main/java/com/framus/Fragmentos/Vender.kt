package com.framus.Fragmentos

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.framus.BaseDeDatos.appDatabase
import com.framus.BaseDeDatos.discosDAO
import com.framus.Entidades.Discos
import com.framus.a09_firebase.R
import com.google.firebase.firestore.FirebaseFirestore

class Vender : Fragment() {

    //+++++DEFINICIONES GLOBALES+++++
    //Creo el boton de confirmacion de venta
    lateinit var btn_conf: Button
    //Casillas
    lateinit var casilla_banda: EditText
    lateinit var casilla_titulo: EditText
    lateinit var casilla_anio: EditText
    lateinit var casilla_genero: EditText
    lateinit var casilla_cover: EditText
    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //El frame
    lateinit var root_layout: ConstraintLayout
    //Definición de las variables la base de datos
    private var db: appDatabase? = null
    private var discosDAO: discosDAO? = null
    //Generador del ID de usuario
    var gen_id: Int = 0
    //Base de datos online
    private val bd = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_vender, container, false)

        //+++++ASOCIACIONES+++++
        //Boton de venta
        btn_conf = v.findViewById(R.id.Conf_venta)
        //Casillas
        casilla_banda= v.findViewById(R.id.casilla_banda)
        casilla_titulo= v.findViewById(R.id.casilla_titulo)
        casilla_anio= v.findViewById(R.id.casilla_anio)
        casilla_genero= v.findViewById(R.id.casilla_genero)
        casilla_cover= v.findViewById(R.id.casilla_cover)
        //El frame
        root_layout = v.findViewById(R.id.frameLayout3)
        //Asociacion de las variables la base de datos
        db = appDatabase.getAppDataBase(v.context)
        discosDAO = db?.discosDAO()

        //Preferencias
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        //+++++Alteraciones segun las preferencias
        //Color de los botones
        btn_conf.setBackgroundColor(Color.parseColor(prefs.getString("Botones","#0000FF")))
        //Color del fondo
        if (prefs.getBoolean("Fondo",false))
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.rojo)))
        else
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.black)))

        return v
    }

    override fun onStart() {
        super.onStart()

        btn_conf.setOnClickListener {
            gen_id = (5..9999).random()
            val cd = Discos(
                gen_id,
                casilla_banda.text.toString(),
                casilla_titulo.text.toString(),
                casilla_anio.text.toString(),
                casilla_genero.text.toString(),
                "https://static.wikia.nocookie.net/temonpe/images/c/cd/Cd.gif/revision/latest?cb=20100930214539&path-prefix=es"
            )
            bd.collection("albums").document(cd.id.toString()).set(cd)

            //val action = VenderDirections.actionVenderToPantPrinc()
            //v.findNavController().navigate(action)
        }
    }
}