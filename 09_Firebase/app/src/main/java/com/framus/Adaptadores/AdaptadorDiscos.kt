package com.framus.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.framus.Entidades.Discos
import com.framus.Fragmentos.Pant_princDirections
import com.framus.a09_firebase.R

class AdaptadorDiscos(
        private var discoList: MutableList<Discos>,
        val context: Context,
        val onItemClick: (Int) -> Boolean
) : RecyclerView.Adapter<AdaptadorDiscos.DiscoHolder>() {

    companion object {
        private val TAG = "AdaptadorDiscos"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_discos,parent,false)
        return (DiscoHolder(view))
    }

    override fun getItemCount(): Int {
        return discoList.size
    }

    fun setData(newData: ArrayList<Discos>) {
        this.discoList = newData
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DiscoHolder, position: Int) {
        //Muestro el nombre del disco
        holder.setName(discoList[position].titulo)

        //Muestro la caratula
        Glide
                .with(context)
                .load(discoList[position].caratula)
                .centerInside()
                .into(holder.getImageView(discoList[position].id));
        holder.getImageView(discoList[position].id)

        holder.getCardLayout().setOnLongClickListener() {
            onItemClick(position)
        }
    }

    class DiscoHolder (v : View ) : RecyclerView.ViewHolder(v){
        private var view: View

        init {
            this.view = v
        }

        fun setName(name: String) {
            val txt: TextView = view.findViewById(R.id.titulo)
            txt.text = name
        }

        fun getCardLayout (): CardView {
            return view.findViewById(R.id.card_package_disco)
        }

        fun getImageView (id: Int) : ImageView {
            val v: ImageView = view.findViewById(R.id.img_disco)

            v.setOnClickListener {
                //val action = Pant_princDirections.actionPantPrincToDetallesA(id)
                val action = Pant_princDirections.actionPantPrincToContenedorDetalles(id)
                v.findNavController().navigate(action)
            }

            return v
        }
    }
}