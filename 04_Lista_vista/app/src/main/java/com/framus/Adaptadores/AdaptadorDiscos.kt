package com.framus.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.framus.Entidades.Discos
import com.framus.a04_lista_vista.R
import android.content.Context

class AdaptadorDiscos (private var discoList: MutableList<Discos>, val onItemClick: (Int) -> Boolean) : RecyclerView.Adapter<AdaptadorDiscos.DiscoHolder>() {

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

        fun getButton (): Button {
            return view.findViewById(R.id.btn_disco)
        }

        fun getImageView () : ImageView {
            return view.findViewById(R.id.img_disco)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_discos,parent,false)
        return (AdaptadorDiscos.DiscoHolder(view))
    }

    override fun onBindViewHolder(holder: DiscoHolder, position: Int) {
        holder.setName(discoList[position].titulo)

        Glide
            .with(context)
            .load("https://firebasestorage.googleapis.com/v0/b/firestoreexample-ec489.appspot.com/o/Fotos%2FGUERNICA.jpg?alt=media&token=001a8ffc-96c2-4aeb-9120-8d5099b3fa1c")

            .centerInside()
            .into(holder.getImageView());

        holder.getCardLayout().setOnLongClickListener() {
            onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return discoList.size
    }
}