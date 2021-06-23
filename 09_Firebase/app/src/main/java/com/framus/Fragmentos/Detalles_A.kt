package com.framus.Fragmentos

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.framus.BaseDeDatos.appDatabase
import com.framus.BaseDeDatos.discosDAO
import com.framus.Entidades.Discos
import com.framus.a09_firebase.R

class Detalles_A : Fragment() {

    //+++++DEFINICIONES GLOBALES+++++
    //Cuadros de texto
    lateinit var text_banda: TextView
    lateinit var text_titulo: TextView
    lateinit var text_anio: TextView
    lateinit var text_genero: TextView
    //Definición de las variables la base de datos
    private var db: appDatabase? = null
    private var discosDAO: discosDAO? = null
    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //Creo el boton de confirmacion de compra
    lateinit var btn_compra: Button
    //Creo el boton de confirmacion de correcion
    lateinit var btn_mod: Button
    //El frame
    lateinit var root_layout: ConstraintLayout
    //Variable donde se carga el argumento del fragmento
    var pos: Int = 0
    //Variable auxiliar de busqueda
    var cd:  MutableList<Discos> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.takeIf { it.containsKey("id_cd") }?.apply {
            identificador = getInt("id_cd")
        }

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_detalles__a, container, false)

        //+++++ASOCIACIONES+++++
        //Cuadros de texto
        text_banda = v.findViewById(R.id.txt_banda_d)
        text_titulo = v.findViewById(R.id.txt_titulo_d)
        text_anio = v.findViewById(R.id.txt_anio_d)
        text_genero = v.findViewById(R.id.txt_genero_d)
        //Boton de venta
        btn_compra = v.findViewById(R.id.Comprar)
        //Boton de correcion
        btn_mod = v.findViewById(R.id.Corregir)
        //El frame
        root_layout = v.findViewById(R.id.frameLayout4)
        //Asociacion de las variables la base de datos
        db = appDatabase.getAppDataBase(v.context)
        discosDAO = db?.discosDAO()

        //Variable auxiliar para cargar el ID del disco a comprar o modificar
        val id: Int = identificador

        //Identifico el disco a mostrar
        cd = discosDAO?.loadAllPersons() as MutableList<Discos>
        for ( i in 0 until cd.size){
            if (cd[i].id == id){
                pos = i
                break
            }
        }

        //Preferencias
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        //+++++Alteraciones segun las preferencias
        //Color de los botones
        btn_mod.setBackgroundColor(Color.parseColor(prefs.getString("Botones","")))
        btn_compra.setBackgroundColor(Color.parseColor(prefs.getString("Botones","")))
        //Color del fondo
        if (prefs.getBoolean("Fondo",false))
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.rojo)))
        else
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.black)))

        //Se muestra toda la info del disco
        text_banda.text = "Banda: " + cd[pos].banda
        text_titulo.text = "Título: " + cd[pos].titulo
        text_anio.text = "Año: " + cd[pos].anio
        text_genero.text = "Género: " + cd[pos].genero

        return v
    }

    override fun onStart() {
        super.onStart()

        //Variable auxiliar para cargar el ID del disco a comprar o modificar
        val id: Int = identificador

        //Accion de baja de la tabla, comprando un disco
        btn_compra.setOnClickListener {
            discosDAO?.delete(Discos(id, "", "", "", "", ""))
            val action = Contenedor_detallesDirections.actionContenedorDetallesToPantPrinc()
            v.findNavController().navigate(action)
        }

        //Accion de modificacion de la tabla, corrigiendo la info de un disco
        btn_mod.setOnClickListener {
            val action = Contenedor_detallesDirections.actionContenedorDetallesToCorreccion2(id)
            v.findNavController().navigate(action)
        }
    }

    companion object{
        var identificador: Int = 0
    }
}