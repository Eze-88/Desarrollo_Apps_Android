package com.framus.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.framus.BaseDeDatos.appDatabase
import com.framus.BaseDeDatos.discosDAO
import com.framus.Entidades.Discos
import com.framus.a06_tabs.R

class Vender : Fragment() {

    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //Creo el boton de confirmacion de venta
    lateinit var btn_conf: Button
    //Casillas
    lateinit var casilla_banda: EditText
    lateinit var casilla_titulo: EditText
    lateinit var casilla_anio: EditText
    lateinit var casilla_genero: EditText
    lateinit var casilla_cover: EditText

    //Definición de las variables la base de datos
    private var db: appDatabase? = null
    private var discosDAO: discosDAO? = null

    //Generador del ID de usuario
    var gen_id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_vender, container, false)

        //Boton de venta
        btn_conf = v.findViewById(R.id.Conf_venta)
        //Casillas
        casilla_banda= v.findViewById(R.id.casilla_banda)
        casilla_titulo= v.findViewById(R.id.casilla_titulo)
        casilla_anio= v.findViewById(R.id.casilla_anio)
        casilla_genero= v.findViewById(R.id.casilla_genero)
        casilla_cover= v.findViewById(R.id.casilla_cover)

        db = appDatabase.getAppDataBase(v.context)
        discosDAO = db?.discosDAO()

        return v
    }

    override fun onStart() {
        super.onStart()

        btn_conf.setOnClickListener {
            gen_id = (5..9999).random()
            discosDAO?.insertPerson(Discos(
                gen_id,
                casilla_banda.text.toString(),
                casilla_titulo.text.toString(),
                casilla_anio.text.toString(),
                casilla_genero.text.toString(),
                "https://static.wikia.nocookie.net/temonpe/images/c/cd/Cd.gif/revision/latest?cb=20100930214539&path-prefix=es"
            ))
        }
    }
}