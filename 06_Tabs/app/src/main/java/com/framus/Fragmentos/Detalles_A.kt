package com.framus.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.framus.BaseDeDatos.appDatabase
import com.framus.BaseDeDatos.discosDAO
import com.framus.BaseDeDatos.usuarioDao
import com.framus.Entidades.Discos
import com.framus.a06_tabs.R
import org.w3c.dom.Text

class Detalles_A : Fragment() {

    //+++++DEFINICIONES GLOBALES+++++
    //Definición de las variables la base de datos
    private var db: appDatabase? = null
    private var discosDAO: discosDAO? = null
    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //Textos
    lateinit var text_banda: TextView
    lateinit var text_titulo: TextView
    lateinit var text_anio: TextView
    lateinit var text_genero: TextView

    var pos: Int = 0

    var cd:  MutableList<Discos> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_detalles__a, container, false)

        //Carteles
        text_banda = v.findViewById(R.id.txt_banda_d)
        text_titulo = v.findViewById(R.id.txt_titulo_d)
        text_anio = v.findViewById(R.id.txt_anio_d)
        text_genero = v.findViewById(R.id.txt_genero_d)

        db = appDatabase.getAppDataBase(v.context)
        discosDAO = db?.discosDAO()

        return v
    }

    override fun onStart() {
        super.onStart()

        cd = discosDAO?.loadAllPersons() as MutableList<Discos>

        var id: Int = Detalles_AArgs.fromBundle(requireArguments()).id

        for ( i in 0 until cd.size){
            if (cd[i].id == id){
                pos = i
                break
            }
        }

        text_banda.text = "Banda: " + cd[pos].banda
    }
}