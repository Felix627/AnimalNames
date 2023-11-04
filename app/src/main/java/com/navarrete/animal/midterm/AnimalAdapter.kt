package com.navarrete.animal.midterm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AnimalAdapter(private val animalList: List<Animal>) :
    RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    private var onItemClick: ((Animal) -> Unit)? = null

    fun setOnItemClickListener(listener: (Animal) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = animalList[position]
        holder.bind(animal)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(animal)
        }
    }

    override fun getItemCount(): Int {
        return animalList.size
    }

    inner class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.animalNameTextView)

        fun bind(animal: Animal) {
            nameTextView.text = animal.animalName
        }
    }
}