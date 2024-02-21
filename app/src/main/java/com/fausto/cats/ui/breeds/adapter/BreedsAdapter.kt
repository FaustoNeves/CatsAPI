package com.fausto.cats.ui.breeds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fausto.cats.databinding.BreedItemAdapterBinding
import com.fausto.cats.domain.model.BreedsModel

internal class BreedsAdapter(private val breedsList: List<BreedsModel>) :
    RecyclerView.Adapter<BreedsAdapter.ViewHolder>() {
    class ViewHolder(binding: BreedItemAdapterBinding) : RecyclerView.ViewHolder(binding.root) {

        var breedName = binding.breedName
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