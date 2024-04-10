package com.fausto.breeds.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fausto.breeds.databinding.BreedItemAdapterBinding
import com.fausto.model.BreedsModel

internal class BreedsAdapter(
    private val breedsList: List<BreedsModel>, private val listener: SectionAdapterClickListener?
) : RecyclerView.Adapter<BreedsAdapter.ViewHolder>() {

    internal inner class ViewHolder(binding: BreedItemAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var breedName = binding.breedName

        init {
            binding.root.setOnClickListener {
                breedsList[adapterPosition].apply {
                    listener?.invoke(referenceImageId, queryBreedId)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            BreedItemAdapterBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount() = breedsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.breedName.text = breedsList[position].name
    }
}