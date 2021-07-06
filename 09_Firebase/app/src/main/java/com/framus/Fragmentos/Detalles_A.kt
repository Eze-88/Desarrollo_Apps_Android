package com.framus.Fragmentos

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.framus.BaseDeDatos.appDatabase
import com.framus.BaseDeDatos.discosDAO
import com.framus.Entidades.Discos
import com.framus.a09_firebase.R
import com.google.android.gms.location.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Math.pow
import kotlin.math.sqrt

class Detalles_A : Fragment() {

    //+++++DEFINICIONES GLOBALES+++++
    //Cuadros de texto
    lateinit var text_banda: TextView
    lateinit var text_titulo: TextView
    lateinit var text_anio: TextView
    lateinit var text_genero: TextView
    lateinit var text_distancia: TextView
    //Definicion de la variable para referenciar la vista
    lateinit var v: View
    //Creo el boton de confirmacion de compra
    lateinit var btn_compra: Button
    //Creo el boton de confirmacion de correcion
    lateinit var btn_mod: Button
    //El frame
    lateinit var root_layout: ConstraintLayout
    //Base de datos online
    private val bd = FirebaseFirestore.getInstance()
    // Permisos para la ubicacion
    val PERMISSION_ID = 42
    //Variable necesaria para la ubicacion
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    //Variable que contendrá la ubicacion actual
    lateinit var ubicacion: Location
    var cidi: Discos? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val parentJob = Job()
        val scope = CoroutineScope(Dispatchers.Default + parentJob)

        scope.launch {
            bajo_BD()
            obtengo_ubicacion()
        }
    }

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
        text_distancia = v.findViewById(R.id.kilometros)
        //Boton de venta
        btn_compra = v.findViewById(R.id.Comprar)
        //Boton de correcion
        btn_mod = v.findViewById(R.id.Corregir)
        //El frame
        root_layout = v.findViewById(R.id.frameLayout4)

        bd.collection("albums").document(identificador.toString()).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot != null){
                val cd = dataSnapshot.toObject<Discos>()
                if (cd != null) {
                    //Se muestra toda la info del disco
                    text_banda.text = "Banda: " + cd.banda
                    text_titulo.text = "Título: " + cd.titulo
                    text_anio.text = "Año: " + cd.anio
                    text_genero.text = "Género: " + cd.genero
                    var distancia = FloatArray(1)
                    Location.distanceBetween(ubicacion.latitude,ubicacion.longitude,cd.lat,cd.long, distancia)
                    text_distancia.text = "Distancia (km): " + String.format("%.2f",distancia[0]/1000)
                }
            } else {
                Log.d("ERROR_BD", "No existe el documento")
            }
        }

        //Preferencias
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())

        //+++++Alteraciones segun las preferencias
        //Color de los botones
        btn_mod.setBackgroundColor(Color.parseColor(prefs.getString("Botones","#0000FF")))
        btn_compra.setBackgroundColor(Color.parseColor(prefs.getString("Botones","#0000FF")))
        //Color del fondo
        if (prefs.getBoolean("Fondo",false))
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.rojo)))
        else
            root_layout.setBackgroundColor(Color.parseColor(getString(R.color.black)))

        return v
    }

    override fun onStart() {
        super.onStart()

        //Variable auxiliar para cargar el ID del disco a comprar o modificar
        val id: Int = identificador

        //Accion de baja de la tabla, comprando un disco
        btn_compra.setOnClickListener {
            bd.collection("albums").document(identificador.toString()).delete()
        }

        //Accion de modificacion de la tabla, corrigiendo la info de un disco
        btn_mod.setOnClickListener {
            val action = Contenedor_detallesDirections.actionContenedorDetallesToCorreccion2(id)
            v.findNavController().navigate(action)
        }
    }

    suspend fun bajo_BD(){
        bd.collection("albums").document(identificador.toString()).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot != null){
                val cd = dataSnapshot.toObject<Discos>()
                if (cd != null) {
                    Log.d("LOG_BD","Exito")
                    cidi = cd
                }
            } else {
                Log.d("ERROR_BD", "No existe el documento")
            }
        }
    }

    suspend fun obtengo_ubicacion(){

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        getLastLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        ubicacion = location
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Turn on location", Toast.LENGTH_LONG).show()
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        mFusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    companion object{
        var identificador: Int = 0
    }
}